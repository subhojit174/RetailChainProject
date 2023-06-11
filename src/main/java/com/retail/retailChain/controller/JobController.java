package com.retail.retailChain.controller;

import java.io.File;
import java.util.List;

import javax.batch.operations.JobRestartException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.retail.retailChain.entity.RetailStore;
import com.retail.retailChain.repository.RetailRepository;

@Component
@EnableScheduling
public class JobController {
	 	@Autowired
	    private JobLauncher jobLauncher;
	 	@Autowired
	    private Job job;
		@Value("${csv.location}")
		private String fileLocation;
		private static final Logger log = LoggerFactory.getLogger(JobController.class);
	    @Autowired
	    private KafkaTemplate<String,List<RetailStore>> kafkaTemplate;
	    @Value("${spring.kafka.topic-name}")
	    private String topicName;
	    @Autowired
	    private RetailRepository retailRepository;


	    @Bean
	    @Scheduled(fixedRate = 5000)
	    public void importCsvToDBJob() {
	        File f=new File(fileLocation+"/retail.csv");
	        if(f.exists()) {
	        JobParameters jobParameters = new JobParametersBuilder()
	                .addLong("startAt", System.currentTimeMillis())
	                .toJobParameters();
	        try {
	            jobLauncher.run(job, jobParameters);
	            Message<List<RetailStore>> message =  MessageBuilder
	                    .withPayload(retailRepository.findAll())
	                    .setHeader(KafkaHeaders.TOPIC, topicName)
	                    .build();
	            kafkaTemplate.send(message);
	            deleteFie(f);
	        } catch (Exception  e) {
	            e.printStackTrace();
	        }
	        }
	    }
	    private void deleteFie(File f) {

	  	      if(f.delete())                       
	  	      {  
	  	      log.info(f.getName() + " deleted");  
	  	      }  
	  	      else  
	  	      {  
	  	      log.info(f.getName() +" deletion failed");  
	  	      }

	        }
	    
}
