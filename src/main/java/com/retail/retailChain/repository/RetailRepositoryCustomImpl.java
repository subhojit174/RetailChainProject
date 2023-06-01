package com.retail.retailChain.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.retail.retailChain.entity.RetailStore;
@Repository
public class RetailRepositoryCustomImpl implements RetailRepositoryCustom {
	@Autowired
	EntityManager em;

	@Override
	public List<RetailStore> findAllByIdAndEntryDate(Integer store_id, LocalDate localDate) {
	    CriteriaBuilder cb = em.getCriteriaBuilder();
	    CriteriaQuery<RetailStore> cq = cb.createQuery(RetailStore.class);
	    Root<RetailStore> retailStore = cq.from(RetailStore.class);
	    List<Predicate> predicates = new ArrayList<>();
	    if (store_id!=null)
	    {
	    	System.out.println(store_id);
	        predicates.add(cb.equal(retailStore.get("storeId"), store_id));

	    }
	    if(localDate!=null) {
	    	predicates.add(cb.equal(retailStore.get("date"), localDate));
	    }
	    cq.where(predicates.toArray(new Predicate[0]));

		// TODO Auto-generated method stub
		return em.createQuery(cq).getResultList();
	}

}
