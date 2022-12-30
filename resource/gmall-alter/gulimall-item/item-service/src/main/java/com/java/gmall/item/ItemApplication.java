package com.java.gmall.item;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author jiangli
 * @since 2020/1/26 13:11
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableFeignClients
@ComponentScan(basePackages={"com.java.core","com.java.gmall.item"})
public class ItemApplication {

	public static void main(String[] args) {
		SpringApplication.run(ItemApplication.class,args);
	}
}
