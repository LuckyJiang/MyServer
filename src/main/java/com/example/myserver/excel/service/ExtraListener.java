package com.example.myserver.excel.service;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.CellExtra;

import java.util.List;
import java.util.Map;

public class ExtraListener extends AnalysisEventListener<Map<Integer, String>> {

    private final List<CellExtra> cellExtraList;

    private final Integer maxHeadLevel;

    private final Integer headerRowNum;
    
    public ExtraListener(final List<CellExtra> cellExtraList, 
                         final Integer maxHeadLevel,
                         final Integer headerRowNum) {
        this.cellExtraList = cellExtraList;
        this.maxHeadLevel = maxHeadLevel;
        this.headerRowNum = headerRowNum;
    }

    @Override
    public void invoke(final Map<Integer, String> integerStringMap, final AnalysisContext analysisContext) {
    }

    @Override
    public void doAfterAllAnalysed(final AnalysisContext analysisContext) {
    }

    @Override
    public void extra(final CellExtra extra, final AnalysisContext context) {
        switch (extra.getType()) {
            case MERGE:
                if (extra.getRowIndex() - headerRowNum < maxHeadLevel) {
                    cellExtraList.add(extra);
                }
                break;
            default:
        }
    }
}
