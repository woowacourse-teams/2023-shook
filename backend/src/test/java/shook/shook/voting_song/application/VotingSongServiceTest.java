package shook.shook.voting_song.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import shook.shook.song.domain.SongTitle;
import shook.shook.support.UsingJpaTest;
import shook.shook.voting_song.application.dto.VotingSongRegisterRequest;
import shook.shook.voting_song.application.dto.VotingSongResponse;
import shook.shook.voting_song.domain.VotingSong;
import shook.shook.voting_song.domain.repository.VotingSongRepository;

class VotingSongServiceTest extends UsingJpaTest {

    @Autowired
    private VotingSongRepository votingSongRepository;

    private VotingSongService votingSongService;

    @BeforeEach
    void setUp() {
        votingSongService = new VotingSongService(votingSongRepository);
    }

    @DisplayName("파트 수집 중인 노래를 등록한다.")
    @Test
    void register() {
        //given
        final VotingSongRegisterRequest request =
            new VotingSongRegisterRequest("새로운노래제목", "비디오URL", "이미지URL", "가수", 180);

        //when
        votingSongService.register(request);
        saveAndClearEntityManager();

        //then
        final VotingSong savedSong = votingSongRepository.findByTitle(new SongTitle("새로운노래제목"))
            .get();

        assertAll(
            () -> assertThat(savedSong.getId()).isNotNull(),
            () -> assertThat(savedSong.getCreatedAt()).isNotNull(),
            () -> assertThat(savedSong.getTitle()).isEqualTo("새로운노래제목"),
            () -> assertThat(savedSong.getVideoUrl()).isEqualTo("비디오URL"),
            () -> assertThat(savedSong.getSinger()).isEqualTo("가수"),
            () -> assertThat(savedSong.getLength()).isEqualTo(180)
        );
    }

    @DisplayName("노래 목록을 모두 조회한다.")
    @Nested
    class findAll {

        @DisplayName("파트 수집 중인 노래 목록을 모두 조회한다.")
        @Test
        void findAllVotingSongs() {
            // given
            final VotingSong firstSong =
                votingSongRepository.save(new VotingSong("노래1", "비디오URL", "이미지URL", "가수", 180));
            final VotingSong secondSong =
                votingSongRepository.save(new VotingSong("노래2", "비디오URL", "이미지URL", "가수", 180));
            final VotingSong thirdSong =
                votingSongRepository.save(new VotingSong("노래3", "비디오URL", "이미지URL", "가수", 180));
            final VotingSong fourthSong =
                votingSongRepository.save(new VotingSong("노래4", "비디오URL", "이미지URL", "가수", 180));
            final VotingSong fifthSong =
                votingSongRepository.save(new VotingSong("노래5", "비디오URL", "이미지URL", "가수", 180));

            final List<VotingSongResponse> expected =
                Stream.of(firstSong, secondSong, thirdSong, fourthSong, fifthSong)
                    .map(VotingSongResponse::from)
                    .toList();

            // when
            final List<VotingSongResponse> votingSongs = votingSongService.findAll();

            // then
            assertThat(votingSongs).usingRecursiveComparison()
                .isEqualTo(expected);
        }

        @DisplayName("파트 수집 중인 노래가 없으면 빈 목록을 반환한다.")
        @Test
        void returnEmptyList() {
            // given
            // when
            final List<VotingSongResponse> votingSongs = votingSongService.findAll();

            // then
            assertThat(votingSongs).isEmpty();
        }
    }
}
