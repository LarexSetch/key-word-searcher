package com.example.key.words.searcher.client;

import feign.Headers;

@Headers({"User-Agent: words-searcher-client/1.0.0"})
public interface YandexClient {
    SearchXmlResponse searchXml(String query);

    class SearchXmlResponse {

    }
}
