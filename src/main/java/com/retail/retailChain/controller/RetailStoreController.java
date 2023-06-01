package com.retail.retailChain.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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

}
