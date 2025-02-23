package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {

		//pg_ctl -D "C:\Program Files\PostgreSQL\17\data" start
		SpringApplication.run(DemoApplication.class, args);
	}

}
