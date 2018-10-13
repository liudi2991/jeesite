/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.thinkgem.jeesite.common.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 
 * @author yaohailu
 * @version 2018年9月28日
 */
@Configuration
@EnableSwagger2
@EnableWebMvc
@ComponentScan("com.thinkgem.jeesite.modules.cms.web.front.api")
public class SpringFoxConfig extends WebMvcConfigurationSupport {
	@Bean
	public Docket apiDocket() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("JeeSite Api Demo").select()
				.apis(RequestHandlerSelectors.any()).paths(PathSelectors.any()).build().apiInfo(getApiInfo());
	}

	private ApiInfo getApiInfo() {
		return new ApiInfo("JeeSite工作室 Swagger教程", "Swagger在线使用教程", "1.0.1", "http://www.jeesite.com",
				new Contact("长春叭哥", "http://www.jeesite.com", "admin@chinadays.cn"), "LICENSE", "LICENSE URL",
				Collections.emptyList());
	}
}
