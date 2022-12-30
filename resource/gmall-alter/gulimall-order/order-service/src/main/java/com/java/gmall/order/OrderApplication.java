package com.java.gmall.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author jiangli
 * @since 2020/1/30 11:00
 */
@SpringBootApplication
@EnableFeignClients
@ComponentScan(basePackages={"com.java.core","com.java.gmall.order"})
public class OrderApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderApplication.class,args);
	}
}
