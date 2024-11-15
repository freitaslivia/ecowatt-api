package br.com.ecowatt_api.dto;

public record LoginResponseDTO(String token, Long idUsuario, String login) {
}