package com.retail.retailChain.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.retail.retailChain.config.AppConstants;
import com.retail.retailChain.controller.RetailStoreController;
import com.retail.retailChain.entity.RetailStore;
import com.retail.retailChain.entity.Role;
import com.retail.retailChain.entity.User;
import com.retail.retailChain.repository.RoleRepository;
import com.retail.retailChain.repository.UserRepository;
import com.retail.retailChain.security.CustomeUserDetailsService;
import com.retail.retailChain.security.JWTAuthenticationEntryPoint;
import com.retail.retailChain.security.JwtTokenHelper;
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
	@MockBean
	private RoleRepository roleRepository;
	@MockBean
	private UserRepository userRepository;
	@MockBean
	private PasswordEncoder passwordEncoder;
	@MockBean
	private JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	@MockBean
	private JwtTokenHelper jwtTokenHelper;
	@MockBean
	private CustomeUserDetailsService customeUserDetailsService;
	List<RetailStore> retailStoreList = new ArrayList<>();
	RetailStore retailStore = new RetailStore();

	public void initialize() {
		retailStore.setId(1L);
		retailStore.setStoreId(1);
		retailStore.setSutk("seb-abc-11");
		retailStore.setProductName("Rice");
		retailStore.setPrice("5000");
		retailStore.setDate(LocalDate.now());
		retailStoreList.add(retailStore);
		Role normalRole = new Role(AppConstants.NORMAL_USER, "ROLE_NORMAL");
		Role adminRole = new Role(AppConstants.ADMIN_USER, "ROLE_ADMIN");
		when(roleRepository.saveAll(any())).thenReturn(Arrays.asList(normalRole, adminRole));
		User user1 = new User(1, "user1", "user1@gmail.com", passwordEncoder.encode("abc"), Arrays.asList(normalRole));
		User admin1 = new User(2, "admin1", "admin1@gmail.com", passwordEncoder.encode("efg"),
				Arrays.asList(adminRole));
		when(userRepository.findByEmail("user1@gmail.com")).thenReturn(Optional.of(user1));
		when(userRepository.findByEmail("admin1@gmail.com")).thenReturn(Optional.of(admin1));
		String username = "admin1@gmail.com";
		when(jwtTokenHelper.extractUsername(any())).thenReturn(username);
		when(customeUserDetailsService.loadUserByUsername(any())).thenReturn(admin1);
		when(jwtTokenHelper.validateToken(any(), any())).thenReturn(true);

	}

	@Test
	void fetchAllRetailStoreListTest() throws Exception {
		initialize();

		given(RetailStoreService.fetchAllRetailStore()).willReturn(retailStoreList);
		mvc.perform(get("/retailStore/").header("Authorization",
				"Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbjFAZ21haWwuY29tIiwiZXhwIjoxNjg2MzM4MTEzLCJpYXQiOjE2ODYzMjAxMTN9.3MTyfKWXeal0SaomwT6yYhufHbLdI1lyhDlNYe4rdBk")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1))).andExpect(jsonPath("$[0].sutk", is(retailStore.getSutk())));
	}

	@Test
	void fetchAllRetailStoreListCriteriaTest() throws Exception {
		initialize();
		given(RetailStoreService.findRetailByStoreIdAndDate(any(), any(), any())).willReturn(retailStoreList);
		mvc.perform(get("/retailStore/storeId/1").header("Authorization",
				"Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbjFAZ21haWwuY29tIiwiZXhwIjoxNjg2MzM4MTEzLCJpYXQiOjE2ODYzMjAxMTN9.3MTyfKWXeal0SaomwT6yYhufHbLdI1lyhDlNYe4rdBk")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1))).andExpect(jsonPath("$[0].sutk", is(retailStore.getSutk())));
	}

	@Test
	void updateRetailStoreTest() throws Exception {
		initialize();
		given(RetailStoreService.updateRetailStore(any(), any())).willReturn(retailStore);
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put("/retailStore/id/1").header("Authorization",
				"Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbjFAZ21haWwuY29tIiwiZXhwIjoxNjg2MzM4MTEzLCJpYXQiOjE2ODYzMjAxMTN9.3MTyfKWXeal0SaomwT6yYhufHbLdI1lyhDlNYe4rdBk")
				.contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8").content(getRetailStoreJson(1L));
		mvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(MockMvcResultHandlers.print());
	}

	private String getRetailStoreJson(Long id) {
		return "{\"id\":" + id
				+ ", \"storeId\":1,\"sutk\":\"seb-abc-11\",\"productName\":\"Rice\",\"price\":\"5000\",\"date\":\""
				+ LocalDate.now() + "\"}";
	}
}
