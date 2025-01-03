package com.example.myserver.kafka;


import cn.hutool.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;


/**
 * @author by Jiang Xiaomin
 * @desrc:
 */
@Service
public class KafkaService implements ApplicationRunner {
    
    @Autowired
    private KafkaProducer kafkaProducer;
    
    private Integer index = 0;
    
    @Override
    public void run(ApplicationArguments args) throws Exception {
        while (true) {
            
            kafkaProducer.sendNormalMessage(createData());
            Thread.sleep(3000);
        }
    }
    
    public String createData() {
        index++;
        index = index%100;
        int i = index%60;
        Map<String, Object> map = new HashMap<>();
        map.put("user", "simtech");
        Map<String, Object> value = new HashMap<>();
        value.put("TI10601",  i);
        value.put("FT10602B", 2 + i);
        value.put("LIA10503", 3 + i);
        value.put("PT10501", 5 + i);
        value.put("LIA10501", 7 + i);
        value.put("M_P2101S", createData2());
        value.put("RX03_RF2", createData2());
        value.put("RX012_RF45", createData2());
        value.put("RX03_RF3", createData2());
        value.put("HC103_RF4", createData2());
        map.put("data", value);
        return JSONUtil.toJsonStr(map);
    }
    
    private String createData2() {
        return String.format("%.2f", new Random().nextDouble());
    }
}
