package com.example.key.words.searcher.client;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class YandexClientTest {
    @Autowired
    private YandexClient client;

    @Test
    public void searchTest() {
        YandexClient.SearchXmlResponse response = client.searchXml("asdf");

        response.getResponse().getResults().getGroupings().forEach(
                grouping -> grouping.getGroups().forEach(
                        group -> group.getDocs().forEach(
                                doc -> {
                                    Assertions.assertNotNull(doc.getDomain());
                                    Assertions.assertNotNull(doc.getUrl());
                                }
                        )
                )
        );
    }
}
