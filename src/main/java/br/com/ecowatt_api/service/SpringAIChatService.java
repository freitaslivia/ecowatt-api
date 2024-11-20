package br.com.ecowatt_api.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Service
public class SpringAIChatService {
    private final ChatClient.Builder chatClientBuilder;

    public SpringAIChatService(ChatClient.Builder chatClientBuilder) {
        this.chatClientBuilder = chatClientBuilder;
    }

    private static final String ARQUIVO_PATH = "ecowatt.txt";


    public String run(String userPrompt) {
        String fileContent = lerArquivoTexto(ARQUIVO_PATH);

        String promptComArquivo = "Conteúdo do arquivo: \n" + fileContent + "\n Responda a pergunta do usuário somente com base no arquivo que você acessou\nPergunta do usuário: " + userPrompt;

        var chatClient = chatClientBuilder.build();

        return chatClient
                .prompt()
                .user(promptComArquivo)
                .call()
                .content()
                .replace("\n", "");
    }

    private String lerArquivoTexto(String filePath) {
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath);
            if (inputStream == null) {
                throw new IOException("Arquivo não encontrado: " + filePath);
            }
            String content = new String(inputStream.readAllBytes());
            inputStream.close();
            return content;
        } catch (IOException e) {
            e.printStackTrace();
            return "Erro ao ler o arquivo.";
        }
    }

}