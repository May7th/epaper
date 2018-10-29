package com.oyun.media.epaper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author changzhen
 */
@SpringBootApplication
@EnableSwagger2
//@EnableScheduling
public class EpaperApplication {

	public static void main(String[] args) {
		SpringApplication.run(EpaperApplication.class, args);
	}
}
