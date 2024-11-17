package br.com.ecowatt_api;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info =
@Info(title = "ECOWATT API", version = "0.0.1"))
public class EcowattApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcowattApiApplication.class, args);
	}

}
