package com.example.demo;

import eu.rekawek.toxiproxy.ToxiproxyClient;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;

import static eu.rekawek.toxiproxy.model.ToxicDirection.DOWNSTREAM;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		proxy(args[0]);
		SpringApplication.run(DemoApplication.class, args);
	}

	private static void proxy(String time) {
		var client = new ToxiproxyClient("toxiproxy", 8474);

		try {
			client.createProxy("mysql", "toxiproxy:19385", "demo-mysql:3306")
					.toxics()
                	.latency("mysql-latency-down", DOWNSTREAM, Integer.parseInt(time));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Bean
	ApplicationRunner applicationRunner(HelloService helloService) {
		return  (arguments) -> {
			helloService.save();
		};
	}

}
