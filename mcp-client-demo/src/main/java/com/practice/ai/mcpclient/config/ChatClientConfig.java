package com.practice.ai.mcpclient.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.ChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.definition.ToolDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
@Slf4j
public class ChatClientConfig {

    @Bean
    @DependsOn("chatMemory")
    public ChatClient chatClient(ChatClient.Builder builder, ToolCallbackProvider toolCallbackProvider,
                                 ChatMemory chatMemory) {
        return builder
//                .defaultAdvisors(new SimpleLoggerAdvisor(), new SafeGuardAdvisor(List.of("政府", "美国")))
                .defaultAdvisors(new SimpleLoggerAdvisor(), PromptChatMemoryAdvisor.builder(chatMemory).build())
                .defaultToolCallbacks(toolCallbackProvider)
                .build();
    }

    @Bean
    public ChatMemory chatMemory(ChatMemoryRepository chatMemoryRepository) {
        return MessageWindowChatMemory.builder()
                .maxMessages(30)
                .chatMemoryRepository(chatMemoryRepository).build();
    }
}
