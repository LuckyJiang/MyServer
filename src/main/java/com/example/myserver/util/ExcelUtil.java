package com.example.myserver.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.enums.CellExtraTypeEnum;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.example.myserver.exception.BadInputParameterException;
import com.example.myserver.exception.InternalServerException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

public final class ExcelUtil {

    private static final Integer DEFAULT_EXCEL_COLUMN_COUNT = 26;

    private ExcelUtil() {
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


}
