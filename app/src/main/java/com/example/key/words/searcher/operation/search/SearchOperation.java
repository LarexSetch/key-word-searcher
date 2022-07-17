package com.example.key.words.searcher.operation.search;

import com.example.key.words.searcher.calculator.Calculator;

import java.util.List;
import java.util.stream.Collectors;

final class SearchOperation implements Search {
    private final DataLoader loader;
    private final Calculator<SearchResult> calculator;

    public SearchOperation(DataLoader loader, Calculator<SearchResult> calculator) {
        this.loader = loader;
        this.calculator = calculator;
    }

    @Override
    public SearchResponse invoke(SearchRequest request) {
        return new SearchResponse(
                createResponseItems(
                        calculator.calculate(
                                loader.load(request.getQueries())
                        )
                )
        );
    }

    private List<SearchResponse.Item> createResponseItems(List<Calculator.Row> calculatorRows) {
        return calculatorRows.stream()
                .map(result -> new SearchResponse.Item(
                        result.getGroupBy(),
                        result.getCount()
                ))
                .collect(Collectors.toList());
    }
}
