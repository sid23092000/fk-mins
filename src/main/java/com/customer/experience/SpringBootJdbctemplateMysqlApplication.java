package com.customer.experience;

import com.customer.experience.controller.ListController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringBootJdbctemplateMysqlApplication {

	public static void main(String[] args) {
		 SpringApplication.run(SpringBootJdbctemplateMysqlApplication.class, args);
//		ListController listController = context.getBean(ListController.class);
	}

}
