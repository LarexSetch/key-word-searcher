package com.example.key.words.searcher.operation.search;

import com.example.key.words.searcher.client.YandexClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SearchFactory {
    @Bean
    public Search createSearch(YandexClient client) {
        return new BusinessRuleDecorator(
                new SearchOperation(
                        new CommonDataLoader(client),
                        new UrlDomainCalculator()
                ),
                100 // TODO configuration
        );
    }
}
