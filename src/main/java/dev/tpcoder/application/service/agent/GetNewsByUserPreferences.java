package dev.tpcoder.application.service.agent;

import dev.tpcoder.application.dto.GetNewsByPreferenceRequest;
import dev.tpcoder.application.dto.GetNewsByPreferenceResponse;
import dev.tpcoder.application.dto.GetNewsRequest;
import dev.tpcoder.application.service.NewsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;

public class GetNewsByUserPreferences implements Function<GetNewsByPreferenceRequest, GetNewsByPreferenceResponse> {

    private static final Logger logger = LoggerFactory.getLogger(GetNewsByUserPreferences.class);
    private final NewsService newsService;

    public GetNewsByUserPreferences(NewsService newsService) {
        this.newsService = newsService;
    }

    @Override
    public GetNewsByPreferenceResponse apply(GetNewsByPreferenceRequest getNewsByPreferenceRequest) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime last2Days = now.minusDays(2);
        LocalDateTime yesterday = now.minusDays(1);
        GetNewsRequest request = new GetNewsRequest(
                List.of("th", "jp", "us", "gb"),
                getNewsByPreferenceRequest.topic(),
                last2Days.toLocalDate(),
                yesterday.toLocalDate()
        );
        logger.info("Request: {}", request);
        var newsResponse = newsService.getNews(request);
        return newsService.extractNewsAndGiveOpinion(newsResponse);
    }
}
