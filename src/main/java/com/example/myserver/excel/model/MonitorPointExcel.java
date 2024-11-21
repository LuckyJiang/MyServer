package com.example.myserver.excel.model;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;


/**
 * @author by Jiang Xiaomin
 * @desrc:
 */
@Data
public class MonitorPointExcel {

    @ExcelProperty(value = "位置")
    private String location;

    @ExcelProperty(value = "编号")
    private String number;

    @ExcelProperty(value = "盲板编号")
    private String blindPlateNumber;

    @ExcelProperty(value = "公称压力\nPN")
    private String ratedPressure;

    @ExcelProperty(value = "公称管径\nDN")
    private String ratedDiameter;

    @ExcelProperty(value = "挂牌人")
    private String tagPerson;

    @ExcelProperty(value = "挂牌时间")
    private String tagTime;

    @ExcelProperty(value = "管线名称")
    private String pipeLineName;

    @ExcelProperty(value = "管线名称\n确认人")
    private String pipeLineNameConfirmer;

    @ExcelProperty(value = "确认时间")
    private String confirmTime;

    @ExcelProperty(value = {"主管数据", "管径（DN）"})
    private String mainDiameter;

    @ExcelProperty(value = {"主管数据", "原始壁厚(mm)"})
    private String mainOriginThickness;

    @ExcelProperty(value = {"主管数据", "检测最小值(mm)"})
    private String mainMinThickness;

    @ExcelProperty(value = {"主管数据", "检测最大值(mm)"})
    private String mainMaxThickness;

    @ExcelProperty(value = {"主管数据", "减薄率(%)"})
    private String mainReductionRate;

    @ExcelProperty(value = {"倒淋数据", "管径（DN）"})
    private String drainDiameter;

    @ExcelProperty(value = {"倒淋数据", "原始壁厚(mm)"})
    private String drainOriginThickness;

    @ExcelProperty(value = {"倒淋数据", "检测最小值(mm)"})
    private String drainMinThickness;

    @ExcelProperty(value = {"倒淋数据", "检测最大值(mm)"})
    private String drainMaxThickness;

    @ExcelProperty(value = {"倒淋数据", "减薄率(%)"})
    private String drainReductionRate;

    @ExcelProperty(value = {"外观检查情况"})
    private String checkStatus;

    @ExcelProperty(value = {"检测单位"})
    private String inspectionUnit;

    @ExcelProperty(value = "检测情况\n确认人")
    private String inspectionConfirmPerson;

    @ExcelProperty(value = "排查时间")
    private String inspectionTime;

    @ExcelProperty(value = "备注")
    private String remark;
}
