package com.adtrack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/*This class responsible start the application.
 * */
@SpringBootApplication
@EntityScan("com.adtrack.entity")
@EnableJpaRepositories("com.adtrack.repository")
@EnableCaching
public class AdtrackApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdtrackApplication.class, args);
	}	
}
