package shook.shook.song.application;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import shook.shook.song.application.dto.SearchedSongResponse;
import shook.shook.song.domain.Song;
import shook.shook.song.domain.repository.SongRepository;
import shook.shook.support.UsingJpaTest;

class SongSearchServiceTest extends UsingJpaTest {

    @Autowired
    private SongRepository songRepository;

    private SongSearchService songSearchService;

    @BeforeEach
    void setUp() {
        songSearchService = new SongSearchService(songRepository);
    }

    @DisplayName("제목이 비어있을 때")
    @Nested
    class SongSingerSearch {

        @DisplayName("앞뒤 공백제거, 대소문자 상관없이 정확히 일치하는 가수 이름의 모든 노래를 조회한다.")
        @ParameterizedTest(name = "가수의  이름이 {0} 일 때")
        @ValueSource(strings = {"   redvelvet ", "	Redvelvet\n"})
        void findAllBySinger_exist(final String singer) {
            //given
            final Song saved2 = songRepository.save(new Song("아무노래", "비디오URL", "RedVelvet", 180));
            songRepository.save(new Song("다른노래", "비디오URL", "Red Velvet", 180));

            //when
            saveAndClearEntityManager();
            final List<SearchedSongResponse> responses = songSearchService.findAllBySingerAndTitle(
                singer, null);

            //then
            final List<SearchedSongResponse> expectedResponses = Stream.of(saved2)
                .map(SearchedSongResponse::from)
                .toList();

            assertThat(responses).usingRecursiveComparison()
                .isEqualTo(expectedResponses);
        }

        @DisplayName("정확히 일치하는 가수 이름 조회 시, 일치 결과가 없다면 빈 내용이 반환된다.")
        @Test
        void findAllBySinger_noExist() {
            //given
            //when
            final List<SearchedSongResponse> responses = songSearchService.findAllBySingerAndTitle(
                "가수2", null);

            //then
            assertThat(responses).usingRecursiveComparison()
                .isEqualTo(Collections.emptyList());
        }
    }

    @DisplayName("가수가 비어있을 때")
    @Nested
    class SongTitleSearch {

        @DisplayName("앞뒤 공백제거, 대소문자 상관없이 정확히 일치하는 제목의 모든 노래를 조회한다.")
        @ParameterizedTest(name = "제목이 {0} 일 때")
        @ValueSource(strings = {"   Hi  ", "	HI\n"})
        void findAllByTitle_exist(final String title) {
            //given
            final Song saved2 = songRepository.save(new Song("Hi", "비디오URL", "다른가수", 180));
            final Song saved3 = songRepository.save(new Song("hI", "비디오URL", "가수", 180));

            //when
            final List<SearchedSongResponse> responses = songSearchService.findAllBySingerAndTitle(
                null, title);

            //then
            final List<SearchedSongResponse> expectedResponses = Stream.of(saved2, saved3)
                .map(SearchedSongResponse::from)
                .toList();

            assertThat(responses).usingRecursiveComparison()
                .isEqualTo(expectedResponses);
        }

        @DisplayName("정확히 일치하는 제목 조회 시, 일치 결과가 없다면 빈 배열이 반환된다.")
        @Test
        void findAllByTitle_noExist() {
            //given
            //when
            final List<SearchedSongResponse> responses = songSearchService.findAllBySingerAndTitle(
                null, "다른노래");

            //then
            assertThat(responses).usingRecursiveComparison()
                .isEqualTo(Collections.emptyList());
        }
    }

    @DisplayName("가수와 제목이 둘 다 있을 때")
    @Nested
    class SongSingerAndTitleSearch {

        @DisplayName("앞뒤 공백 제거, 대소문자 상관없이 제목과 가수가 모두 정확히 일치하는 모든 노래를 조회한다.")
        @ParameterizedTest(name = "제목이 {0}, 가수가 {1} 일 때")
        @CsvSource(value = {"   Hi : singer ", "HI:SinGer"}, delimiter = ':')
        void findAllBySingerAndTitle_exist(String title, String singer) {
            //given
            final Song saved2 = songRepository.save(new Song("hi", "비디오URL", "singer", 180));
            songRepository.save(new Song("hi2", "비디오URL", "singer", 180));

            //when
            final List<SearchedSongResponse> responses = songSearchService.findAllBySingerAndTitle(
                singer, title);

            //then
            final List<SearchedSongResponse> expectedResponses = Stream.of(saved2)
                .map(SearchedSongResponse::from)
                .toList();

            assertThat(responses).usingRecursiveComparison()
                .isEqualTo(expectedResponses);
        }

        @DisplayName("제목만 일치하는 경우, 빈 배열이 반환된다.")
        @Test
        void findAllBySingerAndTitle_onlyTitleMatch() {
            //given
            songRepository.save(new Song("노래제목", "비디오URL", "가수", 180));
            songRepository.save(new Song("제목2", "비디오URL", "가수", 180));

            //when
            final List<SearchedSongResponse> responses = songSearchService.findAllBySingerAndTitle(
                "가수2", "노래제목");

            //then
            assertThat(responses).usingRecursiveComparison()
                .isEqualTo(Collections.emptyList());
        }

        @DisplayName("가수만 일치하는 경우, 빈 배열이 반환된다.")
        @Test
        void findAllBySingerAndTitle_onlySingerMatch() {
            //given
            songRepository.save(new Song("노래제목", "비디오URL", "가수", 180));
            songRepository.save(new Song("제목2", "비디오URL", "가수", 180));

            //when
            final List<SearchedSongResponse> responses = songSearchService.findAllBySingerAndTitle(
                "가수", "노래제목2");

            //then
            assertThat(responses).usingRecursiveComparison()
                .isEqualTo(Collections.emptyList());
        }
    }

    @DisplayName("노래와 가수가 둘 다 비어있다면 빈 노래 목록이 조회된다.")
    @Test
    void findBySingerAndTitle_emptyInput() {
        //given
        //when
        final List<SearchedSongResponse> responses = songSearchService.findAllBySingerAndTitle(
            null, null);

        //then
        assertThat(responses).usingRecursiveComparison()
            .isEqualTo(Collections.emptyList());
    }
}
