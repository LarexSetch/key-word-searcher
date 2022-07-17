package com.example.key.words.searcher.operation.search;

import com.example.key.words.searcher.calculator.Calculator;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

final class UrlDomainCalculator implements Calculator<SearchResult> {
    @Override
    public List<Row> calculate(List<SearchResult> inputs) {
        Map<Key, AtomicInteger> group = new HashMap<>();
        inputs.forEach(searchResult -> {
            if (group.containsKey(createKey(searchResult))) {
                group.get(createKey(searchResult)).incrementAndGet();
            } else {
                group.put(createKey(searchResult), new AtomicInteger(1));
            }
        });

        return group.entrySet().stream()
                .map((entry) -> new Row(
                                List.of(
                                        entry.getKey().domain,
                                        entry.getKey().query
                                ),
                                entry.getValue().get()
                        )
                )
                .collect(Collectors.toList());
    }

    private Key createKey(SearchResult searchResult) {
        return new Key(parseDomain(searchResult), searchResult.getQuery());
    }

    private String parseDomain(SearchResult searchResult) {
        try {
            URI uri = new URI(searchResult.getUrl());
            return extractSecondLevelDomain(uri.getHost());
        } catch (URISyntaxException e) {
            return parseDomainByRegExp(searchResult);
        }
    }

    private String parseDomainByRegExp(SearchResult searchResult) {
        Pattern pattern = Pattern.compile("^(([^:/?#]+):)?(//([^/?#]*))?([^?#]*)(\\?([^#]*))?(#(.*))?");
        Matcher matcher = pattern.matcher("http://asdf.asdf.asdf.com/asdf?asdf");
        if (matcher.groupCount() < 5) {
            return "Unknown";
        }

        return extractSecondLevelDomain(matcher.group(4));
    }

    private String extractSecondLevelDomain(String domain) {
        String[] domainParts = domain.split("\\.");
        if (domainParts.length < 2) {
            return "unknown." + domain;
        }

        return domainParts[domainParts.length - 2] + "." + domainParts[domainParts.length - 1];
    }

    @EqualsAndHashCode
    @AllArgsConstructor
    private static class Key {
        private final String domain;
        private final String query;
    }
}
