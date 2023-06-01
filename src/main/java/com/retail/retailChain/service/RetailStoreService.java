package com.retail.retailChain.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


import com.retail.retailChain.entity.RetailStore;

public interface RetailStoreService {

	public List<RetailStore> fetchAllRetailStore();
	public List<RetailStore> findRetailByStoreIdAndDate(Integer store_id, LocalDate date);
}
