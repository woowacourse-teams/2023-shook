package shook.shook.song.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import shook.shook.song.domain.Song;
import shook.shook.song.domain.SongTitle;
import shook.shook.support.UsingJpaTest;

class SongRepositoryTest extends UsingJpaTest {

    @Autowired
    private SongRepository songRepository;

    @DisplayName("Song 을 저장한다.")
    @Test
    void save() {
        //given
        final Song song = new Song("제목", "비디오URL", "가수", 5);

        //when
        final Song save = songRepository.save(song);

        //then
        assertThat(song).isSameAs(save);
        assertThat(save.getId()).isNotNull();
    }

    @DisplayName("Id로 Song 을 조회한다.")
    @Test
    void findById() {
        //given
        final Song song = new Song("노래제목", "비디오URL", "가수", 180);
        songRepository.save(song);

        //when
        saveAndClearEntityManager();
        final Optional<Song> findSong = songRepository.findById(song.getId());

        //then
        assertThat(findSong).isPresent();
        assertThat(findSong.get()).usingRecursiveComparison().isEqualTo(song);
    }

    @DisplayName("제목으로 Song 을 조회한다.")
    @Test
    void findByTitle() {
        //given
        final Song song = new Song("노래제목", "비디오URL", "가수", 180);
        songRepository.save(song);

        //when
        saveAndClearEntityManager();
        final Optional<Song> findSong = songRepository.findByTitle(new SongTitle("노래제목"));

        //then
        assertThat(findSong).isPresent();
        assertThat(findSong.get()).usingRecursiveComparison().isEqualTo(song);
    }

    @DisplayName("Song 을 저장할 때의 시간 정보로 createAt이 자동 생성된다.")
    @Test
    void createdAt_prePersist() {
        //given
        final Song song = new Song("제목", "비디오URL", "가수", 5);

        //when
        final LocalDateTime prev = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
        final Song saved = songRepository.save(song);
        final LocalDateTime after = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);

        //then
        assertThat(song).isSameAs(saved);
        assertThat(song.getCreatedAt()).isBetween(prev, after);
    }

    @DisplayName("대소문자 상관없이 정확히 일치하는 가수를 가진 모든 Song 목록을 조회한다.")
    @ParameterizedTest(name = "가수의  이름이 {0} 일 때")
    @ValueSource(strings = {"redvelvet", "Redvelvet"})
    void findAllBySinger(final String singer) {
        //given
        final Song song1 = new Song("노래제목", "비디오URL", "RedVelvet", 180);
        final Song song2 = new Song("노래제목2", "비디오URL", "RedVelvet", 100);
        songRepository.save(song1);
        songRepository.save(song2);

        //when
        saveAndClearEntityManager();
        final List<Song> songs = songRepository.findAllBySingerIgnoringCase(singer);

        //then
        assertThat(songs).usingRecursiveComparison()
            .isEqualTo(List.of(song1, song2));
    }

    @DisplayName("대소문자 상관없이 정확히 일치하는 제목을 가진 모든 Song 목록을 조회한다.")
    @ParameterizedTest(name = "제목이 {0} 일 때")
    @ValueSource(strings = {"hi", "HI"})
    void findAllByTitle(final String title) {
        //given
        final Song song1 = new Song("Hi", "비디오URL", "가수", 180);
        final Song song2 = new Song("hI", "비디오URL", "가수", 100);
        songRepository.save(song1);
        songRepository.save(song2);

        //when
        saveAndClearEntityManager();
        final List<Song> songs = songRepository.findAllByTitleIgnoringCase(title);

        //then
        assertThat(songs).usingRecursiveComparison()
            .isEqualTo(List.of(song1, song2));
    }
}
