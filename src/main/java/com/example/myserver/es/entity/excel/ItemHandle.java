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
public class ItemHandle {

    @TableField(value = "REPOSITORY_VERSION")
    private String repositoryVersion;

    @TableField(value = "REFERENCE_ID")
    private String referenceId;
    
    @TableField(value = "DOCUMENT_ID")
    private String documentId;
    
    @TableField(value = "VALUE")
    private String value;
}
