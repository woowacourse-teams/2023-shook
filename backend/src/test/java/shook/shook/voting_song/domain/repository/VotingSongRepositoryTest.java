package shook.shook.voting_song.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import shook.shook.support.UsingJpaTest;
import shook.shook.voting_song.domain.VotingSong;

class VotingSongRepositoryTest extends UsingJpaTest {

    @Autowired
    private VotingSongRepository votingSongRepository;

    @DisplayName("특정 파트 수집 중인 노래 id 를 기준으로 id가 작은 노래를 조회한다.")
    @Nested
    class findSongsLessThanSongId {

        @DisplayName("찾으려는 파트 수집 중인 노래 아이디보다 작은 아이디를 갖는 노래 4개를 조회한다. (이전 노래가 4개보다 많을 때)")
        @Test
        void findFourSongsBeforeSongId() {
            // given
            final VotingSong firstSong = votingSongRepository.save(
                new VotingSong("제목1", "비디오URL", "이미지URL", "가수", 30)
            );
            final VotingSong secondSong = votingSongRepository.save(
                new VotingSong("제목2", "비디오URL", "이미지URL", "가수", 30)
            );
            final VotingSong thirdSong = votingSongRepository.save(
                new VotingSong("제목3", "비디오URL", "이미지URL", "가수", 30)
            );
            final VotingSong fourthSong = votingSongRepository.save(
                new VotingSong("제목4", "비디오URL", "이미지URL", "가수", 30)
            );
            final VotingSong fifthSong = votingSongRepository.save(
                new VotingSong("제목5", "비디오URL", "이미지URL", "가수", 30)
            );
            final VotingSong standardSong = votingSongRepository.save(
                new VotingSong("제목5", "비디오URL", "이미지URL", "가수", 30)
            );

            // when
            final List<VotingSong> beforeVotingSongs = votingSongRepository.findByIdLessThanOrderByIdDesc(
                standardSong.getId(),
                PageRequest.of(0, 4)
            );

            // then
            final List<VotingSong> expected =
                List.of(fifthSong, fourthSong, thirdSong, secondSong);

            assertThat(beforeVotingSongs).usingRecursiveComparison()
                .isEqualTo(expected);
        }

        @DisplayName("찾으려는 파트 수집 중인 노래 아이디보다 작은 아이디를 갖는 노래 4개를 조회한다. (이전 노래가 1개 이상 ~ 4개일 때)")
        @Test
        void findSongsSizeLessThanFourBeforeSongId() {
            // given
            final VotingSong firstSong = votingSongRepository.save(
                new VotingSong("제목1", "비디오URL", "이미지URL", "가수", 30)
            );
            final VotingSong secondSong = votingSongRepository.save(
                new VotingSong("제목2", "비디오URL", "이미지URL", "가수", 30)
            );
            final VotingSong standardSong = votingSongRepository.save(
                new VotingSong("제목3", "비디오URL", "이미지URL", "가수", 30)
            );

            // when
            final List<VotingSong> beforeVotingSongs = votingSongRepository.findByIdLessThanOrderByIdDesc(
                standardSong.getId(),
                PageRequest.of(0, 4)
            );

            // then
            final List<VotingSong> expected = List.of(secondSong, firstSong);

            assertThat(beforeVotingSongs).usingRecursiveComparison()
                .isEqualTo(expected);
        }

        @DisplayName("찾으려는 파트 수집 중인 노래 아이디보다 작은 아이디를 갖는 노래 4개를 조회한다. (이전 노래가 없을 때)")
        @Test
        void findEmptySongsBeforeSongId() {
            // given
            final VotingSong standardSong = votingSongRepository.save(
                new VotingSong("제목1", "비디오URL", "이미지URL", "가수", 30)
            );

            // when
            final List<VotingSong> beforeVotingSongs = votingSongRepository.findByIdLessThanOrderByIdDesc(
                standardSong.getId(),
                PageRequest.of(0, 4)
            );

            // then
            assertThat(beforeVotingSongs).isEmpty();
        }
    }

    @DisplayName("특정 파트 수집 중인 노래 id 를 기준으로 id가 큰 노래를 조회한다.")
    @Nested
    class findSongsGreaterThanSongId {

        @DisplayName("찾으려는 파트 수집 중인 노래 아이디보다 큰 아이디를 갖는 노래 4개를 조회한다. (이후 노래가 4개보다 많을 때)")
        @Test
        void findFourSongsBeforeSongId() {
            // given
            final VotingSong standardSong = votingSongRepository.save(
                new VotingSong("제목1", "비디오URL", "이미지URL", "가수", 30)
            );
            final VotingSong secondSong = votingSongRepository.save(
                new VotingSong("제목2", "비디오URL", "이미지URL", "가수", 30)
            );
            final VotingSong thirdSong = votingSongRepository.save(
                new VotingSong("제목3", "비디오URL", "이미지URL", "가수", 30)
            );
            final VotingSong fourthSong = votingSongRepository.save(
                new VotingSong("제목4", "비디오URL", "이미지URL", "가수", 30)
            );
            final VotingSong fifthSong = votingSongRepository.save(
                new VotingSong("제목5", "비디오URL", "이미지URL", "가수", 30)
            );
            final VotingSong sixthSong = votingSongRepository.save(
                new VotingSong("제목5", "비디오URL", "이미지URL", "가수", 30)
            );

            // when
            final List<VotingSong> beforeVotingSongs = votingSongRepository.findByIdGreaterThanOrderByIdAsc(
                standardSong.getId(),
                PageRequest.of(0, 4)
            );

            // then
            final List<VotingSong> expected =
                List.of(secondSong, thirdSong, fourthSong, fifthSong);

            assertThat(beforeVotingSongs).usingRecursiveComparison()
                .isEqualTo(expected);
        }

        @DisplayName("찾으려는 파트 수집 중인 노래 아이디보다 큰 아이디를 갖는 노래 4개를 조회한다. (이후 노래가 1개 이상 ~ 4개일 때)")
        @Test
        void findSongsSizeLessThanFourAfterSongId() {
            // given
            final VotingSong standardSong = votingSongRepository.save(
                new VotingSong("제목1", "비디오URL", "이미지URL", "가수", 30)
            );
            final VotingSong secondSong = votingSongRepository.save(
                new VotingSong("제목2", "비디오URL", "이미지URL", "가수", 30)
            );
            final VotingSong thirdSong = votingSongRepository.save(
                new VotingSong("제목3", "비디오URL", "이미지URL", "가수", 30)
            );

            // when
            final List<VotingSong> beforeVotingSongs = votingSongRepository.findByIdGreaterThanOrderByIdAsc(
                standardSong.getId(),
                PageRequest.of(0, 4)
            );

            // then
            final List<VotingSong> expected = List.of(secondSong, thirdSong);

            assertThat(beforeVotingSongs).usingRecursiveComparison()
                .isEqualTo(expected);
        }

        @DisplayName("찾으려는 파트 수집 중인 노래 아이디보다 큰 아이디를 갖는 노래 4개를 조회한다. (이후 노래가 없을 때)")
        @Test
        void findEmptySongsAfterSongId() {
            // given
            final VotingSong standardSong = votingSongRepository.save(
                new VotingSong("제목1", "비디오URL", "이미지URL", "가수", 30)
            );

            // when
            final List<VotingSong> afterVotingSongs = votingSongRepository.findByIdGreaterThanOrderByIdAsc(
                standardSong.getId(),
                PageRequest.of(0, 4)
            );

            // then
            assertThat(afterVotingSongs).isEmpty();
        }
    }
}
