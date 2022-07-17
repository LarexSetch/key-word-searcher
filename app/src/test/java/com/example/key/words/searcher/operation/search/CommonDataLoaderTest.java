package com.example.key.words.searcher.operation.search;

import com.example.key.words.searcher.client.YandexClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class CommonDataLoaderTest {
    @Autowired
    private YandexClient client;

    @Test
    public void loadTest() {
        DataLoader loader = new CommonDataLoader(client);

        Assertions.assertEquals(30, loader.load(List.of("asdf", "zxcv", "zxcv")).size());
        Assertions.assertEquals(20, loader.load(List.of("asdf", "zxcv")).size());
    }

}
