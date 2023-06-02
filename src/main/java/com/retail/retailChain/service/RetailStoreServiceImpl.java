package com.retail.retailChain.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.hibernate.ObjectNotFoundException;
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
	public List<RetailStore> findRetailByStoreIdAndDate(Integer store_id, Optional<LocalDate> date, 
			Optional<String> productName) {
		// TODO Auto-generated method stub
		return retailRepository.findAllByIdAndEntryDate(store_id, date, productName);
	}
	@Override
	public RetailStore updateRetailStore(Long id,RetailStore retailStore) {
		RetailStore updateRetailStore=retailRepository.findById(id)
				.orElseThrow(()->new ObjectNotFoundException("Resource not found", String.valueOf(id)));
		updateRetailStore.setStoreId(retailStore.getStoreId());
		updateRetailStore.setSutk(retailStore.getSutk());
		updateRetailStore.setProductName(retailStore.getProductName());
		updateRetailStore.setPrice(retailStore.getPrice());
		updateRetailStore.setDate(retailStore.getDate());
		updateRetailStore=retailRepository.save(updateRetailStore);
		// TODO Auto-generated method stub
		return updateRetailStore;
	}

}
