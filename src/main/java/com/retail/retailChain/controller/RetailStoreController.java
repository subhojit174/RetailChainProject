package com.retail.retailChain.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
    Logger logger = LoggerFactory.getLogger(RetailStoreController.class);
    
	@Autowired
	private RetailStoreService retailStoreService;
	@GetMapping("/")
	public List<RetailStore> fetchAllRetailStoreList(){
		return retailStoreService.fetchAllRetailStore();
	}
	@GetMapping(path= {"/date/{date}","/date/{date}/product/{productName}","/storeId/{id}/date/{date}/product/{productName}",
			"/storeId/{id}/date/{date}","/storeId/{id}","/product/{productName}"})
	public List<RetailStore> fetchAllRetailStoreList(@PathVariable Map<String, String> pathVar){
		  logger.info("RetailStoreController fetchAllRetailStoreList method starts");
		  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		  LocalDate lDate=null;
		  String storeId=pathVar.get("id");
		  Integer id=null;
		  if(storeId!=null) {
		  //System.out.println(storeId);
		  id=Integer.parseInt(storeId);
		  }
		  String date=pathVar.get("date");
		  String productName=pathVar.get("productName");
		  if(date!=null) {
			 lDate=LocalDate.parse(date,formatter);
			 if(lDate==null)
				 throw new DateTimeParseException("Wrong Date type",date,0);
		  }
		  logger.info("RetailStoreController fetchAllRetailStoreList method ends");
		  return retailStoreService.findRetailByStoreIdAndDate(Optional.ofNullable(id), Optional.ofNullable(lDate)
				  ,Optional.ofNullable(productName));
	}
	@PutMapping("/id/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public RetailStore updateRetailStore(@PathVariable Long id,@Valid @RequestBody RetailStore retailStore) {
		  logger.info("RetailStoreController updateRetailStore method starts");

		  return retailStoreService.updateRetailStore(id, retailStore);
	}
}

