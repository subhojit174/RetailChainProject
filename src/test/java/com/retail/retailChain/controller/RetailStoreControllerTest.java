package com.retail.retailChain.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.retail.retailChain.controller.RetailStoreController;
import com.retail.retailChain.entity.RetailStore;
import com.retail.retailChain.service.RetailStoreServiceImpl;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.*;


@RunWith(SpringRunner.class)
@WebMvcTest(RetailStoreController.class)
class RetailStoreControllerTest {

	   @Autowired
	    private MockMvc mvc;
	    @MockBean
	    private RetailStoreServiceImpl RetailStoreService;
		List<RetailStore> retailStoreList=new ArrayList<>();
		RetailStore retailStore=new RetailStore();



		public void initialize() {
			retailStore.setId(1L);
			retailStore.setStoreId(1);
			retailStore.setSutk("seb-abc-11");
			retailStore.setProductName("Rice");
			retailStore.setPrice("5000");
			retailStore.setDate(LocalDate.now());
			retailStoreList.add(retailStore);

		}
	    @Test
	    void fetchAllRetailStoreListTest() throws Exception {
	    	initialize();
	    	given(RetailStoreService.fetchAllRetailStore()).willReturn(retailStoreList);
	        mvc.perform(get("/retailStore/")
	        	      .contentType(MediaType.APPLICATION_JSON))
	        	      .andExpect(status().isOk())
	        	      .andExpect(jsonPath("$", hasSize(1)))
	        	      .andExpect(jsonPath("$[0].sutk", is(retailStore.getSutk())));
	    }
	    @Test
	    void fetchAllRetailStoreListCriteriaTest() throws Exception {
	    	initialize();
		  	given(RetailStoreService.findRetailByStoreIdAndDate(any(), any(), any()))
		  	.willReturn(retailStoreList);
	        mvc.perform(get("/retailStore/storeId/1")
	        	      .contentType(MediaType.APPLICATION_JSON))
	        	      .andExpect(status().isOk())
	        	      .andExpect(jsonPath("$", hasSize(1)))
	        	      .andExpect(jsonPath("$[0].sutk", is(retailStore.getSutk())));
	    }
	    @Test
	    void updateRetailStoreTest() throws Exception {
	    	initialize();
	    	given(RetailStoreService.updateRetailStore(any(), any())).willReturn(retailStore);
	        MockHttpServletRequestBuilder builder =
	                MockMvcRequestBuilders.put("/retailStore/id/1")
	                                      .contentType(MediaType.APPLICATION_JSON_VALUE)
	                                      .accept(MediaType.APPLICATION_JSON)
	                                      .characterEncoding("UTF-8")
	                                      .content(getRetailStoreJson(1L));
	        mvc.perform(builder)
            .andExpect(MockMvcResultMatchers.status()
                                            .isOk())
            /*.andExpect(MockMvcResultMatchers.content()
                                            .string("Article created."))*/
            .andDo(MockMvcResultHandlers.print());
	    }
	    private String getRetailStoreJson(Long id) {
	        return "{\"id\":" + id + ", \"storeId\":1,\"sutk\":\"seb-abc-11\",\"productName\":\"Rice\",\"price\":\"5000\",\"date\":\""+LocalDate.now()+"\"}";
	    }
}
