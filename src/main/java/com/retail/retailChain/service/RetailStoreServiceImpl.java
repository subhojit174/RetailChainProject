package com.retail.retailChain.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.retail.retailChain.entity.RetailStore;
import com.retail.retailChain.repository.RetailRepository;
@Service
public class RetailStoreServiceImpl implements RetailStoreService {

	@Autowired
	private RetailRepository retailRepository;
	@Override
	public List<RetailStore> fetchAllRetailStore() {
		// TODO Auto-generated method stub
		
		return retailRepository.findAll();
	}
	@Override
	public List<RetailStore> findRetailByStoreIdAndDate(Integer store_id, LocalDate date) {
		// TODO Auto-generated method stub
		return retailRepository.findAllByIdAndEntryDate(store_id, date);
	}

}
