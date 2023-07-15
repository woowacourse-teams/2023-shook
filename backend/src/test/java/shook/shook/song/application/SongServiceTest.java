package shook.shook.song.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import shook.shook.song.application.dto.SongRegisterRequest;
import shook.shook.song.application.dto.SongResponse;
import shook.shook.song.domain.Song;
import shook.shook.song.domain.SongTitle;
import shook.shook.song.domain.repository.SongRepository;
import shook.shook.song.exception.SongException;

@DataJpaTest
class SongServiceTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private SongRepository songRepository;

    private SongService songService;

    @BeforeEach
    void setUp() {
        songService = new SongService(songRepository);
    }

    void saveAndClearEntityManager() {
        entityManager.flush();
        entityManager.clear();
    }

    @DisplayName("노래를 등록한다.")
    @Test
    void register() {
        //given
        final SongRegisterRequest request = new SongRegisterRequest("노래제목", "이미지URL", "가수", 180);

        //when
        songService.register(request);
        saveAndClearEntityManager();

        //then
        final Song savedSong = songRepository.findByTitle(new SongTitle("노래제목")).get();
        Assertions.assertAll(
            () -> assertThat(savedSong.getId()).isNotNull(),
            () -> assertThat(savedSong.getCreatedAt()).isNotNull(),
            () -> assertThat(savedSong.getTitle()).isEqualTo("노래제목"),
            () -> assertThat(savedSong.getVideoUrl()).isEqualTo("이미지URL"),
            () -> assertThat(savedSong.getSinger()).isEqualTo("가수"),
            () -> assertThat(savedSong.getLength()).isEqualTo(180)
        );
    }

    @DisplayName("Id로 노래를 조회한다.(존재할 때)")
    @Test
    void findById_exist() {
        //given
        final Song savedSong = songRepository.save(new Song("노래제목", "이미지URL", "가수", 180));

        //when
        saveAndClearEntityManager();
        final SongResponse response = songService.findById(savedSong.getId());

        //then
        assertThat(response).usingRecursiveComparison().isEqualTo(SongResponse.from(savedSong));
    }

    @DisplayName("Id로 노래를 조회한다.(존재하지 않을 때)")
    @Test
    void findById_notExist() {
        //given
        //when
        //then
        assertThatThrownBy(() -> songService.findById(1L))
            .isInstanceOf(SongException.SongNotExistException.class);
    }

    @DisplayName("제목으로 노래를 조회한다.(존재할 때)")
    @Test
    void findByTitle_exist() {
        //given
        final Song savedSong = songRepository.save(new Song("노래제목", "이미지URL", "가수", 180));

        //when
        saveAndClearEntityManager();
        final SongResponse response = songService.findByTitle(savedSong.getTitle());

        //then
        assertThat(response).usingRecursiveComparison().isEqualTo(SongResponse.from(savedSong));
    }

    @DisplayName("제목으로 노래를 조회한다.(존재하지 않을 때)")
    @Test
    void findByTitle() {
        //given
        //when
        //then
        assertThatThrownBy(() -> songService.findByTitle("없는제목"))
            .isInstanceOf(SongException.SongNotExistException.class);
    }
}
