package com.java.gmall.pms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author jiangli
 * @since 2020/1/10 3:46
 */
@SpringBootApplication
@EnableFeignClients
@EnableSwagger2
public class PmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(PmsApplication.class,args);
	}
}
