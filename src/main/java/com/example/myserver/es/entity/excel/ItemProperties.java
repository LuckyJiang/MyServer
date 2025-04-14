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
public class ItemProperties {
    
    @TableField(value = "REPOSITORY_VERSION")
    private String repositoryVersion;

    @TableField(value = "ITEM_ID")
    private String itemId;

    @TableField(value = "PROPERTIES")
    private String properties;
}
