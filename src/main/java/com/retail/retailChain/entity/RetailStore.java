package com.retail.retailChain.entity;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

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
    @NotBlank(message = "Store Id  is mandatory")
	@Column(name="STORE_ID")
	private int storeId;
    @NotBlank(message = "SUTK  is mandatory")
	@Column(name="SUTK_NAME")
	private String sutk;
    @NotBlank(message = "Product Name  is mandatory")
	@Column(name="PRODUCT_NAME")
	private String productName;
    @NotBlank(message = "Product Price  is mandatory")

	@Column(name="PRODUCT_PRICE")
	private String price;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd") 
    @JsonFormat(pattern = "yyyy-MM-dd") 
	@Column(name="ENTRY_DATE")
	private LocalDate date;

}
