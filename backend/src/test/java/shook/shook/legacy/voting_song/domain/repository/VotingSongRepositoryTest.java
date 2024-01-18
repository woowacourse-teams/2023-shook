package shook.shook.legacy.voting_song.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import shook.shook.legacy.song.domain.Artist;
import shook.shook.legacy.song.domain.repository.ArtistRepository;
import shook.shook.legacy.support.UsingJpaTest;
import shook.shook.legacy.voting_song.domain.VotingSong;

class VotingSongRepositoryTest extends UsingJpaTest {

    @Autowired
    private VotingSongRepository votingSongRepository;

    @Autowired
    private ArtistRepository artistRepository;

    private VotingSong saveVotingSongWithTitle(final String votingSongTitle) {
        final Artist artist = new Artist("profile", "가수");
        final VotingSong votingSong = new VotingSong(
            votingSongTitle,
            "12345678901",
            "이미지URL",
            artist,
            180
        );

        artistRepository.save(artist);
        return votingSongRepository.save(votingSong);
    }

    @DisplayName("특정 파트 수집 중인 노래 id 를 기준으로 id가 작은 노래를 조회한다.")
    @Nested
    class findSongsLessThanSongId {

        @DisplayName("앞뒤로 노래들이 충분히 존재할 때")
        @Test
        void enough() {
            // given
            final VotingSong firstSong = saveVotingSongWithTitle("제목1");
            final VotingSong secondSong = saveVotingSongWithTitle("제목2");
            final VotingSong thirdSong = saveVotingSongWithTitle("제목3");
            final VotingSong fourthSong = saveVotingSongWithTitle("제목4");
            final VotingSong fifthSong = saveVotingSongWithTitle("제목5");
            final VotingSong standardSong = saveVotingSongWithTitle("제목5");
            final VotingSong seventhSong = saveVotingSongWithTitle("제목7");
            final VotingSong eighthSong = saveVotingSongWithTitle("제목8");
            final VotingSong ninthSong = saveVotingSongWithTitle("제목9");
            final VotingSong tenthSong = saveVotingSongWithTitle("제목10");
            final VotingSong eleventhSong = saveVotingSongWithTitle("제목11");
            // when
            final List<VotingSong> beforeVotingSongs =
                votingSongRepository.findByIdGreaterThanEqualAndIdLessThanEqual(
                    standardSong.getId() - 4, standardSong.getId() + 4
                );

            // then
            final List<VotingSong> expected =
                List.of(
                    secondSong,
                    thirdSong,
                    fourthSong,
                    fifthSong,
                    standardSong,
                    seventhSong,
                    eighthSong,
                    ninthSong,
                    tenthSong
                );

            assertThat(beforeVotingSongs).usingRecursiveComparison()
                .isEqualTo(expected);
        }

        @DisplayName("이전 노래가 4개보다 적을 때")
        @Test
        void prevSongNotEnough() {
            // given
            final VotingSong firstSong = saveVotingSongWithTitle("제목1");
            final VotingSong secondSong = saveVotingSongWithTitle("제목2");
            final VotingSong standardSong = saveVotingSongWithTitle("제목3");
            final VotingSong fourthSong = saveVotingSongWithTitle("제목4");
            final VotingSong fifthSong = saveVotingSongWithTitle("제목5");
            final VotingSong sixthSong = saveVotingSongWithTitle("제목6");
            final VotingSong seventhSong = saveVotingSongWithTitle("제목7");
            final VotingSong eighthSong = saveVotingSongWithTitle("제목8");
            final VotingSong ninthSong = saveVotingSongWithTitle("제목9");
            // when
            final List<VotingSong> beforeVotingSongs =
                votingSongRepository.findByIdGreaterThanEqualAndIdLessThanEqual(
                    standardSong.getId() - 4, standardSong.getId() + 4
                );

            // then
            final List<VotingSong> expected =
                List.of(
                    firstSong,
                    secondSong,
                    standardSong,
                    fourthSong,
                    fifthSong,
                    sixthSong,
                    seventhSong
                );

            assertThat(beforeVotingSongs).usingRecursiveComparison()
                .isEqualTo(expected);
        }

        @DisplayName("다음 노래가 4개보다 적을 때")
        @Test
        void nextSongNotEnough() {
            // given
            final VotingSong firstSong = saveVotingSongWithTitle("제목1");
            final VotingSong secondSong = saveVotingSongWithTitle("제목2");
            final VotingSong thirdSong = saveVotingSongWithTitle("제목3");
            final VotingSong fourthSong = saveVotingSongWithTitle("제목4");
            final VotingSong fifthSong = saveVotingSongWithTitle("제목5");
            final VotingSong sixthSong = saveVotingSongWithTitle("제목6");
            final VotingSong standardSong = saveVotingSongWithTitle("제목7");
            final VotingSong eighthSong = saveVotingSongWithTitle("제목8");
            final VotingSong ninthSong = saveVotingSongWithTitle("제목9");
            // when
            final List<VotingSong> beforeVotingSongs =
                votingSongRepository.findByIdGreaterThanEqualAndIdLessThanEqual(
                    standardSong.getId() - 4, standardSong.getId() + 4
                );

            // then
            final List<VotingSong> expected =
                List.of(
                    thirdSong,
                    fourthSong,
                    fifthSong,
                    sixthSong,
                    standardSong,
                    eighthSong,
                    ninthSong
                );

            assertThat(beforeVotingSongs).usingRecursiveComparison()
                .isEqualTo(expected);
        }

        @DisplayName("이전 노래, 다음 노래 모두 4개보다 적을 때")
        @Test
        void bothNotEnough() {
            // given
            final VotingSong firstSong = saveVotingSongWithTitle("제목1");
            final VotingSong secondSong = saveVotingSongWithTitle("제목2");
            final VotingSong thirdSong = saveVotingSongWithTitle("제목3");
            final VotingSong standardSong = saveVotingSongWithTitle("제목4");
            final VotingSong fifthSong = saveVotingSongWithTitle("제목5");
            final VotingSong sixthSong = saveVotingSongWithTitle("제목6");

            // when
            final List<VotingSong> beforeVotingSongs =
                votingSongRepository.findByIdGreaterThanEqualAndIdLessThanEqual(
                    standardSong.getId() - 4, standardSong.getId() + 4
                );

            // then
            final List<VotingSong> expected =
                List.of(
                    firstSong,
                    secondSong,
                    thirdSong,
                    standardSong,
                    fifthSong,
                    sixthSong
                );

            assertThat(beforeVotingSongs).usingRecursiveComparison()
                .isEqualTo(expected);
        }
    }
}
