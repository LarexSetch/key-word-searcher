package com.example.key.words.searcher.client;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import lombok.Data;
import lombok.Getter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;


@Headers({"User-Agent: words-searcher-client/1.0.0"})
public interface YandexClient {
    @RequestLine("GET /search/xml?query={query}")
    SearchXmlResponse searchXml(@Param("query") String query);

    @Getter
    @XmlRootElement(name = "yandexsearch")
    class SearchXmlResponse {
        @XmlElement(name = "response")
        private Response response;

        @Getter
        public static class Response {
            @XmlElement(name = "results")
            private Results results;

            @Getter
            public static class Results {
                @XmlElements({@XmlElement(name = "grouping")})
                private List<Grouping> groupings;

                @Getter
                public static class Grouping {
                    @XmlElements({@XmlElement(name = "group")})
                    private List<Group> groups;

                    @Getter
                    public static class Group {
                        @XmlElements({@XmlElement(name = "doc")})
                        private List<Doc> docs;

                        @Getter
                        public static class Doc {
                            @XmlElement(name = "domain")
                            private String domain;
                            @XmlElement(name = "url")
                            private String url;
                        }
                    }
                }
            }
        }
    }
}
