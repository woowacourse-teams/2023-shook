package shook.shook.song.application;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
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

    @DisplayName("노래 검색시 가수 이름만 존재하고 제목이 없을 때")
    @Nested
    class SongSingerSearch {

        @DisplayName("일치하는 가수의 모든 노래를 조회한다. (가수 이름은 앞뒤 공백을 제거하고 대소문자를 무시한다.)")
        @ParameterizedTest(name = "가수의  이름이 {0} 일 때")
        @ValueSource(strings = {"   redvelvet ", "	Redvelvet\n"})
        void findAllBySinger_exist(final String singer) {
            //given
            final Song song = songRepository.save(
                new Song("아무노래", "비디오URL", "image", "RedVelvet", 180));
            songRepository.save(new Song("다른노래", "비디오URL", "image", "Red Velvet", 180));

            //when
            saveAndClearEntityManager();
            final List<SearchedSongResponse> responses = songSearchService.findAllBySingerAndTitle(
                singer, null);

            //then
            final List<SearchedSongResponse> expectedResponses = List.of(
                SearchedSongResponse.from(song)
            );

            assertThat(responses).usingRecursiveComparison()
                .isEqualTo(expectedResponses);
        }

        @DisplayName("일치하는 가수의 모든 노래를 조회할 때, 일치 결과가 없다면 빈 노래 목록이 조회된다.")
        @Test
        void findAllBySinger_noExist() {
            //given
            //when
            final List<SearchedSongResponse> responses = songSearchService.findAllBySingerAndTitle(
                "가수2", null);

            //then
            assertThat(responses).isEmpty();
        }
    }

    @DisplayName("노래 검색시 제목만 존재하고 가수 이름이 없을 때")
    @Nested
    class SongTitleSearch {

        @DisplayName("일치하는 제목의 모든 노래를 조회한다. (제목은 앞뒤 공백을 제거하고 대소문자를 무시한다.)")
        @ParameterizedTest(name = "제목이 {0} 일 때")
        @ValueSource(strings = {"   Hi  ", "	HI\n"})
        void findAllByTitle_exist(final String title) {
            //given
            final Song song1 = songRepository.save(new Song("Hi", "비디오URL", "image", "다른가수", 180));
            final Song song2 = songRepository.save(new Song("hI", "비디오URL", "image", "가수", 180));

            //when
            final List<SearchedSongResponse> responses = songSearchService.findAllBySingerAndTitle(
                null, title);

            //then
            final List<SearchedSongResponse> expectedResponses = List.of(
                SearchedSongResponse.from(song1),
                SearchedSongResponse.from(song2)
            );

            assertThat(responses).usingRecursiveComparison()
                .isEqualTo(expectedResponses);
        }

        @DisplayName("일치하는 제목을 조회할 때, 일치 결과가 없다면 빈 노래 목록이 조회된다.")
        @Test
        void findAllByTitle_noExist() {
            //given
            //when
            final List<SearchedSongResponse> responses = songSearchService.findAllBySingerAndTitle(
                null, "다른노래");

            //then
            assertThat(responses).isEmpty();
        }
    }

    @DisplayName("가수와 제목이 둘 다 있을 때")
    @Nested
    class SongSingerAndTitleSearch {

        @DisplayName("일치하는 가수와 제목의 모든 노래를 조회한다. (가수 이름, 제목은 앞뒤 공백을 제거하고 대소문자를 무시한다.)")
        @ParameterizedTest(name = "제목이 {0}, 가수가 {1} 일 때")
        @CsvSource(value = {"   Hi : singer ", "HI:SinGer"}, delimiter = ':')
        void findAllBySingerAndTitle_exist(String title, String singer) {
            //given
            final Song song = songRepository.save(new Song("hi", "비디오URL", "image", "singer", 180));
            songRepository.save(new Song("hi2", "비디오URL", "image", "singer", 180));

            //when
            final List<SearchedSongResponse> responses = songSearchService.findAllBySingerAndTitle(
                singer, title);

            //then
            final List<SearchedSongResponse> expectedResponses = List.of(
                SearchedSongResponse.from(song)
            );

            assertThat(responses).usingRecursiveComparison()
                .isEqualTo(expectedResponses);
        }

        @DisplayName("제목만 일치하는 경우, 빈 노래 목록이 조회된다.")
        @Test
        void findAllBySingerAndTitle_onlyTitleMatch() {
            //given
            songRepository.save(new Song("노래제목", "비디오URL", "image", "가수", 180));
            songRepository.save(new Song("제목2", "비디오URL", "image", "가수", 180));

            //when
            final List<SearchedSongResponse> responses = songSearchService.findAllBySingerAndTitle(
                "가수2", "노래제목");

            //then
            assertThat(responses).isEmpty();
        }

        @DisplayName("가수만 일치하는 경우, 빈 노래 목록이 조회된다.")
        @Test
        void findAllBySingerAndTitle_onlySingerMatch() {
            //given
            songRepository.save(new Song("노래제목", "비디오URL", "image", "가수", 180));
            songRepository.save(new Song("제목2", "비디오URL", "image", "가수", 180));

            //when
            final List<SearchedSongResponse> responses = songSearchService.findAllBySingerAndTitle(
                "가수", "노래제목2");

            //then
            assertThat(responses).isEmpty();
        }
    }

    @DisplayName("노래와 가수가 둘 다 없다면 빈 노래 목록이 조회된다.")
    @Test
    void findBySingerAndTitle_emptyInput() {
        //given
        //when
        final List<SearchedSongResponse> responses = songSearchService.findAllBySingerAndTitle(
            null, null);

        //then
        assertThat(responses).isEmpty();
    }
}
