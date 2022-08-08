package com.lex;

import com.lex.configuration.ApplicationSecurityConfig;
import com.lex.configuration.ApiFeignBasicConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

@EnableFeignClients
@SpringBootApplication
@Import({ApplicationSecurityConfig.class, ApiFeignBasicConfiguration.class})
public class LexApplication {

	public static void main(String[] args) {
		SpringApplication.run(LexApplication.class, args);
	}

}
