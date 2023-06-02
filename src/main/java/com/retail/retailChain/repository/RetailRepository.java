package com.retail.retailChain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.retail.retailChain.entity.RetailStore;


public interface RetailRepository extends JpaRepository<RetailStore,Long>,RetailRepositoryCustom {
	//public RetailStore findByProductName();

}
