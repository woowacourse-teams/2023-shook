package shook.shook.song.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import jakarta.persistence.EntityManager;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import shook.shook.member.domain.Member;
import shook.shook.member.domain.repository.MemberRepository;
import shook.shook.song.domain.killingpart.KillingPart;
import shook.shook.song.domain.killingpart.KillingPartLike;
import shook.shook.song.domain.repository.SongRepository;
import shook.shook.support.UsingJpaTest;

@Sql("classpath:/killingpart/initialize_killing_part_song.sql")
class InMemorySongsTest extends UsingJpaTest {

    private static Member MEMBER;

    private InMemorySongs inMemorySongs;

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    void setUp() {
        MEMBER = memberRepository.findById(1L).get();
        inMemorySongs = new InMemorySongs(entityManager);
    }

    @DisplayName("InMemorySong 을 1.좋아요 순, 2. id 순으로 정렬된 노래로 초기화한다.")
    @Test
    void recreate() {
        // given
        final List<Song> songs = songRepository.findAllWithKillingParts();
        likeAllKillingPartsInSong(songs.get(0));
        likeAllKillingPartsInSong(songs.get(1));

        // when
        inMemorySongs.recreate(songs);
        // 정렬 순서: 2L, 1L, 4L

        // then
        final List<Song> songsInMemory = inMemorySongs.getSongs();
        assertAll(
            () -> assertThat(songsInMemory.get(0)).hasFieldOrPropertyWithValue("id", 2L),
            () -> assertThat(songsInMemory.get(1)).hasFieldOrPropertyWithValue("id", 1L),
            () -> assertThat(songsInMemory.get(2)).hasFieldOrPropertyWithValue("id", 4L)
        );
    }

    private void likeAllKillingPartsInSong(final Song song) {
        final List<KillingPart> killingParts = song.getKillingParts();
        for (final KillingPart killingPart : killingParts) {
            killingPart.like(new KillingPartLike(killingPart, MEMBER));
        }
    }

    @DisplayName("노래 id 로 Song 을 조회한다.")
    @Test
    void getSongById() {
        // given
        final List<Song> songs = songRepository.findAllWithKillingParts();
        inMemorySongs.recreate(songs);

        // when
        final Song foundSong = inMemorySongs.getSongById(4L);

        // then
        final Song expectedSong = songs.get(0);
        assertThat(foundSong).isEqualTo(expectedSong);
    }

    @DisplayName("특정 노래에 대해 1. 좋아요 수가 더 적거나 2. 좋아요 수가 같은 경우 id가 더 작은 노래 목록을 조회한다.")
    @Test
    void getPrevLikedSongs() {
        // given
        final List<Song> songs = songRepository.findAllWithKillingParts();
        final Song firstSong = songs.get(0);
        final Song secondSong = songs.get(1);
        final Song thirdSong = songs.get(2);
        final Song fourthSong = songs.get(3);
        likeAllKillingPartsInSong(firstSong);
        likeAllKillingPartsInSong(secondSong);
        inMemorySongs.recreate(songs); // second, first, fourth, third

        // when
        final List<Song> prevLikedSongs = inMemorySongs.getPrevLikedSongs(thirdSong, 2);

        // then
        assertAll(
            () -> assertThat(prevLikedSongs).hasSize(2),
            () -> assertThat(prevLikedSongs.get(0)).isEqualTo(firstSong),
            () -> assertThat(prevLikedSongs.get(1)).isEqualTo(fourthSong)
        );
    }

    @DisplayName("특정 노래에 대해 1. 좋아요 수가 더 많거나 2. 좋아요 수가 같은 경우 id가 더 큰 노래 목록을 조회한다.")
    @Test
    void getNextLikedSongs() {
        // given
        final List<Song> songs = songRepository.findAllWithKillingParts();
        final Song firstSong = songs.get(0);
        final Song secondSong = songs.get(1);
        final Song thirdSong = songs.get(2);
        final Song fourthSong = songs.get(3);
        likeAllKillingPartsInSong(firstSong);
        likeAllKillingPartsInSong(secondSong);
        inMemorySongs.recreate(songs); // second, first, fourth, third

        // when
        final List<Song> nextLikedSongs = inMemorySongs.getNextLikedSongs(secondSong, 2);

        // then
        assertAll(
            () -> assertThat(nextLikedSongs).hasSize(2),
            () -> assertThat(nextLikedSongs.get(0)).isEqualTo(firstSong),
            () -> assertThat(nextLikedSongs.get(1)).isEqualTo(fourthSong)
        );
    }

    @DisplayName("특정 장르 노래에 대해 1. 좋아요 수가 더 적거나 2. 좋아요 수가 같은 경우 id가 더 작은 노래 목록을 조회한다.")
    @Test
    void getSortedSongsByGenre() {
        final List<Song> songs = songRepository.findAllWithKillingParts();
        final Song firstSong = songs.get(0);
        final Song secondSong = songs.get(1);
        final Song thirdSong = songs.get(2);
        final Song fourthSong = songs.get(3);
        likeAllKillingPartsInSong(firstSong);
        likeAllKillingPartsInSong(secondSong);
        inMemorySongs.recreate(songs); // first, fourth, third

        // when
        final List<Song> prevLikedSongs = inMemorySongs.getPrevLikedSongByGenre(firstSong, Genre.DANCE, 2);

        // then
        assertThat(prevLikedSongs)
            .usingRecursiveComparison()
            .comparingOnlyFields("id")
            .isEqualTo(List.of(4L, 3L));
    }

    @DisplayName("특정 장르 노래에 대해 1. 좋아요 수가 더 많거나 2. 좋아요 수가 같은 경우 id가 더 큰 노래 목록을 조회한다.")
    @Test
    void getPrevLikedSongByGenre() {
        final List<Song> songs = songRepository.findAllWithKillingParts();
        final Song firstSong = songs.get(0);
        final Song secondSong = songs.get(1);
        final Song thirdSong = songs.get(2);
        final Song fourthSong = songs.get(3);
        likeAllKillingPartsInSong(firstSong);
        likeAllKillingPartsInSong(secondSong);
        inMemorySongs.recreate(songs); // first, fourth, third

        // when
        final List<Song> prevLikedSongs = inMemorySongs.getNextLikedSongByGenre(thirdSong, Genre.DANCE, 2);

        // then
        assertThat(prevLikedSongs)
            .usingRecursiveComparison()
            .comparingOnlyFields("id")
            .isEqualTo(List.of(1L, 4L));
    }
}
