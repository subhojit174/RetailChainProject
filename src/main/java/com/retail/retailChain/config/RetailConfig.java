package com.retail.retailChain.config;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.retail.retailChain.entity.RetailStore;
import com.retail.retailChain.repository.RetailRepository;

import lombok.AllArgsConstructor;

@Configuration
@EnableBatchProcessing
@EnableScheduling

public class RetailConfig {
	@Autowired
    private JobBuilderFactory jobBuilderFactory;
	@Autowired
    private StepBuilderFactory stepBuilderFactory;
	@Autowired
	RetailRepository retailRepository;
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	@Value("${csv.location}")
	private String fileLocation;

    @Bean
    public FlatFileItemReader<RetailStore> reader() {
        FlatFileItemReader<RetailStore> itemReader = new FlatFileItemReader<>();
        System.out.println("Location:"+fileLocation);
        File f=new File(fileLocation+"/retail.csv");

        itemReader.setResource(new FileSystemResource(f));
        //itemReader.setResource(new FileSystemResource("src/main/resources/customers.csv"));

        itemReader.setName("csvReader");
        itemReader.setLinesToSkip(1);
        itemReader.setLineMapper(lineMapper());
        return itemReader;
    }

    private LineMapper<RetailStore> lineMapper() {
        DefaultLineMapper<RetailStore> lineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        //lineTokenizer.setNames(new String[] {"id", "abc"});
        lineTokenizer.setNames(new String[] { "id","sutk","productName","price","date"});

        //, "productName", "price", "date","country"
        //lineTokenizer.setNames( "date", "price","id", "sku","productName");

        BeanWrapperFieldSetMapper<RetailStore> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setConversionService(testConversionService());
        fieldSetMapper.setTargetType(RetailStore.class);
        
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;

    }
    @Bean
    public ConversionService testConversionService() {
        DefaultConversionService testConversionService = new DefaultConversionService();
        DefaultConversionService.addDefaultConverters(testConversionService);
        testConversionService.addConverter(new Converter<String, LocalDate>() {
            @Override
            public LocalDate convert(String text) {
                return LocalDate.parse(text, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            }
        });

        return testConversionService;
    }


    @Bean
    public RetailProcessor processor() {
        return new RetailProcessor();
    }

    @Bean
    public RepositoryItemWriter<RetailStore> writer() {
        RepositoryItemWriter<RetailStore> writer = new RepositoryItemWriter<>();
        writer.setRepository(retailRepository);
        writer.setMethodName("save");
        return writer;
    }

    @Bean
    public Step step1() {

        return stepBuilderFactory.get("csv-step").<RetailStore, RetailStore>chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .taskExecutor(taskExecutor())
                .build();

        
    }

    @Bean
    public Job runJob() {
    	
        Job job= jobBuilderFactory
        		.get("importRetailStore")
        		
                .flow(step1()).end().build();
  
        return job;

    }

    @Bean
    public TaskExecutor taskExecutor() {
        SimpleAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor();
        asyncTaskExecutor.setConcurrencyLimit(10);
        return asyncTaskExecutor;
    }


	


}
