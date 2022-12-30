package com.java.gmall.oms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author jiangli
 * @since 2020/1/30 10:32
 */
@SpringBootApplication
@EnableFeignClients
public class OmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(OmsApplication.class,args);
	}
}
