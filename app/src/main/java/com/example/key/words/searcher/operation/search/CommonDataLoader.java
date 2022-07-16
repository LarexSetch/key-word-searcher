package com.example.key.words.searcher.operation.search;

import com.example.key.words.searcher.client.YandexClient;

import java.util.List;

final class CommonDataLoader implements DataLoader {
    private final YandexClient client;

    CommonDataLoader(YandexClient client) {
        this.client = client;
    }

    @Override
    public List<SearchResult> load(List<String> queries) {
        return null;
    }
}
