package com.practice.ai.mcpclient;

import io.modelcontextprotocol.client.McpAsyncClient;
import io.modelcontextprotocol.spec.McpSchema;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ai/chat")
@RequiredArgsConstructor
@Slf4j
public class ChatController {

    private final ChatClient chatClient;

    @Autowired
    private List<McpAsyncClient> mcpAsyncClients;

    @RequestMapping("/map")
    public Mono<McpSchema.CallToolResult> test() {
        var mcpClient = mcpAsyncClients.get(0);

        return mcpClient.listTools()
                .flatMap(tools -> {
                    log.info("tools: {}", tools);

                    return mcpClient.callTool(
                            new McpSchema.CallToolRequest(
                                    "maps_weather",
                                    Map.of("city", "北京")
                            )
                    );
                });
    }
    @GetMapping("/query")
    public String query(@RequestParam String message) {
        return chatClient.prompt()
                .user(message)
                .call()
                .content();
    }
}
