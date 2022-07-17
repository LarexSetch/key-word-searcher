package com.example.key.words.searcher.client;

import feign.Feign;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.jaxb.JAXBContextFactory;
import feign.jaxb.JAXBDecoder;
import feign.jaxb.JAXBEncoder;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class YandexClientFactory {
    @Bean
    public YandexClient createYandexClient(YandexClientConfig config) {
        JAXBContextFactory jaxbFactory = new JAXBContextFactory.Builder()
                .withMarshallerJAXBEncoding("UTF-8")
                .build();

        return Feign.builder()
                .client(new OkHttpClient())
                .encoder(new JAXBEncoder(jaxbFactory))
                .decoder(new JAXBDecoder(jaxbFactory))
                .logger(new Slf4jLogger())
                .requestInterceptor(new AuthorizationRequestInterceptor(config))
                .logLevel(feign.Logger.Level.FULL)
                .target(YandexClient.class, config.baseUrl);
    }


    @Setter
    @Configuration
    @ConfigurationProperties(prefix = "app.yandex.client")
    public static class YandexClientConfig {
        private String user;
        private String apiKey;
        private String baseUrl;
    }

    public static class AuthorizationRequestInterceptor implements RequestInterceptor {
        private final YandexClientConfig config;

        public AuthorizationRequestInterceptor(YandexClientConfig config) {
            this.config = config;
        }

        @Override
        public void apply(RequestTemplate template) {
            template.query("user", config.user);
            template.query("user", config.apiKey);
        }
    }
}
