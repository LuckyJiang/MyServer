package com.example.myserver.es.bboss.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "spring.elasticsearch.bboss")
public class BBossESProperties {
    
    private String elasticUser;
    
    private String elasticPassword;
    
    private Elasticsearch elasticsearch;

    private String timeoutSocket;

    public Map<String, Object> buildProperties() {
        final Map<String, Object> properties = new HashMap<String, Object>();
        properties.put("elasticUser", this.elasticUser);
        properties.put("elasticPassword", this.elasticPassword);
        properties.put("elasticsearch.rest.hostNames", this.getElasticsearch().getRest().getHostNames());
        properties.put("elasticsearch.showTemplate", this.getElasticsearch().getShowTemplate());
        properties.put("http.timeoutSocket", this.timeoutSocket);
        return properties;
    }

    @Getter
    @Setter
    public static class Elasticsearch {
        
        private Rest rest;
        
        private String showTemplate;

    }

    @Getter
    @Setter
    public static class Rest {
        
        private String hostNames;

    }
    
}
