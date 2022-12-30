package com.java.gmall.sms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author jiangli
 * @since 2020/1/11 18:25
 */
@SpringBootApplication
@EnableSwagger2
public class SmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmsApplication.class,args);
	}
}
