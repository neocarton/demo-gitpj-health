package me.lam.huyen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@IntegrationComponentScan
@EnableFeignClients
@SpringBootApplication
public class ProjectHealthApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectHealthApplication.class, args);
	}
}
