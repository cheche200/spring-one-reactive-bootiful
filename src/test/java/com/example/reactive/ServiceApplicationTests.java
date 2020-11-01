package com.example.reactive;

import com.example.reactive.client.GreetingClient;
import com.example.reactive.client.ReactiveApplication;
import com.example.reactive.service.ServiceApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

class ServiceApplicationTests {

	@Test
	void contextLoads() {

		var server = new SpringApplicationBuilder()
				.sources(ServiceApplication.class)
				.properties("spring.rsocket.server.port=8888")
				.build()
				.run();

		var client = new SpringApplicationBuilder()
				.sources(ReactiveApplication.class)
				.build()
				.run();

		GreetingClient bean = client.getBean(GreetingClient.class);
		Flux<GreetingResponse> greet = bean.greet(new GreetingRequest("SpringOne 2020")).take(1);
		StepVerifier
				.create(greet)
				.expectNextMatches(r -> r.getMessage().contains("SpringOne 2020"))
				.verifyComplete();

	}

}
