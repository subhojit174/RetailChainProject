package com.retail.retailChain.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.retail.retailChain.entity.RetailStore;

@Configuration
public class KafkaConsumerConfig {
    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;
	 @Bean
	    public ConsumerFactory<String, List<RetailStore>> retailListConsumerFactory() {
	        JsonDeserializer<List<RetailStore>> deserializer = new JsonDeserializer<>(List.class);
	        deserializer.setRemoveTypeHeaders(false);
	        deserializer.addTrustedPackages("*");
	        deserializer.setUseTypeMapperForKey(true);

	        Map<String,Object> configs=new HashMap<>();
	        configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapAddress);
	        configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
	        configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, deserializer);
	        configs.put(ConsumerConfig.GROUP_ID_CONFIG,"group1");
	        return new DefaultKafkaConsumerFactory<>(configs,new StringDeserializer(),deserializer);
	    }
	    @Bean
	    public ConcurrentKafkaListenerContainerFactory<String,List<RetailStore>> retailListKafkaListenerContainerFactory(){
	        ConcurrentKafkaListenerContainerFactory<String,List<RetailStore>> concurrentKafkaListenerContainerFactory=new ConcurrentKafkaListenerContainerFactory<>();
	        concurrentKafkaListenerContainerFactory.setConsumerFactory(retailListConsumerFactory());
	        return concurrentKafkaListenerContainerFactory;
	    }
}
