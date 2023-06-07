package com.retail.retailChain.config;

import java.util.Collections;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@EnableWebMvc
public class SpringFoxConfig {
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2)  
          .select()                                  
          .apis(RequestHandlerSelectors.basePackage("com.retail.retailChain.controller"))              
          .paths(PathSelectors.any())                          
          .build()
          .apiInfo(apiInfo());
    }
    private ApiInfo apiInfo() {
        return new ApiInfo(
          "Retail Store API", 
          "List od Retail Store API descriptionI.", 
          "API TOS", 
          "Terms of service", 
          new Contact("Subhajit Majumder", "www.example.com", "subhojit174@gmail.com"), 
          "License of API", "API license URL", Collections.emptyList());
    }

}
