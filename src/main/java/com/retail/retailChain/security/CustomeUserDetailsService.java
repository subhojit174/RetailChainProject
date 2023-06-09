package com.retail.retailChain.security;

import com.retail.retailChain.repository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.retail.retailChain.entity.User;
import com.retail.retailChain.exception.ResourceNotFoundException;

@Service
public class CustomeUserDetailsService
        implements UserDetailsService
{
    @Autowired
    private UserRepository userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=this.userRepo.findByEmail(username).orElseThrow(()->new ResourceNotFoundException("User","Email:"+username, 0));
        return user;
    }
}