package com.java.gmall.wms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author jiangli
 * @since 2020/1/11 15:53
 */
@SpringBootApplication
@EnableSwagger2
@EnableFeignClients
public class WmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(WmsApplication.class,args);
	}
}
