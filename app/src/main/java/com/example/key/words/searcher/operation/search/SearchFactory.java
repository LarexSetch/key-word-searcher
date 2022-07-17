package com.example.key.words.searcher.operation.search;

import com.example.key.words.searcher.client.YandexClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SearchFactory {
    @Bean
    public Search createSearch(
            YandexClient client,
            @Value("${app.search.restriction.query.count}") Integer restrictionQueryCount
    ) {
        return new BusinessRuleDecorator(
                new SearchOperation(
                        new CommonDataLoader(client),
                        new UrlDomainCalculator()
                ),
                restrictionQueryCount
        );
    }
}
