package shook.shook.song.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.xml.Jaxb2XmlDecoder;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import shook.shook.song.application.dto.UnregisteredSongResponse;
import shook.shook.song.exception.ExternalApiException;
import shook.shook.song.exception.ExternalApiException.EmptyResultException;
import shook.shook.song.exception.UnregisteredSongException;

@ExtendWith(MockitoExtension.class)
class ManiaDBSearchServiceTest {

    private static final String SEARCH_WORD = "흔들리는꽃속에서네샴푸향이느껴진거야";
    private static final String SPECIAL_MARK_SEARCH_WORD = "\b흔%들리는꽃*들속@에서네샴/푸향이+느껴진-거야!\t";
    private static final String SEARCH_RESULT = """
        <?xml version="1.0" encoding="utf-8" ?>
        <rss version="2.0" xmlns:maniadb="http://www.maniadb.com/api" >
            <channel>
                <title>
                    <![CDATA[Maniadb Open API v0.5 : Search song for "흔들리는꽃속에서네샴푸향이느껴진거야"]]>
                </title>
                <link>www.maniadb.com</link>
                <description>
                    <![CDATA[Maniadb Open API v0.5 : Search song for "흔들리는꽃속에서네샴푸향이느껴진거야"]]>
                </description>
                <lastBuildDate>Fri, 28 Jul 2023 12:03:53 +0900</lastBuildDate>
                <total>1</total>
                <start>1</start>
                <display>100</display>
                <maniadb:urlbase>
                    <![CDATA[http://www.maniadb.com/album/]]>
                </maniadb:urlbase>
                <item id="7557899">
                    <title>
                        <![CDATA[흔들리는 꽃들 속에서 네 샴푸향이 느껴진거야]]>
                    </title>
                    <runningtime>
                        <![CDATA[-]]>
                    </runningtime>
                    <link>
                        <![CDATA[http://www.maniadb.com/album/777829/?ss=7557899]]>
                    </link>
                    <pubDate>
                        <![CDATA[Fri, 28 Jul 2023 12:03:53 +0900]]>
                    </pubDate>
                    <author>
                        <![CDATA[maniadb]]>
                    </author>
                    <description>
                        <![CDATA[]]>
                    </description>
                    <guid>
                        <![CDATA[http://www.maniadb.com/album/777829/?ss=7557899]]>
                    </guid>
                    <comments>
                        <![CDATA[http://www.maniadb.com/album/777829/?ss=7557899#TALK]]>
                    </comments>
                    <maniadb:album>
                        <title>
                            <![CDATA[멜로가 체질 by 김태성 [ost] (2019)]]>
                        </title>
                        <release>
                            <![CDATA[]]>
                        </release>
                        <link>
                            <![CDATA[http://www.maniadb.com/album/777829]]>
                        </link>
                        <image>
                            <![CDATA[http://i.maniadb.com/images/album/777/777829_1_f.jpg]]>
                        </image>
                        <description>
                            <![CDATA[]]>
                        </description>
                    </maniadb:album>
                    <maniadb:artist status="DEPRECATED">
                        <link>
                            <![CDATA[www.maniadb.com/artist/394170]]>
                        </link>
                        <name>
                            <![CDATA[장범준]]>
                        </name>
                    </maniadb:artist>
                    <maniadb:trackartists>
                        <maniadb:artist id="394170">
                            <id>
                                <![CDATA[394170]]>
                            </id>
                            <name>
                                <![CDATA[장범준]]>
                            </name>
                        </maniadb:artist>
                    </maniadb:trackartists>
                    <maniadb:trackartistlist status="DEPRECATED">
                        <![CDATA[장범준]]>
                    </maniadb:trackartistlist>
                </item>
            </channel>
        </rss>""";

    private static final String EMPTY_RESULT_SEARCH_WORD = "빈값검색";
    private static final String EMPTY_RESULT = """
        <?xml version="1.0" encoding="utf-8" ?>
        <rss version="2.0" xmlns:maniadb="http://www.maniadb.com/api" >
            <channel>
                <title>
                    <![CDATA[Maniadb Open API v0.5 : Search song for "빈값검색"]]>
                </title>
                <link>www.maniadb.com</link>
                <description>
                    <![CDATA[Maniadb Open API v0.5 : Search song for "빈값검색"]]>
                </description>
                <lastBuildDate>Fri, 28 Jul 2023 17:37:01 +0900</lastBuildDate>
                <total>0</total>
                <start>1</start>
                <display>100</display>
                <maniadb:urlbase>
                    <![CDATA[http://www.maniadb.com/album/]]>
                </maniadb:urlbase>
            </channel>
        </rss>
        """;

