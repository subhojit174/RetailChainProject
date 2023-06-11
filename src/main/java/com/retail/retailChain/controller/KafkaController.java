package com.retail.retailChain.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.retail.retailChain.entity.RetailStore;
@EnableKafka
@RestController
@RequestMapping("/api/kafka")
public class KafkaController {
	List<RetailStore> retailStoreList=new ArrayList<>();

@GetMapping("/")
public List<RetailStore> consumeJsonMsg(){
	return this.retailStoreList;
}
@KafkaListener(groupId="group1",topics="${spring.kafka.topic-name}",
containerFactory = "retailListKafkaListenerContainerFactory")
public List<RetailStore> getJsonMessageFromTopic(List<RetailStore> retailStoreList){
	System.out.println(retailStoreList);
	ObjectMapper mapper = new ObjectMapper();
	mapper.findAndRegisterModules();
	List<RetailStore> convertedRetailStoreList = mapper.convertValue(
			retailStoreList,
		    new TypeReference<List<RetailStore>>() { });
	convertedRetailStoreList.stream().forEach(System.out::println);
	
	this.retailStoreList=convertedRetailStoreList;
	return this.retailStoreList;
}
}
