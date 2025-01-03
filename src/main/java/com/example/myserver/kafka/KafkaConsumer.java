package com.example.myserver.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author DELL
 * @version 1.0.0
 * @ClassName KafkaConsumer.java
 * @createTime 2024年06月28日 16:58:00
 */


@Component
@Slf4j
public class KafkaConsumer {
    
    //作为消费者监听 镇海传来的数据
    @KafkaListener(topics = "VIRTUAL_SYSTEM_MESSAGE")
    public void onNormalMessage(final String record) {
        log.info("从镇海传来的数据：{}", record);
    }
    
}
