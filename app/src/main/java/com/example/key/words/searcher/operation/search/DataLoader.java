package com.example.key.words.searcher.operation.search;

import java.util.List;

public interface DataLoader {
    List<SearchResult> load(List<String> queries);
}
