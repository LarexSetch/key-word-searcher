package com.example.key.words.searcher.operation.search;

import com.example.key.words.searcher.exception.BusinessRuleException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Map;

public interface Search {
    Map<String, Integer> invoke(SearchRequest request) throws BusinessRuleException;

    @Getter
    @AllArgsConstructor
    class SearchRequest {
        private final List<String> queries;
    }
}
