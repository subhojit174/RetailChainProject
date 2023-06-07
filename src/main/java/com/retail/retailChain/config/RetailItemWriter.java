package com.retail.retailChain.config;

import java.util.List;

import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import com.retail.retailChain.entity.RetailStore;
import com.retail.retailChain.repository.RetailRepository;


public class RetailItemWriter{
	RetailRepository retailRepository;
	
	public RetailItemWriter(RetailRepository retailRepository) {
		super();
		this.retailRepository = retailRepository;
	}

	public RepositoryItemWriter<RetailStore> write() {
	
		RepositoryItemWriter<RetailStore> writer = new RepositoryItemWriter<>();
		retailRepository.flush();
        writer.setRepository(retailRepository);
        writer.setMethodName("save");
        return writer;
	}
}
