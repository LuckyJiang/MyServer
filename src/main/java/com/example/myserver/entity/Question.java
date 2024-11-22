package com.example.myserver.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 试题表
 * </p>
 *
 * @author 
 * @since 2024-11-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("question")
@ApiModel(value="Question对象", description="试题表")
public class Question implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @TableId(value = "ID", type = IdType.AUTO)
    private String id;

    @ApiModelProperty(value = "试题内容")
    private String content;

    @ApiModelProperty(value = "试题难度等级（简单、中等、复杂）")
    private String difficulty;

    @ApiModelProperty(value = "试题类（单选题、多选题、判断题、填空题、问答题）")
    private String type;

    @ApiModelProperty(value = "试题分数")
    private Double score;

    @ApiModelProperty(value = "参考答案")
    private String answer;

    @ApiModelProperty(value = "答案解析")
    private String explanation;

    @ApiModelProperty(value = "题库ID")
    private String questionBankId;

    @ApiModelProperty(value = "删除标识")
    private Boolean deleteFlag;


}
