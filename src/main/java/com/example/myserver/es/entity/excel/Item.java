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
public class Item {

    @TableField(value = "REPOSITORY_VERSION")
    private String repositoryVersion;

    @TableField(value = "ID")
    private String id;


    @TableField(value = "CATEGORY")
    private String category;

    @TableField(value = "NAME")
    private String name;

    @TableField(value = "TAG")
    private String tag;
}
