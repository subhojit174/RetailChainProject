package com.retail.retailChain.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.hibernate.ObjectNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.retail.retailChain.controller.RetailStoreController;
import com.retail.retailChain.entity.RetailStore;
import com.retail.retailChain.exception.NoRecordFoundException;
import com.retail.retailChain.repository.RetailRepository;
@Service
public class RetailStoreServiceImpl implements RetailStoreService {
    Logger logger = LoggerFactory.getLogger(RetailStoreServiceImpl.class);

	@Autowired
	private RetailRepository retailRepository;
	@Override
	public List<RetailStore> fetchAllRetailStore() {
		logger.info("RetailStoreService fetchAllRetailStore method starts");
		List<RetailStore> retailStoreList=retailRepository.findAll();
		if(retailStoreList==null || retailStoreList.size()==0)
			throw new NoRecordFoundException();
		logger.info("RetailStoreService fetchAllRetailStore method ends");
		return retailStoreList;
	}
	@Override
	public List<RetailStore> findRetailByStoreIdAndDate(Optional<Integer> store_id, Optional<LocalDate> date, 
			Optional<String> productName) {
		logger.info("RetailStoreService findRetailByStoreIdAndDate method starts");
		List<RetailStore> retailStoreList=retailRepository.findAllByIdAndEntryDate(store_id, date, productName);
		if(retailStoreList==null || retailStoreList.size()==0)
			throw new NoRecordFoundException();
		logger.info("RetailStoreService findRetailByStoreIdAndDate method ends");
		return retailStoreList;
	}
	@Override
	public RetailStore updateRetailStore(Long id,RetailStore retailStore) {
		logger.info("RetailStoreService updateRetailStore starts");

		RetailStore updateRetailStore=retailRepository.findById(id)
				.orElseThrow(()->new ObjectNotFoundException("Resource not found", String.valueOf(id)));
		updateRetailStore.setStoreId(retailStore.getStoreId());
		updateRetailStore.setSutk(retailStore.getSutk());
		updateRetailStore.setProductName(retailStore.getProductName());
		updateRetailStore.setPrice(retailStore.getPrice());
		updateRetailStore.setDate(retailStore.getDate());
		updateRetailStore=retailRepository.save(updateRetailStore);
		logger.info("RetailStoreService updateRetailStore ends");
		return updateRetailStore;
	}

}
