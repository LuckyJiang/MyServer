package com.example.myserver.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.enums.CellExtraTypeEnum;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.WriteTable;
import com.example.myserver.common.exception.BadInputParameterException;
import com.example.myserver.common.exception.InternalServerException;
import com.google.common.collect.ImmutableMap;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.*;

public final class ExcelUtil {

    private static final Integer DEFAULT_EXCEL_COLUMN_COUNT = 26;

    private ExcelUtil() {
    }

    public static <T> List<T> readExcel(final Path path, final Class<T> tClass) {
        return EasyExcel.read(path.toFile())
                .sheet() // 默认读取第一个sheet
                .head(tClass) // 指定数据模型类
                .doReadSync(); // 同步读取数据，返回数据列表
    }

    public static List<ReadSheet> getReadSheets(final Path tempFile) {
        final List<ReadSheet> readSheets = EasyExcel.read(tempFile.toFile())
                .build()
                .excelExecutor()
                .sheetList();
        return readSheets;
    }

    public static List<ReadSheet> getReadSheets(final MultipartFile file) {
        try (InputStream is = file.getInputStream()) {
            return EasyExcel.read(is)
                    .build()
                    .excelExecutor()
                    .sheetList();
        } catch (IOException e) {
            throw new InternalServerException("解析文件失败");
        }

    }

    /**
     *
     * @param readSheet
     * @param file
     * @param headNum : 表头占的行数。
     */
    public static void readSheet(final ReadSheet readSheet,
                                 final ReadListener<?> listener,
                                 final MultipartFile file,
                                 final Integer headNum) {
        try (InputStream is = file.getInputStream()) {
            EasyExcel.read(is, listener)
                    .headRowNumber(Objects.isNull(headNum) ? 1 : headNum)
                    .extraRead(CellExtraTypeEnum.MERGE)
                    .sheet(readSheet.getSheetNo())
                    .autoTrim(true)
                    .doRead();
        } catch (IOException e) {
            throw new InternalServerException("解析文件失败");
        }
    }

    /**
     *
     * @param readSheet
     * @param file
     * @param headNum : 表头占的行数。
     */
    public static void readSheet(final ReadSheet readSheet,
                                 final ReadListener<?> listener,
                                 final Path file,
                                 final Integer headNum) {
        EasyExcel.read(file.toFile(), listener)
                .headRowNumber(Objects.isNull(headNum) ? 1 : headNum)
                .extraRead(CellExtraTypeEnum.MERGE)
                .sheet(readSheet.getSheetNo())
                .autoTrim(true)
                .doRead();
    }
    
    

    public static <T> List<T> readSheet(final ReadSheet readSheet, final Class<T> tClass, final File file) {
        return EasyExcel.read(file)
                .head(tClass)
                .sheet(readSheet.getSheetNo())
                .doReadSync();
    }

    public static <T> void readSheet(final MultipartFile file, 
                                     final Class<T> clazz, 
                                     final String sheetName, 
                                     final ReadListener<?> listener) {
        if (Objects.isNull(file)) {
            throw new BadInputParameterException("文件不存在");
        }
        try (InputStream is = file.getInputStream()) {
            EasyExcel.read(is, clazz, listener)
                    .sheet(sheetName)
                    .autoTrim(true)
                    .doRead();
        } catch (IOException e) {
            throw new InternalServerException("解析文件失败");
        }
    }

    public static void readSheet(final MultipartFile file,  final String sheetName, final ReadListener<?> listener) {
        try (InputStream is = file.getInputStream()) {
            EasyExcel.read(is, listener)
                    .extraRead(CellExtraTypeEnum.MERGE)
                    .sheet(sheetName)
                    .autoTrim(true)
                    .doRead();
        } catch (IOException e) {
            throw new InternalServerException("解析文件失败");
        }
    }

    public static void readSheet(final File file,  final String sheetName, final ReadListener<?> listener) {
        EasyExcel.read(file, listener)
                .extraRead(CellExtraTypeEnum.MERGE)
                .sheet(sheetName)
                .autoTrim(true)
                .doRead();
    }
    
    public static void readSheet(final String sheetName, final File file, final ReadListener<?> readListener) {
        EasyExcel.read(file, readListener)
                .sheet(sheetName)
                .autoTrim(true)
                .doRead();
    }

    public static ExcelWriter getExcelWriter(final File file) {
        return EasyExcel.write(file).build();
    }
    

    /**
     * 相当于导入了两个table,第一个table有表头没有没有数据，第二个table有表头有数据。
     * @param sheetName
     * @param excelWriter
     * @param data
     */
    public static void exportToExcel(final String sheetName,
                                     final ExcelWriter excelWriter,
                                     final Class clazz,
                                     final List<?> data) {
        final WriteSheet writeSheet = EasyExcel.writerSheet(sheetName).needHead(Boolean.FALSE).build();
        final WriteTable talePartOne = EasyExcel.writerTable(0)
                .head(getHead(sheetName, 26))
                .needHead(Boolean.TRUE)
                .build();
        excelWriter.write(new ArrayList<>(), writeSheet, talePartOne);
        final WriteTable talePartTwo = EasyExcel.writerTable(1)
                .head(clazz)
                .relativeHeadRowIndex(0)
                .needHead(Boolean.TRUE)
                .build();
        excelWriter.write(data, writeSheet, talePartTwo);
    }

    public static void exportToExcel(final File targetFile, 
                                     final File templateFile, 
                                     final Class clazz,
                                     final List<?> data,
                                     final Map<String, Object> fillData
    ) {
        final ExcelWriter excelWriter = EasyExcel.write(targetFile).withTemplate(templateFile).build();
        final WriteSheet writeSheet = EasyExcel.writerSheet("sheet1")
                .head(clazz)
                .relativeHeadRowIndex(1)
                .needHead(Boolean.TRUE).build();
        fillData.forEach((key, value) -> excelWriter.fill(ImmutableMap.of(key, value), writeSheet));
        excelWriter.write(data, writeSheet);
        excelWriter.finish();
    }

    private static List<List<String>> getHead(final String firstRow, final Integer num) {
        final List<List<String>> list = new ArrayList<List<String>>();
        for (int i = 0; i < num; i++) {
            list.add(Arrays.asList(firstRow));
        }
        return list;
    }

    public static void closeExcelWriter(final ExcelWriter excelWriter) {
        excelWriter.finish();
    }

}
