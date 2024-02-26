package shook.shook.song.application;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import shook.shook.improved.artist.domain.Artist;
import shook.shook.improved.artist.domain.repository.ArtistRepository;
import shook.shook.improved.song.application.SongService;
import shook.shook.improved.song.application.dto.SongRegisterRequest;
import shook.shook.improved.song.domain.repository.SongRepository;
import shook.shook.improved.song.song_artist.domain.SongArtist;
import shook.shook.improved.song.song_artist.domain.repository.SongArtistRepository;
import shook.shook.support.UsingJpaTest;

class SongServiceTest extends UsingJpaTest {

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private SongArtistRepository songArtistRepository;

    private SongService songService;

    @BeforeEach
    void setUp() {
        songService = new SongService(songRepository, artistRepository, songArtistRepository);
    }

    @DisplayName("새로운 노래를 등록한다.")
    @Test
    void register_song() {
        // given
        final Artist artist1 = new Artist("아티스트1 프로필", "아티스트1");
        final Artist artist2 = new Artist("아티스트1 프로필", "아티스트1");
        final List<Long> savedArtistIds = artistRepository.saveAll(List.of(artist1, artist2)).stream()
            .map(Artist::getId)
            .toList();

        final SongRegisterRequest request = new SongRegisterRequest("노래제목", "노래비디오", "노래 앨범 이미지",
                                                                                savedArtistIds, 180, "팝");

        // when
        final Long result = songService.register(request);

        // then
        final List<SongArtist> bySongId = songArtistRepository.findBySongId(result);

        Assertions.assertThat(bySongId).hasSize(2);
    }
}
