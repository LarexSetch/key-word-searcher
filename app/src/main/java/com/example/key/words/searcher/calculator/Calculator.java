package com.example.key.words.searcher.calculator;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

public interface Calculator<T> {
    List<Row> calculate(List<T> inputs);

    @Getter
    @EqualsAndHashCode
    @ToString
    @AllArgsConstructor
    class Row {
        private final List<String> groupBy;
        private final Integer count;
    }
}