    private static final String EMPTY_SINGER_SEARCH_RESULT = """
        <?xml version="1.0" encoding="utf-8" ?>
        <rss version="2.0" xmlns:maniadb="http://www.maniadb.com/api" >
            <channel>
                <title>
                    <![CDATA[Maniadb Open API v0.5 : Search song for "흔들리는꽃속에서네샴푸향이느껴진거야"]]>
                </title>
                <link>www.maniadb.com</link>
                <description>
                    <![CDATA[Maniadb Open API v0.5 : Search song for "흔들리는꽃속에서네샴푸향이느껴진거야"]]>
                </description>
                <lastBuildDate>Fri, 28 Jul 2023 12:03:53 +0900</lastBuildDate>
                <total>1</total>
                <start>1</start>
                <display>100</display>
                <maniadb:urlbase>
                    <![CDATA[http://www.maniadb.com/album/]]>
                </maniadb:urlbase>
                <item id="7557899">
                    <title>
                        <![CDATA[흔들리는 꽃들 속에서 네 샴푸향이 느껴진거야]]>
                    </title>
                    <runningtime>
                        <![CDATA[-]]>
                    </runningtime>
                    <link>
                        <![CDATA[http://www.maniadb.com/album/777829/?ss=7557899]]>
                    </link>
                    <pubDate>
                        <![CDATA[Fri, 28 Jul 2023 12:03:53 +0900]]>
                    </pubDate>
                    <author>
                        <![CDATA[maniadb]]>
                    </author>
                    <description>
                        <![CDATA[]]>
                    </description>
                    <guid>
                        <![CDATA[http://www.maniadb.com/album/777829/?ss=7557899]]>
                    </guid>
                    <comments>
                        <![CDATA[http://www.maniadb.com/album/777829/?ss=7557899#TALK]]>
                    </comments>
                    <maniadb:album>
                        <title>
                            <![CDATA[멜로가 체질 by 김태성 [ost] (2019)]]>
                        </title>
                        <release>
                            <![CDATA[]]>
                        </release>
                        <link>
                            <![CDATA[http://www.maniadb.com/album/777829]]>
                        </link>
                        <image>
                            <![CDATA[http://i.maniadb.com/images/album/777/777829_1_f.jpg]]>
                        </image>
                        <description>
                            <![CDATA[]]>
                        </description>
                    </maniadb:album>
                    <maniadb:artist status="DEPRECATED">
                    </maniadb:artist>
                    <maniadb:trackartists>
                    </maniadb:trackartists>
                    <maniadb:trackartistlist status="DEPRECATED">
                    </maniadb:trackartistlist>
                </item>
            </channel>
        </rss>""";

    private static final String EMPTY_STRING = "";

    private MockWebServer mockServer;
    private ManiaDBSearchService maniaDBSearchService;

    @BeforeEach
    void startServer() {
        mockServer = new MockWebServer();
        maniaDBSearchService = new ManiaDBSearchService(
            WebClient.builder()
                .baseUrl(mockServer.url("/")
                    .toString())
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
                .build()
        );
    }

    @AfterEach
    void tearDown() throws IOException {
        mockServer.shutdown();
    }

    @DisplayName("검색 요청을 보내면 XML 응답을 정상적으로 받고, 파싱한 데이터를 응답한다.")
    @Test
    void search() {
        // given
        mockServer.enqueue(new MockResponse()
            .setResponseCode(HttpStatus.OK.value())
            .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_XML)
            .addHeader(HttpHeaders.ACCEPT_CHARSET, StandardCharsets.UTF_8)
            .setBody(SEARCH_RESULT)
        );

        final UnregisteredSongResponse expectedResponse = new UnregisteredSongResponse(
            "흔들리는 꽃들 속에서 네 샴푸향이 느껴진거야",
            "장범준",
            "http://i.maniadb.com/images/album/777/777829_1_f.jpg"
        );

        // when
        final List<UnregisteredSongResponse> responses =
            maniaDBSearchService.searchSongs(SEARCH_WORD);

