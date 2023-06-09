package com.retail.retailChain.security;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {
    Logger logger = LoggerFactory.getLogger(JWTAuthenticationFilter.class);

    @Autowired
    private JwtTokenHelper jwtHelper;
    @Autowired
    private CustomeUserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
    	logger.info("JWTAuthenticationFilter doFilterInternal method starts" );
        String requestHeader = httpServletRequest.getHeader("Authorization");
        String username=null;
        String jwtToken=null;
        if(requestHeader!=null && requestHeader.startsWith("Bearer ")){
            jwtToken=requestHeader.substring(7);
            System.out.println("jwtToken:"+jwtToken);
            try{
                username = this.jwtHelper.extractUsername(jwtToken);
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            //System.out.println(SecurityContextHolder.getContext().getAuthentication());
            if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null && this.jwtHelper.validateToken(jwtToken,userDetails)){
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new
                        UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
            else{
            	logger.info("Token invalid");
            }
        }
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    	logger.info("JWTAuthenticationFilter doFilterInternal method ends" );

    }
}
