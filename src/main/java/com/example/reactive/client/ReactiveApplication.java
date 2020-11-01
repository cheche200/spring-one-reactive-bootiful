package com.example.reactive.client;

import com.example.reactive.GreetingRequest;
import com.example.reactive.GreetingResponse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.retrosocket.EnableRSocketClients;
import org.springframework.retrosocket.RSocketClient;
import reactor.core.publisher.Flux;

import java.io.IOException;

@SpringBootApplication
@EnableRSocketClients
public class ReactiveApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(ReactiveApplication.class, args);
		System.in.read();
	}

	@Bean
	ApplicationListener<ApplicationEvent> ready(GreetingClient client){
		return event -> {
			client.greet(new GreetingRequest("SpringOne 2020"))
					.subscribe(System.out::println);
		};
	}

//	@Bean
//	ApplicationListener<ApplicationEvent> ready(RSocketRequester requester){
//		return event -> {
//
//			requester.route("greetings")
//					.data(new GreetingRequest("SpringOne 2020"))
//					.retrieveFlux(GreetingResponse.class)
//					.subscribe(System.out::println);
//		};
//	}

	@Bean
	RSocketRequester rSocketRequester (RSocketRequester.Builder builder) {
		return builder.connectTcp("localhost", 8888)
				.block();
	}

}
