package com.example.key.words.searcher.operation.search;

import com.example.key.words.searcher.client.YandexClient;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

final class CommonDataLoader implements DataLoader {
    private final YandexClient client;

    CommonDataLoader(YandexClient client) {
        this.client = client;
    }

    @Override
    public List<SearchResult> load(List<String> queries) {
        return queries.stream()
                .parallel()
                .map(query -> client.searchXml(query).getResponse().getResults()
                        .getGroupings()
                        .stream()
                        .map(grouping -> grouping.getGroups().stream()
                                .map(group -> group.getDocs()
                                        .stream()
                                        .map(doc -> new SearchResult(doc.getUrl().trim(), query))
                                        .collect(Collectors.toList())
                                )
                                .collect(new ResultCollector())
                        )
                        .collect(new ResultCollector()))
                .collect(new ResultCollector());
    }

    static class ResultCollector implements Collector<List<SearchResult>, List<SearchResult>, List<SearchResult>> {
        @Override
        public Supplier<List<SearchResult>> supplier() {
            return ArrayList::new;
        }

        @Override
        public BiConsumer<List<SearchResult>, List<SearchResult>> accumulator() {
            return List::addAll;
        }

        @Override
        public BinaryOperator<List<SearchResult>> combiner() {
            return (left, right) -> {
                left.addAll(right);
                return left;
            };
        }

        @Override
        public Function<List<SearchResult>, List<SearchResult>> finisher() {
            return results -> results;
        }

        @Override
        public Set<Characteristics> characteristics() {
            return Collections.singleton(Characteristics.CONCURRENT);
        }
    }
}
