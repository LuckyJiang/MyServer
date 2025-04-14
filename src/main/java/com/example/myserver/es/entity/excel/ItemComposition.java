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
public class ItemComposition {

    @TableField(value = "REPOSITORY_VERSION")
    private String repositoryVersion;

    @TableField(value = "ID")
    private String id;


    @TableField(value = "COMPOSITION_ITEM_ID")
    private String compositionItemId;

    @TableField(value = "ITEM_ID")
    private String itemId;

}
