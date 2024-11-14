package com.example.myserver.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 题库表
 * </p>
 *
 * @author 
 * @since 2024-10-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class QuestionBank implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private String id;

    /**
     * 编码
     */
    private String code;

    /**
     * 题库名称
     */
    private String name;

    /**
     * 题库描述
     */
    private String description;

    /**
     * 删除标识
     */
    private Integer deleteFlag;
    
}