        // then
        assertAll(
            () -> assertThat(responses).hasSize(1),
            () -> assertThat(responses.get(0)).usingRecursiveComparison()
                .isEqualTo(expectedResponse)
        );
    }

    @DisplayName("검색 단어가 null 이거나 빈 문자열인 경우, 예외가 발생한다.")
    @NullAndEmptySource
    @ParameterizedTest(name = "검색 단어가 \"{0}\" 인 경우")
    void nullOrBlankSearchWord(final String searchWord) {
        // given
        // when
        // then
        assertThatThrownBy(() -> maniaDBSearchService.searchSongs(searchWord))
            .isInstanceOf(UnregisteredSongException.NullOrBlankSearchWordException.class);
    }

    @DisplayName("특수문자가 포함된 검색 단어가 입력된 경우, 특수문자를 제거하고 검색한다.")
    @Test
    void searchBySpecialMarkSearchWord() {
        // given
        mockServer.enqueue(new MockResponse()
            .setResponseCode(HttpStatus.OK.value())
            .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_XML)
            .addHeader(HttpHeaders.ACCEPT_CHARSET, StandardCharsets.UTF_8)
            .setBody(SEARCH_RESULT)
        );

        final UnregisteredSongResponse expectedResponse = new UnregisteredSongResponse(
            "흔들리는 꽃들 속에서 네 샴푸향이 느껴진거야",
            "장범준",
            "http://i.maniadb.com/images/album/777/777829_1_f.jpg"
        );

        // when
        final List<UnregisteredSongResponse> responses =
            maniaDBSearchService.searchSongs(SPECIAL_MARK_SEARCH_WORD);

        // then
        assertAll(
            () -> assertThat(responses).hasSize(1),
            () -> assertThat(responses.get(0)).usingRecursiveComparison()
                .isEqualTo(expectedResponse)
        );
    }

    @DisplayName("검색할 단어로 빈 값이 입력된 경우, 예외가 발생한다.")
    @ValueSource(strings = {" ", "\t", "\n", ""})
    @ParameterizedTest(name = "검색 단어가 \"{0}\" 인 경우")
    void searchWithBlankWord(final String blankSearchWord) {
        // given
        // when
        // then
        assertThatThrownBy(() -> maniaDBSearchService.searchSongs(blankSearchWord))
            .isInstanceOf(UnregisteredSongException.NullOrBlankSearchWordException.class);
    }

    @DisplayName("XML 응답에 노래가 존재하지 않으면 빈 리스트를 리턴한다.")
    @Test
    void searchResultReturnEmptyList() {
        // given
        mockServer.enqueue(new MockResponse()
            .setResponseCode(HttpStatus.OK.value())
            .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_XML)
            .addHeader(HttpHeaders.ACCEPT_CHARSET, StandardCharsets.UTF_8)
            .setBody(EMPTY_RESULT)
        );

        // when
        final List<UnregisteredSongResponse> responses =
            maniaDBSearchService.searchSongs(EMPTY_RESULT_SEARCH_WORD);

        // then
        assertThat(responses).isEmpty();
    }

    @DisplayName("XML 응답에 가수가 없으면 가수의 값으로 빈 문자열을 리턴한다.")
    @Test
    void searchResultReturnEmptySinger() {
        // given
        mockServer.enqueue(new MockResponse()
            .setResponseCode(HttpStatus.OK.value())
            .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_XML)
            .addHeader(HttpHeaders.ACCEPT_CHARSET, StandardCharsets.UTF_8)
            .setBody(EMPTY_SINGER_SEARCH_RESULT)
        );

        final UnregisteredSongResponse expectedResponse = new UnregisteredSongResponse(
            "흔들리는 꽃들 속에서 네 샴푸향이 느껴진거야",
            "",
            "http://i.maniadb.com/images/album/777/777829_1_f.jpg"
        );

        // when
        final List<UnregisteredSongResponse> responses =
            maniaDBSearchService.searchSongs(SEARCH_WORD);

        // then
        assertAll(
            () -> assertThat(responses).hasSize(1),
            () -> assertThat(responses.get(0)).usingRecursiveComparison()
                .isEqualTo(expectedResponse)
        );
    }

    @DisplayName("XML 응답을 파싱한 결과값이 null 이면 예외가 발생한다.")
    @Test
    void nullResultThrowException() {
        // given
        mockServer.enqueue(new MockResponse()
            .setResponseCode(HttpStatus.OK.value())
            .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_XML)
            .addHeader(HttpHeaders.ACCEPT_CHARSET, StandardCharsets.UTF_8)
            .setBody(EMPTY_STRING)
        );

        // when
        // then
        assertThatThrownBy(() -> maniaDBSearchService.searchSongs(SEARCH_WORD))
            .isInstanceOf(EmptyResultException.class);
    }

    @DisplayName("ManiaDB로 보내는 요청이 잘못된 경우, 예외가 발생한다.")
    @Test
    void wrongRequestThrowException() {
        // given
        mockServer.enqueue(new MockResponse()
            .setResponseCode(HttpStatus.BAD_REQUEST.value())
            .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_XML)
            .addHeader(HttpHeaders.ACCEPT_CHARSET, StandardCharsets.UTF_8)
        );

        // when
        // then
        assertThatThrownBy(() -> maniaDBSearchService.searchSongs(SEARCH_WORD))
            .isInstanceOf(ExternalApiException.ManiaDBClientException.class);
    }

    @DisplayName("ManiaDB API 서버에서 예외가 발생한 경우, 예외가 발생한다.")
    @Test
    void maniaDBServerExceptionThrowException() {
        // given
        mockServer.enqueue(new MockResponse()
            .setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_XML)
            .addHeader(HttpHeaders.ACCEPT_CHARSET, StandardCharsets.UTF_8)
        );

        // when
        // then
        assertThatThrownBy(() -> maniaDBSearchService.searchSongs(SEARCH_WORD))
            .isInstanceOf(ExternalApiException.ManiaDBServerException.class);
    }
}
