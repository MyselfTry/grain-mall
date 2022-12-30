package com.java.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringBootApplication
@EnableDiscoveryClient
public class GmallAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(GmallAdminApplication.class, args);
    }
}