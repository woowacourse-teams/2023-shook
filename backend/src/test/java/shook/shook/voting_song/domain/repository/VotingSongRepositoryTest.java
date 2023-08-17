package shook.shook.voting_song.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import shook.shook.support.UsingJpaTest;
import shook.shook.voting_song.domain.VotingSong;

class VotingSongRepositoryTest extends UsingJpaTest {

    @Autowired
    private VotingSongRepository votingSongRepository;

    @DisplayName("특정 파트 수집 중인 노래 id 를 기준으로 id가 작은 노래를 조회한다.")
    @Nested
    class findSongsLessThanSongId {

        @DisplayName("앞뒤로 노래들이 충분히 존재할 때")
        @Test
        void enough() {
            // given
            final VotingSong firstSong = votingSongRepository.save(
                new VotingSong("제목1", "비디오ID는 11글자", "이미지URL", "가수", 30)
            );
            final VotingSong secondSong = votingSongRepository.save(
                new VotingSong("제목2", "비디오ID는 11글자", "이미지URL", "가수", 30)
            );
            final VotingSong thirdSong = votingSongRepository.save(
                new VotingSong("제목3", "비디오ID는 11글자", "이미지URL", "가수", 30)
            );
            final VotingSong fourthSong = votingSongRepository.save(
                new VotingSong("제목4", "비디오ID는 11글자", "이미지URL", "가수", 30)
            );
            final VotingSong fifthSong = votingSongRepository.save(
                new VotingSong("제목5", "비디오ID는 11글자", "이미지URL", "가수", 30)
            );
            final VotingSong standardSong = votingSongRepository.save(
                new VotingSong("제목5", "비디오ID는 11글자", "이미지URL", "가수", 30)
            );
            final VotingSong seventhSong = votingSongRepository.save(
                new VotingSong("제목7", "비디오ID는 11글자", "이미지URL", "가수", 30)
            );
            final VotingSong eighthSong = votingSongRepository.save(
                new VotingSong("제목8", "비디오ID는 11글자", "이미지URL", "가수", 30)
            );
            final VotingSong ninthSong = votingSongRepository.save(
                new VotingSong("제목9", "비디오ID는 11글자", "이미지URL", "가수", 30)
            );
            final VotingSong tenthSong = votingSongRepository.save(
                new VotingSong("제목10", "비디오ID는 11글자", "이미지URL", "가수", 30)
            );
            final VotingSong eleventhSong = votingSongRepository.save(
                new VotingSong("제목11", "비디오ID는 11글자", "이미지URL", "가수", 30)
            );
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
            final VotingSong firstSong = votingSongRepository.save(
                new VotingSong("제목1", "비디오ID는 11글자", "이미지URL", "가수", 30)
            );
            final VotingSong secondSong = votingSongRepository.save(
                new VotingSong("제목2", "비디오ID는 11글자", "이미지URL", "가수", 30)
            );
            final VotingSong standardSong = votingSongRepository.save(
                new VotingSong("제목3", "비디오ID는 11글자", "이미지URL", "가수", 30)
            );
            final VotingSong fourthSong = votingSongRepository.save(
                new VotingSong("제목4", "비디오ID는 11글자", "이미지URL", "가수", 30)
            );
            final VotingSong fifthSong = votingSongRepository.save(
                new VotingSong("제목5", "비디오ID는 11글자", "이미지URL", "가수", 30)
            );
            final VotingSong sixthSong = votingSongRepository.save(
                new VotingSong("제목6", "비디오ID는 11글자", "이미지URL", "가수", 30)
            );
            final VotingSong seventhSong = votingSongRepository.save(
                new VotingSong("제목7", "비디오ID는 11글자", "이미지URL", "가수", 30)
            );
            final VotingSong eighthSong = votingSongRepository.save(
                new VotingSong("제목8", "비디오ID는 11글자", "이미지URL", "가수", 30)
            );
            final VotingSong ninthSong = votingSongRepository.save(
                new VotingSong("제목9", "비디오ID는 11글자", "이미지URL", "가수", 30)
            );
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
            final VotingSong firstSong = votingSongRepository.save(
                new VotingSong("제목1", "비디오ID는 11글자", "이미지URL", "가수", 30)
            );
            final VotingSong secondSong = votingSongRepository.save(
                new VotingSong("제목2", "비디오ID는 11글자", "이미지URL", "가수", 30)
            );
            final VotingSong thirdSong = votingSongRepository.save(
                new VotingSong("제목3", "비디오ID는 11글자", "이미지URL", "가수", 30)
            );
            final VotingSong fourthSong = votingSongRepository.save(
                new VotingSong("제목4", "비디오ID는 11글자", "이미지URL", "가수", 30)
            );
            final VotingSong fifthSong = votingSongRepository.save(
                new VotingSong("제목5", "비디오ID는 11글자", "이미지URL", "가수", 30)
            );
            final VotingSong sixthSong = votingSongRepository.save(
                new VotingSong("제목6", "비디오ID는 11글자", "이미지URL", "가수", 30)
            );
            final VotingSong standardSong = votingSongRepository.save(
                new VotingSong("제목7", "비디오ID는 11글자", "이미지URL", "가수", 30)
            );
            final VotingSong eighthSong = votingSongRepository.save(
                new VotingSong("제목8", "비디오ID는 11글자", "이미지URL", "가수", 30)
            );
            final VotingSong ninthSong = votingSongRepository.save(
                new VotingSong("제목9", "비디오ID는 11글자", "이미지URL", "가수", 30)
            );
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
            final VotingSong firstSong = votingSongRepository.save(
                new VotingSong("제목1", "비디오ID는 11글자", "이미지URL", "가수", 30)
            );
            final VotingSong secondSong = votingSongRepository.save(
                new VotingSong("제목2", "비디오ID는 11글자", "이미지URL", "가수", 30)
            );
            final VotingSong thirdSong = votingSongRepository.save(
                new VotingSong("제목3", "비디오ID는 11글자", "이미지URL", "가수", 30)
            );
            final VotingSong standardSong = votingSongRepository.save(
                new VotingSong("제목4", "비디오ID는 11글자", "이미지URL", "가수", 30)
            );
            final VotingSong fifthSong = votingSongRepository.save(
                new VotingSong("제목5", "비디오ID는 11글자", "이미지URL", "가수", 30)
            );
            final VotingSong sixthSong = votingSongRepository.save(
                new VotingSong("제목6", "비디오ID는 11글자", "이미지URL", "가수", 30)
            );

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
