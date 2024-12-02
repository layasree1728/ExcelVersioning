package com.laya.ExcelVersion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableMethodSecurity(securedEnabled = true,prePostEnabled = true)
public class ExcelVersionApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExcelVersionApplication.class, args);
	}

}
