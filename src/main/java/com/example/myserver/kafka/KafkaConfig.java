package com.example.myserver.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.ProducerListener;

/**
 * @author DELL
 * @version 1.0.0
 * @ClassName KafkaConfig.java
 * @createTime 2024年06月14日 14:17:00
 */


@Configuration
@Slf4j
public class KafkaConfig {

    @Autowired
    private ProducerFactory producerFactory;

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        final KafkaTemplate<String, Object> kafkaTemplate = new KafkaTemplate<String, Object>(producerFactory);
        kafkaTemplate.setProducerListener(new ProducerListener<String, Object>() {

            @Override
            public void onSuccess(final ProducerRecord<String, Object> producerRecord,
                                  final RecordMetadata recordMetadata) {
                log.info("发送成功 " + producerRecord.toString());
            }

            public void onSuccess(final String topic, final Integer partition,
                                  final String key, final Object value,
                                  final RecordMetadata recordMetadata) {
                log.info("发送成功 topic = " + topic + " ; partion = " + partition + ";"
                        + " key = " + key + " ; value=" + value);
            }


            public void onError(final ProducerRecord<String, Object> producerRecord,
                                final Exception exception) {
                log.error("发送失败" + producerRecord.toString());
                log.error(exception.getMessage());
            }

            public void onError(final String topic, final Integer partition, final String key,
                                final Object value, final Exception exception) {
                log.error("发送失败" + "topic = " + topic + " ; partion = " + partition + ";"
                        + " key = " + key + " ; value=" + value);
                log.error(exception.getMessage());
            }
        });
        return kafkaTemplate;
    }

}
