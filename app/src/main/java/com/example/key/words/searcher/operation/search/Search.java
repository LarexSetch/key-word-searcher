package com.example.key.words.searcher.operation.search;

import com.example.key.words.searcher.exception.BusinessRuleException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

public interface Search {
    SearchResponse invoke(SearchRequest request) throws BusinessRuleException;

    @Getter
    @AllArgsConstructor
    class SearchRequest {
        private final List<String> queries;
    }

    @Getter
    @AllArgsConstructor
    class SearchResponse {
        private final List<Item> items;

        @Getter
        @AllArgsConstructor
        static class Item {
            private final List<String> groupBy;
            private final Integer count;
        }
    }
}
