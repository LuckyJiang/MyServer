package com.example.myserver.es.entity.excel;


import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.ToString;

/**
 * @author by Jiang Xiaomin
 * @desrc:
 */
@Data
@ToString
public class ItemCategory {

    @TableField(value = "ID")
    private String id;

    @TableField(value = "NAME")
    private String name;

    @TableField(value = "PARENT_ID")
    private String parentId;
}
