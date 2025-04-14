package com.example.myserver.es.service;


import com.example.myserver.es.entity.ESProperty;
import com.example.myserver.es.entity.EsItem;
import com.example.myserver.es.entity.excel.*;
import com.example.myserver.util.ExcelUtil;
import com.example.myserver.util.JacksonUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.frameworkset.elasticsearch.client.ClientInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.example.myserver.es.enums.EsFileName.*;
import static java.util.stream.Collectors.toList;

/**
 * @author by Jiang Xiaomin
 * @desrc:
 */
@Service
public class EsService implements ApplicationRunner {

    public static final Path DATA_ADDRESS = Paths.get(System.getProperty("user.dir"), "datas");

    public static final String FILE_SUFFIX = ".csv";
    
    public static final String INDEX_SUFFIX = "v1";
    
    public static Map<String, ItemCategory> categoryMap = Maps.newHashMap();

    @Autowired
    private ClientInterface clientInterface;
    
    
    public static void main(String[] args) {
        //readCacheData();
    }
    public void readCacheData() {
        final Path categoryFile = DATA_ADDRESS.resolve(CATEGORIES.name().concat(FILE_SUFFIX));
        final List<ItemCategory> categories = ExcelUtil.readExcel(categoryFile, ItemCategory.class);
        categoryMap = categories.stream().collect(Collectors.toMap(ItemCategory::getId, Function.identity()));
        final Path repositoryFile = DATA_ADDRESS.resolve(REPOSITORIES.name().concat(FILE_SUFFIX));
        final List<ItemRepository> repositories = ExcelUtil.readExcel(repositoryFile, ItemRepository.class);
        final Path itemFile = DATA_ADDRESS.resolve(ITEMS.name().concat(FILE_SUFFIX));
        final List<Item> items = ExcelUtil.readExcel(itemFile, Item.class);
        final Path propertyFile = DATA_ADDRESS.resolve(PROPERTIES.name().concat(FILE_SUFFIX));
        final List<ItemProperties> properties = ExcelUtil.readExcel(propertyFile, ItemProperties.class);
        final Path compositionFile = DATA_ADDRESS.resolve(COMPOSITIONS.name().concat(FILE_SUFFIX));
        final List<ItemComposition> compositions = ExcelUtil.readExcel(compositionFile, ItemComposition.class);
/*        final Path referenceFile = DATA_ADDRESS.resolve(REFERENCES.name().concat(FILE_SUFFIX));
        final List<ItemReference> references = ExcelUtil.readExcel(referenceFile, ItemReference.class);
        final Path handleFile = DATA_ADDRESS.resolve(HANDLES.name().concat(FILE_SUFFIX));
        final List<ItemHandle> handles = ExcelUtil.readExcel(handleFile, ItemHandle.class);*/
        Map<String, List<EsItem>> esItemIndexDocument = getEsItemIndexDocument(categories, repositories, items, properties, compositions);
        esItemIndexDocument.forEach(this:: createItemIndex);
    }

    private static void batchAddToItemIndex() {
        
    }

    private static Map<String, List<EsItem>> getEsItemIndexDocument(final List<ItemCategory> categories,
                                                                    final List<ItemRepository> repositories,
                                                                    final List<Item> items,
                                                                    final List<ItemProperties> properties,
                                                                    final List<ItemComposition> compositions) {
        final Map<String, List<EsItem>> itemDocumentMap = Maps.newHashMap();
        final Map<String, ItemProperties> propertiesMap = properties.stream()
                .collect(Collectors.toMap(ItemProperties::getItemId, itemProperties -> itemProperties, (k1, k2) -> k1));
        repositories.forEach(repository -> {
            final String repositoryId = repository.getId();
            final String repositoryVersion = repositoryId.concat("_").concat(INDEX_SUFFIX);
            final List<Item> itemFilter = items.stream()
                    .filter(item -> repositoryVersion.equals(item.getRepositoryVersion()))
                    .collect(Collectors.toList());
            
            List<EsItem> esItems = itemFilter.stream()
                    .map(item -> getEsItemDocument(item, categories, propertiesMap, compositions))
                    .collect(Collectors.toList());
            itemDocumentMap.put(repositoryVersion.concat("_item"), esItems);
        });
        return itemDocumentMap;
    }

    private static EsItem getEsItemDocument(final Item item, 
                                     final List<ItemCategory> categories, 
                                     final Map<String, ItemProperties> propertiesMap,
                                     final List<ItemComposition> compositions) {
        final EsItem esItem = new EsItem();
        esItem.setId(item.getId());
        esItem.setTag(item.getTag());
        /*final List<String> comps = getItemCompositions(item, compositions);
        esItem.setCompositions(comps);*/
        final List<String> cgs =  getItemCategories(item, categories);
        esItem.setCategories(cgs);
        final Map<String, ESProperty> esProperties =  getItemProperties(propertiesMap.getOrDefault(item.getId(), null));
        return esItem;
    }

    private static Map<String, ESProperty> getItemProperties(final ItemProperties properties) {
        final Map<String, ESProperty> esProperties = Maps.newHashMap();
        if (Objects.nonNull(properties)) {
            final String p = properties.getProperties();
            List<ItemProperty> deserialize = JacksonUtil.deserialize(p, new TypeReference<List<ItemProperty>>() {
            });

            deserialize.stream().forEach(property -> {
                Map<String, String> value = property.getValue();
                if (value.containsKey("value")) {
                    final ESProperty esProp = ESProperty.builder().value(value.get("value")).build();
                    esProperties.put(property.getName(), esProp);
                }
                
            });
        }
        return esProperties;
    }

    private static List<String> getItemCategories(Item item, List<ItemCategory> categories) {
        final List<String> categoryNames = Lists.newArrayList();
        final LinkedList<String> stack = new LinkedList<>();
        stack.addFirst(item.getName());
        while (!stack.isEmpty()) {
            final String name = stack.removeFirst();
            final ItemCategory itemCategory = categories.stream()
                    .filter(c -> name.equals(c.getName()))
                    .findFirst().orElse(null);
            if (Objects.nonNull(itemCategory)) {
                categoryNames.add(0, categoryMap.get(itemCategory.getParentId()).getName());
                stack.addLast(itemCategory.getParentId());
            }
        }
        return categoryNames;
    }

    private static List<String> getItemCompositions(final Item item, final List<ItemComposition> compositions) {
        final List<String> ancestors = Lists.newArrayList();
        final LinkedList<List<String>> stack = new LinkedList<>();
        stack.addFirst(Lists.newArrayList(item.getId()));
        while (!stack.isEmpty()) {
            stack.removeFirst().forEach(s -> {
                final List<String> compositionIds = compositions.stream()
                        .filter(nextComposition -> Objects.nonNull(nextComposition.getCompositionItemId()))
                        .map(ItemComposition::getCompositionItemId).collect(toList());
                ancestors.addAll(compositionIds);
                stack.addLast(compositionIds);
            });

        }
        return ancestors;
    }

    private void createItemIndex(final String index, final List<EsItem> esItems) {
        if (!clientInterface.existIndice(index)) {
            final String createIndex = clientInterface.createIndiceMapping(index, "createIndex");
            System.out.println(createIndex);
        }
        clientInterface.addDateDocuments(index, esItems);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        readCacheData();
    }
}
