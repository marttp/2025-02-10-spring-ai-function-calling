package dev.tpcoder.application.service;

import dev.tpcoder.application.dto.GetNewsByPreferenceResponse;
import dev.tpcoder.application.dto.GetNewsRequest;
import dev.tpcoder.application.dto.GetNewsResponse;
import dev.tpcoder.application.util.AgentUtil;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
public class NewsService {

    private final RestClient restClient;
    private final OpenAiChatModel chatModel;
    private final String newsUrl;
    private final String accessKey;

    public NewsService(
            RestClient.Builder restClientBuilder,
            OpenAiChatModel chatModel,
            @Value("${external.news.url}") String newsUrl,
            @Value("${external.news.key}") String accessKey
    ) {
        this.restClient = restClientBuilder.build();
        this.chatModel = chatModel;
        this.newsUrl = newsUrl;
        this.accessKey = accessKey;
    }

    public GetNewsByPreferenceResponse extractNewsAndGiveOpinion(GetNewsResponse newsResponse) {
        var summarizeFormat = """
                title, opinion, url, source
                """;
        return ChatClient.create(chatModel)
                .prompt()
                .system(s -> s.text(
                        """
                        
                        You are a personal secretary who received a new news from internet.
                        Since the time is limited, You need to read description and give your opinion about them in detail.
                        For instance, Result of news, Opportunity for now, Opportunity for the future, etc.
                        Just for better decisions.
                        
                        Format will be list of below format
                        {summarized_format}
                        
                        Only 5 news are allowed on result.
                        """)
                        .param("summarized_format", summarizeFormat))
                .user(u -> u.text(
                        """
                        {news}
                        """)
                        .param("news", newsResponse))
                .call()
                .entity(GetNewsByPreferenceResponse.class);
    }

    public GetNewsResponse getNews(GetNewsRequest request) {
        UriBuilder uriBuilder = UriComponentsBuilder.fromUriString(newsUrl);
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("access_key", accessKey);
        queryParams.add("countries", AgentUtil.combinedQuery(request.countries()));
        queryParams.add("date", AgentUtil.combinedQuery(
                List.of(request.rangeStart().toString(), request.rangeEnd().toString()))
        );
        queryParams.add("categories", AgentUtil.combinedQuery(List.of(request.category())));
        uriBuilder.queryParams(queryParams);
        return restClient.get()
                .uri(uriBuilder.build())
                .retrieve()
                .body(GetNewsResponse.class);
    }

}
