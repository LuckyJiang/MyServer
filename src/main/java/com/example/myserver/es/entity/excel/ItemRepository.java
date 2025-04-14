package com.example.myserver.es.entity.excel;


import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author by Jiang Xiaomin
 * @desrc:
 */
@Data
@ToString
public class ItemRepository {

    @ExcelProperty(value = "ID")
    private String id;

    @ExcelProperty(value = "NAME")
    private String name;

    @ExcelProperty(value = "TYPE")
    private String type;
    
    @ExcelProperty(value = "PARENT_TYPE")
    private String parentType;
    
}
