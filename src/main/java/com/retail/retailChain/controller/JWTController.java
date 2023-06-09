package com.retail.retailChain.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.retail.retailChain.security.CustomeUserDetailsService;
import com.retail.retailChain.security.JwtAuthResponse;
import com.retail.retailChain.security.JwtRequest;
import com.retail.retailChain.security.JwtTokenHelper;

@RestController
@RequestMapping("/api/v1/auth")
public class JWTController {
    Logger logger = LoggerFactory.getLogger(JWTController.class);
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private CustomeUserDetailsService userDetailsService;
    @Autowired
    private JwtTokenHelper jwtHelper;
    @PostMapping("/generateToken")
    public ResponseEntity<?> generateToken(@RequestBody JwtRequest request) throws Exception {
    	logger.info("JWTController generateToken methhod starts");
        try{
            this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken( request.getUsername(),request.getPassword()));

        }
        catch (UsernameNotFoundException ex){
            ex.printStackTrace();
            throw new Exception("User not found");

        }
        catch (BadCredentialsException ex){
            ex.printStackTrace();
            throw new BadCredentialsException("Bad Credential");

        }
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());
        String token = this.jwtHelper.generateToken(userDetails);
    	logger.info("JWTController generateToken methhod ends");

        return ResponseEntity.ok(new JwtAuthResponse(token));
    }
}
