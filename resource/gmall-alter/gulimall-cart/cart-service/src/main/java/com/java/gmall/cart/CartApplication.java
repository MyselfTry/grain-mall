package com.java.gmall.cart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author jiangli
 * @since 2020/1/29 12:18
 */
@SpringBootApplication
@EnableFeignClients
public class CartApplication {

	public static void main(String[] args) {
		SpringApplication.run(CartApplication.class,args);
	}
}
