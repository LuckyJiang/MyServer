package com.example.myserver.es.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Data;
import lombok.ToString;

import java.util.List;
import java.util.Map;

/**
 * @author by Jiang Xiaomin
 * @desrc:
 */
@Data
@ToString
public class EsItem {
    @JsonIgnore
    private String id;
    
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String tag;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<String> compositions = Lists.newArrayList();

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<String> categories = Lists.newArrayList();

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<String, ESProperty> properties = Maps.newHashMap();
    
}
