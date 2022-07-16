package com.example.key.words.searcher.controller;

import com.example.key.words.searcher.exception.BusinessRuleException;
import com.example.key.words.searcher.operation.search.Search;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public final class SearchController {
    private final Logger logger = LoggerFactory.getLogger(SearchController.class);

    private final Search search;

    public SearchController(Search search) {
        this.search = search;
    }

    @RequestMapping(path = "/search")
    private ResponseEntity search(@RequestParam("query") List<String> queries) {
        try {
            return new ResponseEntity<>(search.invoke(new Search.SearchRequest(queries)), HttpStatus.OK);
        } catch (BusinessRuleException exception) {
            logger.error("Business rule restrictions", exception);

            return new ResponseEntity(createErrorMapResponse(), HttpStatus.CONFLICT);
        }
    }

    private Map<String, String> createErrorMapResponse() {
        Map<String, String> data = new HashMap<>();
        data.put("status", "error");
        data.put("reason", "Business rule restrictions");

        return data;
    }
}
