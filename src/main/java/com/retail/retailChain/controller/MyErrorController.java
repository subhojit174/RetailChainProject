package com.retail.retailChain.controller;

import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import springfox.documentation.annotations.ApiIgnore;
@Component
@RequestMapping("my-error-controller")
@ApiIgnore 
public class MyErrorController extends BasicErrorController {

	public MyErrorController(ErrorAttributes errorAttributes, ServerProperties serverProperties) {
		super(errorAttributes, serverProperties.getError());
		// TODO Auto-generated constructor stub
	}

}
