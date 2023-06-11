package com.retail.retailChain.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import org.springframework.kafka.support.serializer.JsonSerializer;
import com.retail.retailChain.entity.RetailStore;

@Configuration
public class KafkaProducerConfig {
    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;
    @Bean
    public Map<String, Object> producerConfig() {
        final HashMap<String, Object> result = new HashMap<>();
        result.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        result.put(
  	          ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, 
  	          StringSerializer.class);
        result.put(
  	          ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, 
  	          JsonSerializer.class);
        return result;
    }
	   @Bean
	    public ProducerFactory<String, List<RetailStore>> producerFactory() {

	        return new DefaultKafkaProducerFactory<>(producerConfig());
	    }

	   
	    @Bean
	    public KafkaTemplate<String, List<RetailStore>> kafkaTemplate() {
	        return new KafkaTemplate<>(producerFactory());
	    }
}
