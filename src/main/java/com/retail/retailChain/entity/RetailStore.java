package com.retail.retailChain.entity;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "RETAIL_STORE")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RetailStore {
	
	@Id
	@GeneratedValue
	private long id;
	@Column(name="STORE_ID")
	private int storeId;
	@Column(name="SUTK_NAME")
	private String sutk;

	@Column(name="PRODUCT_NAME")
	private String productName;
	@Column(name="PRODUCT_PRICE")
	private String price;
	@Column(name="ENTRY_DATE")
	private LocalDate date;

}
