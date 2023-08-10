package shook.shook.song.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import shook.shook.song.application.dto.voting_song.VotingSongRegisterRequest;
import shook.shook.song.domain.SongTitle;
import shook.shook.song.domain.repository.VotingSongRepository;
import shook.shook.song.domain.voting_song.VotingSong;
import shook.shook.support.UsingJpaTest;

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
}
