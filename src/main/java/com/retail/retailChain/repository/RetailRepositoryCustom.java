package com.retail.retailChain.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.retail.retailChain.entity.RetailStore;

public interface RetailRepositoryCustom {
	List<RetailStore> findAllByIdAndEntryDate(Integer retail_id,LocalDate date);

}
