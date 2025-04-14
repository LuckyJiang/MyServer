package com.example.myserver.es.bboss.config;


import org.frameworkset.elasticsearch.ElasticSearchHelper;
import org.frameworkset.elasticsearch.boot.ElasticSearchBoot;
import org.frameworkset.elasticsearch.client.ClientInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * @author by Jiang Xiaomin
 * @desrc:
 */
@Configuration
public class EsConfig {

    @Autowired
    private BBossESProperties properties;

    @PostConstruct
    private void initBBoss() {
        final Map<String, Object> ps = properties.buildProperties();
        ElasticSearchBoot.boot(ps, true);
    }

    @Bean
    @ConditionalOnExpression("${my.elasticsearch.enable}")
    public ClientInterface esClient() {
        return ElasticSearchHelper.getConfigRestClientUtil("es-bboss/operation.xml");
    }
}
