package com.retail.retailChain.config;

import org.springframework.batch.item.ItemProcessor;

import com.retail.retailChain.entity.RetailStore;

public class RetailProcessor  implements ItemProcessor<RetailStore,RetailStore>  {

	@Override
	public RetailStore process(RetailStore retailStore) throws Exception {
		// TODO Auto-generated method stub
		return retailStore;
	}

}
