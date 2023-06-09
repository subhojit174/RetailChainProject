package com.retail.retailChain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.retail.retailChain.config.AppConstants;
import com.retail.retailChain.entity.Role;
import com.retail.retailChain.entity.User;
import com.retail.retailChain.repository.RoleRepository;
import com.retail.retailChain.repository.UserRepository;

@SpringBootApplication
public class RetaiChainApplication implements CommandLineRunner{
	@Autowired
	private RoleRepository roleRepo;
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private PasswordEncoder passwordEncoder;
	public static void main(String[] args) {
		SpringApplication.run(RetaiChainApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		try {
			Role normalRole = new Role(AppConstants.NORMAL_USER, "ROLE_NORMAL");
			Role adminRole = new Role(AppConstants.ADMIN_USER, "ROLE_ADMIN");
			List<Role> roles = Arrays.asList(normalRole, adminRole);
			List<Role> resultRole = this.roleRepo.saveAll(roles);
			resultRole.forEach(System.out::println);
			User user1=new User(1,"user1","user1@gmail.com",passwordEncoder.encode("abc")
					,Arrays.asList(normalRole));
			User admin1=new User(2,"admin1","admin1@gmail.com",passwordEncoder.encode("efg")
					,Arrays.asList(adminRole));
			if(!userRepo.findByEmail("user1@gmail.com").isPresent())
				userRepo.save(user1);
			if(!userRepo.findByEmail("admin1@gmail.com").isPresent())
				userRepo.save(admin1);		
			
		}
		catch (Exception ex){
			ex.printStackTrace();
		}
		
	}

}
