package com.example.key.words.searcher.client;

import feign.*;
import feign.codec.*;
import feign.jaxb.JAXBContextFactory;
import feign.jaxb.JAXBDecoder;
import feign.jaxb.JAXBEncoder;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;
import lombok.Setter;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.concurrent.Semaphore;

@Configuration
public class YandexClientFactory {
    @Bean
    public YandexClient createYandexClient(YandexClientConfig config) {
        ClientRequestInterceptor interceptor = new ClientRequestInterceptor(config);

        return Feign.builder()
                .client(new OkHttpClient())
                .encoder(interceptor)
                .decoder(interceptor)
                .errorDecoder(interceptor)
                .logger(new Slf4jLogger())
                .requestInterceptor(interceptor)
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
        private Integer concurrentMaxRequests;
    }

    public static class ClientRequestInterceptor implements RequestInterceptor, Decoder, ErrorDecoder, Encoder {
        private final YandexClientConfig config;
        private final org.slf4j.Logger logger = LoggerFactory.getLogger(ClientRequestInterceptor.class);
        private static final JAXBContextFactory jaxbFactory = new JAXBContextFactory.Builder().withMarshallerJAXBEncoding("UTF-8").build();
        private static final Encoder encoder = new JAXBEncoder(jaxbFactory);
        private static final Decoder decoder = new JAXBDecoder(jaxbFactory);
        private static final ErrorDecoder errorDecoder = new ErrorDecoder.Default();
        private final Semaphore semaphore;

        public ClientRequestInterceptor(YandexClientConfig config) {
            this.config = config;
            this.semaphore = new Semaphore(config.concurrentMaxRequests);
        }

        @Override
        public void apply(RequestTemplate template) {
            try {
                semaphore.acquire();
            } catch (InterruptedException e) {
                logger.error("Cannot acquire semaphore", e);
            }

            template.query("user", config.user);
            template.query("key", config.apiKey);
        }

        @Override
        public Object decode(Response response, Type type) throws IOException, DecodeException, FeignException {
            semaphore.release();
            return decoder.decode(response, type);
        }

        @Override
        public void encode(Object object, Type bodyType, RequestTemplate template) throws EncodeException {
            encoder.encode(object, bodyType, template);
        }

        @Override
        public Exception decode(String methodKey, Response response) {
            semaphore.release();

            return errorDecoder.decode(methodKey, response);
        }
    }
}
