package shook.shook.song.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.xml.Jaxb2XmlDecoder;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ManiaDBConfiguration {

    private static final String MANIA_DB_BASE_URL = "http://www.maniadb.com/api/search";

    @Bean
    public WebClient getWebClient() {
        return WebClient.builder()
            .baseUrl(MANIA_DB_BASE_URL)
            .exchangeStrategies(
                ExchangeStrategies.builder()
                    .codecs(configurer ->
                        configurer.defaultCodecs().jaxb2Decoder(new Jaxb2XmlDecoder())
                    )
                    .codecs(configurer ->
                        configurer.defaultCodecs().maxInMemorySize(4 * 1024 * 1024)
                    )
                    .build()
            )
            .build();
    }
}
