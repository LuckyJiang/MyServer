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
public class ItemReference {

    @TableField(value = "REPOSITORY_VERSION")
    private String repositoryVersion;

    @TableField(value = "ID")
    private String id;

    @TableField(value = "TARGET_ITEM_ID")
    private String targetItemId;

    @TableField(value = "SOURCE_DOCUMENT_ID")
    private String sourceDocumentId;

    @TableField(value = "TARGET_DOCUMENT_ID")
    private String targetDocumentId;
}
