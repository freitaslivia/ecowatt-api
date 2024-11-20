package br.com.ecowatt_api.controller;

import br.com.ecowatt_api.security.SecurityConfigurations;
import br.com.ecowatt_api.service.SpringAIChatService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@RequestMapping("/springai")
@Tag(name = "Spring AI")
@SecurityRequirement(name = SecurityConfigurations.SECURITY)
public class SpringAIChatController {

    private final SpringAIChatService chatService;

    public SpringAIChatController(SpringAIChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping()
    public ResponseEntity<Map> generate(@RequestParam(value = "message", defaultValue = "Qual é o objetivo principal da EcoWatt?") String message) {
        return new ResponseEntity<>(Map.of("ollama", chatService.run(message)), HttpStatus.OK);
    }

}