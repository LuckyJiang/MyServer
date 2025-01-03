package com.example.myserver.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * @author DELL
 * @version 1.0.0
 * @ClassName KafkaProducer.java
 * @createTime 2024年06月14日 14:02:00
 **/
@Component
public class KafkaProducer {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    /**
     * 模拟盛泰发送的数据
     * @param message
     */
    public void sendNormalMessage(final String message) {
        kafkaTemplate.send("VIRTUAL_SYSTEM_MESSAGE", message);
    }

}
