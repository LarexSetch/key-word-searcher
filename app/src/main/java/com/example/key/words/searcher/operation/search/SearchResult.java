package com.example.key.words.searcher.operation.search;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class SearchResult {
    private final String domain;
    private final String url;
    private final String title;
    private final String headline;
}
