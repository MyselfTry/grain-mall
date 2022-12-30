package com.java.gmall.index;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author jiangli
 * @since 2020/1/17 22:16
 */
@SpringBootApplication
@EnableFeignClients
public class IndexApplication {

	public static void main(String[] args) {
		SpringApplication.run(IndexApplication.class,args);
	}
}
