package com.example.key.words.searcher.operation.search;

import com.example.key.words.searcher.exception.BusinessRuleException;

import java.util.Map;

final class BusinessRuleDecorator implements Search {
    private final Search inner;

    public BusinessRuleDecorator(Search inner) {
        this.inner = inner;
    }

    @Override
    public Map<String, Integer> invoke(SearchRequest request) throws BusinessRuleException {
        return null;
    }
}
