package com.retail.retailChain.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.retail.retailChain.entity.RetailStore;

public interface RetailStoreService {

	public List<RetailStore> fetchAllRetailStore();
	public List<RetailStore> findRetailByStoreIdAndDate(Optional<Integer> store_id, Optional<LocalDate> date, 
			Optional<String> productName);
	public RetailStore updateRetailStore(Long id,RetailStore retailStore);
}
