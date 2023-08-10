package shook.shook.song.application;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import shook.shook.song.application.dto.UnregisteredSongResponse;
import shook.shook.song.application.dto.maniadb.ManiaDBAPISearchResponse;
import shook.shook.song.application.dto.maniadb.SearchedSongFromManiaDBApiResponses;
import shook.shook.song.exception.ExternalApiException;
import shook.shook.song.exception.UnregisteredSongException;
import shook.shook.util.StringChecker;

@RequiredArgsConstructor
@Service
public class ManiaDBSearchService {

    private static final String MANIA_DB_API_URI = "/%s/?sr=song&display=%d&key=example&v=0.5";
    private static final int SEARCH_SIZE = 100;
    private static final String SPECIAL_MARK_REGEX = "[^ㄱ-ㅎㅏ-ㅣ가-힣a-zA-Z0-9,. ]";

    private final WebClient webClient;

    public List<UnregisteredSongResponse> searchSongs(final String searchWord) {
        validateSearchWord(searchWord);

        final String parsedSearchWord = replaceSpecialMark(searchWord);
        final SearchedSongFromManiaDBApiResponses searchResult = getSearchResult(parsedSearchWord);

        if (Objects.isNull(searchResult.getSongs())) {
            return Collections.emptyList();
        }

        return searchResult.getSongs().stream()
            .map(UnregisteredSongResponse::from)
            .toList();
    }

    private void validateSearchWord(final String searchWord) {
        if (StringChecker.isNullOrBlank(searchWord)) {
            throw new UnregisteredSongException.NullOrBlankSearchWordException();
        }
    }

    private String replaceSpecialMark(final String rawSearchWord) {
        return rawSearchWord.replaceAll(SPECIAL_MARK_REGEX, "");
    }

    private SearchedSongFromManiaDBApiResponses getSearchResult(final String searchWord) {
        final String searchUrl = String.format(MANIA_DB_API_URI, searchWord, SEARCH_SIZE);
        final ManiaDBAPISearchResponse result = getResultFromManiaDB(searchUrl);

        if (Objects.isNull(result)) {
            throw new ExternalApiException.EmptyResultException();
        }

        return result.getSongs();
    }

    private ManiaDBAPISearchResponse getResultFromManiaDB(final String searchUrl) {
        return webClient.get()
            .uri(searchUrl)
            .accept(MediaType.TEXT_XML)
            .acceptCharset(StandardCharsets.UTF_8)
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError, (clientResponse) -> {
                throw new ExternalApiException.ManiaDBClientException();
            })
            .onStatus(HttpStatusCode::is5xxServerError, (clientResponse) -> {
                throw new ExternalApiException.ManiaDBServerException();
            })
            .bodyToMono(ManiaDBAPISearchResponse.class)
            .block();
    }
}
