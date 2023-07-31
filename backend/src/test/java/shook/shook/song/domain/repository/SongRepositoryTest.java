package shook.shook.song.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import shook.shook.song.domain.Song;
import shook.shook.song.domain.SongTitle;
import shook.shook.song.domain.repository.dto.SongTotalVoteCountDto;
import shook.shook.support.UsingJpaTest;

class SongRepositoryTest extends UsingJpaTest {

    @Autowired
    private SongRepository songRepository;

    @DisplayName("Song 을 저장한다.")
    @Test
    void save() {
        //given
        final Song song = new Song("제목", "비디오URL", "이미지URL", "가수", 5);

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
        final Song song = new Song("노래제목", "비디오URL", "이미지URL", "가수", 180);
        songRepository.save(song);

        //when
        saveAndClearEntityManager();
        final Optional<Song> findSong = songRepository.findById(song.getId());

        //then
        assertThat(findSong).isPresent();
        assertThat(findSong.get()).usingRecursiveComparison()
            .isEqualTo(song);
    }

    @DisplayName("제목으로 Song 을 조회한다.")
    @Test
    void findByTitle() {
        //given
        final Song song = new Song("노래제목", "비디오URL", "이미지URL", "가수", 180);
        songRepository.save(song);

        //when
        saveAndClearEntityManager();
        final Optional<Song> findSong = songRepository.findByTitle(new SongTitle("노래제목"));

        //then
        assertThat(findSong).isPresent();
        assertThat(findSong.get()).usingRecursiveComparison()
            .isEqualTo(song);
    }

    @DisplayName("Song 을 저장할 때의 시간 정보로 createAt이 자동 생성된다.")
    @Test
    void createdAt_prePersist() {
        //given
        final Song song = new Song("제목", "비디오URL", "이미지URL", "가수", 5);

        //when
        final LocalDateTime prev = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
        final Song saved = songRepository.save(song);
        final LocalDateTime after = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);

        //then
        assertThat(song).isSameAs(saved);
        assertThat(song.getCreatedAt()).isBetween(prev, after);
    }

    @DisplayName("모든 노래와 노래의 총 득표수를 가진 객체 리스트를 반환한다.")
    @Test
    void findSongAndTotalVoteCount() {
        //given
        //when
        final List<SongTotalVoteCountDto> songWithTotalVoteCount = songRepository.findSongWithTotalVoteCount();

        //then
        final List<Song> songs = songWithTotalVoteCount.stream()
            .map(SongTotalVoteCountDto::getSong)
            .toList();
        final List<Long> totalVotes = songWithTotalVoteCount.stream()
            .map(SongTotalVoteCountDto::getTotalVoteCount)
            .toList();

        final List<Song> expectedSongs = songRepository.findAll();
        final List<Long> expectedTotalVotes = IntStream.range(0, 40)
            .mapToObj((index) -> 0L)
            .toList();

        assertThat(songs).usingRecursiveComparison()
            .isEqualTo(expectedSongs);
        assertThat(totalVotes).usingRecursiveComparison()
            .isEqualTo(expectedTotalVotes);

    }
}
