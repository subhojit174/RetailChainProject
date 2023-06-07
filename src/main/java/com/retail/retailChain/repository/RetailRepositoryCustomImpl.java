package com.retail.retailChain.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;

import com.retail.retailChain.entity.RetailStore;
import com.retail.retailChain.service.RetailStoreServiceImpl;
@Repository
public class RetailRepositoryCustomImpl implements RetailRepositoryCustom {
    Logger logger = LoggerFactory.getLogger(RetailRepositoryCustomImpl.class);
	@Autowired
	EntityManager em;

	@Override
	public List<RetailStore> findAllByIdAndEntryDate(Optional<Integer> store_id,
	Optional<LocalDate>  localDate, Optional<String> productName) {
		logger.info("RetailRepository findAllByIdAndEntryDate method starts");
	    CriteriaBuilder cb = em.getCriteriaBuilder();
	    CriteriaQuery<RetailStore> cq = cb.createQuery(RetailStore.class);
	    Root<RetailStore> retailStore = cq.from(RetailStore.class);
	    List<Predicate> predicates = new ArrayList<>();
	    if (store_id.isPresent())
	    {
	        predicates.add(cb.equal(retailStore.get("storeId"), store_id.get()));

	    }
	    if(localDate.isPresent()) {
	    	predicates.add(cb.equal(retailStore.get("date"), localDate.get()));
	    }
	    if(productName.isPresent()) {
	    	predicates.add(cb.equal(retailStore.get("productName"), productName.get()));
	    	
	    }
	    cq.where(predicates.toArray(new Predicate[0]));

		logger.info("RetailRepository findAllByIdAndEntryDate method ends");

		return em.createQuery(cq).getResultList();
	}

}
