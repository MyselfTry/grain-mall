package com.java.gmall.ums;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author jiangli
 * @since 2020/1/27 19:55
 */
@SpringBootApplication
@EnableFeignClients
public class UmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(UmsApplication.class,args);
	}
}
