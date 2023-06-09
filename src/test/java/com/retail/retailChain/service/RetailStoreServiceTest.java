package com.retail.retailChain.service;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.retail.retailChain.entity.RetailStore;
import com.retail.retailChain.repository.RetailRepository;

@ExtendWith(MockitoExtension.class)
public class RetailStoreServiceTest {
	@InjectMocks
	RetailStoreServiceImpl retailService;
	@Mock
	private RetailRepository retailRepository;
	List<RetailStore> retailStoreList=new ArrayList<>();
	RetailStore retailStore=new RetailStore();


	@Before
	public void init() {
		MockitoAnnotations.openMocks(this);
		retailStore.setId(1);
		retailStore.setStoreId(1);
		retailStore.setSutk("seb-abc-11");
		retailStore.setProductName("Rice");
		retailStore.setPrice("5000");
		retailStore.setDate(LocalDate.now());
		retailStoreList.add(retailStore);

	}
	@Test
	public void  fetchAllRetailStoreTest() {
		when(retailRepository.findAll()).thenReturn(retailStoreList);
		List<RetailStore> resultRetailStoreList=retailService.fetchAllRetailStore();
		assertEquals(resultRetailStoreList.size(), retailStoreList.size());
		verify(retailRepository,times(1)).findAll();
		
	}
	@Test
	public void findRetailByStoreIdAndDateTest() {
		
		when(retailRepository.findAllByIdAndEntryDate(any(), any(),any())).thenReturn(retailStoreList);
		List<RetailStore> resultRetailStoreList=retailService.findRetailByStoreIdAndDate(Optional.of(1), 
				Optional.of(LocalDate.now()), Optional.of("Rice"));
		assertEquals(resultRetailStoreList.size(), retailStoreList.size());
		verify(retailRepository,times(1)).findAllByIdAndEntryDate(any(), any(),any());
		
	}
	@Test
	public void updateRetailStoreTest() {
		when(retailRepository.findById(any())).thenReturn(Optional.of(retailStore));
		when(retailRepository.save(any())).thenReturn(retailStore);
		RetailStore updateRetailStore=retailService.updateRetailStore(1L, retailStore);
		assertEquals(retailStore.getStoreId(), updateRetailStore.getStoreId());
		verify(retailRepository,times(1)).findById(any());
		verify(retailRepository,times(1)).save(any());
	}

}
