package com.example.key.words.searcher.operation.search;

import com.example.key.words.searcher.calculator.Calculator;

import java.util.Map;

final class SearchOperation implements Search {
    private final DataLoader loader;
    private final Calculator<SearchResult> calculator;

    public SearchOperation(DataLoader loader, Calculator<SearchResult> calculator) {
        this.loader = loader;
        this.calculator = calculator;
    }

    @Override
    public Map<String, Integer> invoke(SearchRequest request) {
        return null;
    }
}
