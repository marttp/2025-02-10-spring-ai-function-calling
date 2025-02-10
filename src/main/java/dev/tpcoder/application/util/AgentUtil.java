package dev.tpcoder.application.util;

import org.springframework.ai.openai.OpenAiChatOptions;

import java.util.List;

public class AgentUtil {

    public static OpenAiChatOptions createFunctionOptions(String... functions) {
        var builder = OpenAiChatOptions.builder();
        // Concat function to create callback connectivity
        for (String function : functions) {
            builder = builder.function(function);
        }
        return builder.build();
    }

    public static String combinedQuery(List<String> queries) {
        return String.join(",", queries);
    }
}
