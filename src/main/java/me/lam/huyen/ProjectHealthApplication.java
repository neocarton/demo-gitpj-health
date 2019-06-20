package me.lam.huyen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableFeignClients
@SpringBootApplication
public class ProjectHealthApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectHealthApplication.class, args);
	}

}
