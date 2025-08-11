package com.practice.ai.mcpclient.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.definition.ToolDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class ChatClientConfig {
    @Bean
    public ChatClient chatClient(ChatClient.Builder chatClientBuilder, ToolCallbackProvider toolCallbackProvider) {
        ToolCallback[] tools = toolCallbackProvider.getToolCallbacks();
        log.info("获取到的MCP工具有：");
        for (ToolCallback tool : tools) {
            ToolDefinition toolDefinition = tool.getToolDefinition();
            log.info("工具名称：{}", toolDefinition.name());
            log.info("工具描述：{}", toolDefinition.description());
        }
        return chatClientBuilder.defaultToolCallbacks(tools).build();
    }
}
