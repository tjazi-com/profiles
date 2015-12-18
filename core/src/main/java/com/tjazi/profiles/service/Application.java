package com.tjazi.profiles.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Krzysztof Wasiak on 10/10/15.
 */

@ComponentScan
@Configuration
@EnableAutoConfiguration
@EnableDiscoveryClient
@EnableHystrix
public class Application {

    public static void main(String[] args){
        SpringApplication.run(Application.class);
    }
}
