package com.example.myserver.es.entity.excel;


import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.ToString;

import java.util.Map;

/**
 * @author by Jiang Xiaomin
 * @desrc:
 */
@Data
@ToString
public class ItemProperty {
    
    private String name;

    private Map<String, String> value;

}
