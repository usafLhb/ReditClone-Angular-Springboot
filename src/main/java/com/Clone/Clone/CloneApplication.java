package com.Clone.Clone;

import com.Clone.Clone.config.SwaggerConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@Import(SwaggerConfiguration.class)
public class CloneApplication {

	public static void main(String[] args) {

		SpringApplication.run(CloneApplication.class, args);



		System.out.println("DONE");
	}

}
