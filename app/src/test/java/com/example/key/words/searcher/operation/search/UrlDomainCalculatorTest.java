package com.example.key.words.searcher.operation.search;

import com.example.key.words.searcher.calculator.Calculator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class UrlDomainCalculatorTest {
    @Test
    public void calculate() {
        Calculator<SearchResult> calculator = new UrlDomainCalculator();

        Assertions.assertEquals(
                createExpectedListRow().stream()
                        .sorted((left, right) -> left.toString().compareToIgnoreCase(right.toString()))
                        .collect(Collectors.toList()),
                calculator.calculate(createListSearchResult()).stream()
                        .sorted((left, right) -> left.toString().compareToIgnoreCase(right.toString()))
                        .collect(Collectors.toList())
        );
    }

    private List<SearchResult> createListSearchResult() {
        return List.of(
                new SearchResult("http://test.com", "asdf"),
                new SearchResult("http://test.com/asdf", "asdf"),
                new SearchResult("http://test.com/zxcv", "asdf"),
                new SearchResult("http://demo.com/?asdf=asdf", "asdf"),
                new SearchResult("http://test.com/?asd=zxcv", "zxcv"),
                new SearchResult("http://demo.com/asdfoiuwqer", "zxcv")
        );
    }

    private List<Calculator.Row> createExpectedListRow() {
        return List.of(
                new Calculator.Row(List.of("test.com", "asdf"), 3),
                new Calculator.Row(List.of("demo.com", "asdf"), 1),
                new Calculator.Row(List.of("test.com", "zxcv"), 1),
                new Calculator.Row(List.of("demo.com", "zxcv"), 1)
        );
    }
}
