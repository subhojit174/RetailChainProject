package com.retail.retailChain.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.retail.retailChain.entity.RetailStore;

public interface RetailRepositoryCustom {
	List<RetailStore> findAllByIdAndEntryDate(Optional<Integer> retail_id,Optional<LocalDate> date, Optional<String> productName);

}
