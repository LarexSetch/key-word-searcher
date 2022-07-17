package com.example.key.words.searcher.operation.search;

import com.example.key.words.searcher.exception.BusinessRuleException;

final class BusinessRuleDecorator implements Search {
    private final Search inner;
    private final Integer queryCountRestriction;

    public BusinessRuleDecorator(Search inner, Integer queryCountRestriction) {
        this.inner = inner;
        this.queryCountRestriction = queryCountRestriction;
    }

    @Override
    public SearchResponse invoke(SearchRequest request) throws BusinessRuleException {
        if (request.getQueries().size() > queryCountRestriction) {
            throw new BusinessRuleException("Too many queries max allowed " + queryCountRestriction);
        }

        return inner.invoke(request);
    }
}
