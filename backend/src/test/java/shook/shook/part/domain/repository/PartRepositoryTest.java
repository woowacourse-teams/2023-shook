package shook.shook.part.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import shook.shook.part.domain.Part;
import shook.shook.part.domain.PartLength;
import shook.shook.song.domain.Song;
import shook.shook.song.domain.repository.SongRepository;
import shook.shook.support.UsingJpaTest;

class PartRepositoryTest extends UsingJpaTest {

    private static Song SAVED_SONG;

    @Autowired
    private PartRepository partRepository;

    @Autowired
    private SongRepository songRepository;

    @BeforeEach
    void setUp() {
        SAVED_SONG = songRepository.save(new Song("제목", "비디오URL", "이미지URL", "가수", 30));
    }

    @DisplayName("Part 을 저장한다.")
    @Test
    void save() {
        //given
        final Part part = Part.forSave(14, PartLength.SHORT, SAVED_SONG);

        //when
        final Part save = partRepository.save(part);

        //then
        assertThat(part).isSameAs(save);
        assertThat(part.getId()).isNotNull();
    }

    @DisplayName("Part 을 저장할 때의 시간 정보로 createAt이 자동 생성된다.")
    @Test
    void createdAt() {
        //given
        final Part part = Part.forSave(14, PartLength.SHORT, SAVED_SONG);

        //when
        final LocalDateTime prev = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
        final Part saved = partRepository.save(part);
        final LocalDateTime after = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);

        //then
        assertThat(part).isSameAs(saved);
        assertThat(part.getCreatedAt()).isBetween(prev, after);
    }

    @DisplayName("노래에 해당하는 모든 파트를 조회한다.")
    @Test
    void findAllBySong() {
        //given
        final Part firstPart = Part.forSave(1, PartLength.SHORT, SAVED_SONG);
        final Part secondPart = Part.forSave(5, PartLength.SHORT, SAVED_SONG);
        partRepository.save(firstPart);
        partRepository.save(secondPart);

        //when
        saveAndClearEntityManager();
        final List<Part> allBySong = partRepository.findAllBySong(SAVED_SONG);

        //then
        assertThat(allBySong).containsAll(List.of(firstPart, secondPart));
    }
}
