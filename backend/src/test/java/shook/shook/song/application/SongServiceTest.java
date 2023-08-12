package shook.shook.song.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import shook.shook.song.application.dto.KillingPartRegisterRequest;
import shook.shook.song.application.dto.SongWithKillingPartsRegisterRequest;
import shook.shook.song.domain.Song;
import shook.shook.song.domain.killingpart.repository.KillingPartRepository;
import shook.shook.song.domain.repository.SongRepository;
import shook.shook.support.UsingJpaTest;

class SongServiceTest extends UsingJpaTest {

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private KillingPartRepository killingPartRepository;

    private SongService songService;

    @BeforeEach
    void setUp() {
        songService = new SongService(songRepository, killingPartRepository);
    }

    @DisplayName("Song 을 저장할 때, Song 과 KillingParts 가 함께 저장된다.")
    @Test
    void register() {
        // given
        final SongWithKillingPartsRegisterRequest request = new SongWithKillingPartsRegisterRequest(
            "title", "videoUrl", "imageUrl", "singer", 300,
            List.of(
                new KillingPartRegisterRequest(10, 5),
                new KillingPartRegisterRequest(15, 10),
                new KillingPartRegisterRequest(0, 10)
            )
        );

        // when
        long savedSongId = songService.register(request);
        saveAndClearEntityManager();

        //then
        final Song foundSong = songRepository.findById(savedSongId).get();

        assertAll(
            () -> assertThat(foundSong).isNotNull(),
            () -> assertThat(foundSong.getTitle()).isEqualTo("title"),
            () -> assertThat(foundSong.getVideoUrl()).isEqualTo("videoUrl"),
            () -> assertThat(foundSong.getAlbumCoverUrl()).isEqualTo("imageUrl"),
            () -> assertThat(foundSong.getSinger()).isEqualTo("singer"),
            () -> assertThat(foundSong.getCreatedAt()).isNotNull(),
            () -> assertThat(foundSong.getKillingParts()).hasSize(3)
        );
    }
}
