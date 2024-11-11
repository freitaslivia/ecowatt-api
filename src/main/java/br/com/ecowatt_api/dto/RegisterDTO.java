package br.com.ecowatt_api.dto;

public record RegisterDTO(String login,
                          String senha,
                          String nomeCompleto,
                          String senhaConfirmacao) {}
