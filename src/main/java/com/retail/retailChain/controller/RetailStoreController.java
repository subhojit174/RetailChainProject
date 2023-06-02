package com.retail.retailChain.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.retail.retailChain.entity.RetailStore;
import com.retail.retailChain.service.RetailStoreService;

@RestController
@RequestMapping("/retailStore")
public class RetailStoreController {
	@Autowired
	private RetailStoreService retailStoreService;
	@GetMapping("/")
	public List<RetailStore> fetchAllRetailStoreList(){
		return retailStoreService.fetchAllRetailStore();
	}
	@GetMapping(path= {"/storeId/{id}","/storeId/{id}/date/{date}","/storeId/{id}/date/{date}/product/{productName}"})
	public List<RetailStore> fetchAllRetailStoreList(@PathVariable Integer id,@PathVariable(value="date")
	@DateTimeFormat(pattern = "yyyy-MM-dd") Optional<LocalDate> date,@PathVariable Optional<String> productName){
		//System.out.println("date:"+date.isPresent());
		return retailStoreService.findRetailByStoreIdAndDate(id, date, productName);
	}
	@PutMapping("/id/{id}")
	public RetailStore updateRetailStore(@PathVariable Long id, @RequestBody RetailStore retailStore) {
		return retailStoreService.updateRetailStore(id, retailStore);
	}
}

