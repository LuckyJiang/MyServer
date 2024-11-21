package com.example.myserver.excel.service;


import com.alibaba.excel.metadata.CellExtra;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.example.myserver.util.ExcelUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author by Jiang Xiaomin
 * @desrc:
 */
@Service
public class ExcelService {
    
    private static final String SHEET_NAME = "queTemplate";

    private static final Integer HEADER_NUMBER = 3;

    /**
     * 将合并单元格的数据进行读取到一个list中。
     * @param file
     */
    public void upload(final MultipartFile file) {
        final QuestionExcelListener listener = new QuestionExcelListener();
        ExcelUtil.readSheet(file, SHEET_NAME, listener);
    }

    /**
     * 将合并单元格的数据进行读取到一个list中。
     * @param file
     */
    public void upload(final File file) {
        final QuestionExcelListener listener = new QuestionExcelListener();
        ExcelUtil.readSheet(file, SHEET_NAME, listener);
    }
    
    public void upload2(final Path file) {
        final List<ReadSheet> readSheets = Optional.ofNullable(ExcelUtil.getReadSheets(file))
                .orElse(Collections.emptyList());
        readSheets.forEach(readSheet -> {
            final List<CellExtra> cellExtraList = new ArrayList<>();
            final ExtraListener extraListener = new ExtraListener(cellExtraList, HEADER_NUMBER, HEADER_NUMBER);
            ExcelUtil.readSheet(readSheet, extraListener, file, HEADER_NUMBER);
            final MonitorPointExcel dataListener =
                    new MonitorPointExcel(MonitorPointExcel.class,
                            HEADER_NUMBER, cellExtraList, HEADER_NUMBER, readSheet.getSheetName());
            ExcelUtil.readSheet(readSheet, dataListener, file, HEADER_NUMBER);
        });
    }
    
}
