package dev.tpcoder.application.agent;

import dev.tpcoder.application.dto.GetNewsByPreferenceRequest;
import dev.tpcoder.application.dto.GetNewsByPreferenceResponse;
import dev.tpcoder.application.dto.UserPreferenceRequest;
import dev.tpcoder.application.dto.UserPreferenceResponse;
import dev.tpcoder.application.repository.UserPreferencesRepository;
import dev.tpcoder.application.service.NewsService;
import dev.tpcoder.application.service.agent.GetNewsByUserPreferences;
import dev.tpcoder.application.service.agent.UserPreferencesService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

import java.util.function.Function;

@Configuration
public class AiAgentConfig {

    public static final String GET_USER_PREFERENCES_FUNCTION_NAME = "getUserPreferences";
    public static final String GET_LATEST_NEWS_BY_TOPIC_FUNCTION_NAME = "getLatestNewsByTopic";

    @Bean(name = GET_USER_PREFERENCES_FUNCTION_NAME)
    @Description("Get topic by userId")
    public Function<UserPreferenceRequest, UserPreferenceResponse> getUserPreferencesInfo(
            UserPreferencesRepository userPreferencesRepository) {
        return new UserPreferencesService(userPreferencesRepository);
    }

    @Bean(name = GET_LATEST_NEWS_BY_TOPIC_FUNCTION_NAME)
    @Description("Get latest news from user topic")
    public Function<GetNewsByPreferenceRequest, GetNewsByPreferenceResponse> getNewsFromPreference(NewsService newsService) {
        return new GetNewsByUserPreferences(newsService);
    }

}
