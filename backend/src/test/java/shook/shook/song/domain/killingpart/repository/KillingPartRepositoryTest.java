package shook.shook.song.domain.killingpart.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import shook.shook.part.domain.PartLength;
import shook.shook.song.domain.Genre;
import shook.shook.song.domain.KillingParts;
import shook.shook.song.domain.Song;
import shook.shook.song.domain.killingpart.KillingPart;
import shook.shook.song.domain.repository.SongRepository;
import shook.shook.support.UsingJpaTest;

class KillingPartRepositoryTest extends UsingJpaTest {

    private static Song SAVED_SONG;
    private static KillingPart FIRST_KILLING_PART;
    private static KillingPart SECOND_KILLING_PART;
    private static KillingPart THIRD_KILLING_PART;
    private static KillingParts KILLING_PARTS;

    @Autowired
    private KillingPartRepository killingPartRepository;

    @Autowired
    private SongRepository songRepository;

    @BeforeEach
    void setUp() {
        FIRST_KILLING_PART = KillingPart.forSave(0, PartLength.SHORT);
        SECOND_KILLING_PART = KillingPart.forSave(10, PartLength.SHORT);
        THIRD_KILLING_PART = KillingPart.forSave(14, PartLength.STANDARD);
        KILLING_PARTS = new KillingParts(
            List.of(
                FIRST_KILLING_PART,
                SECOND_KILLING_PART,
                THIRD_KILLING_PART
            )
        );
        SAVED_SONG = songRepository.save(
            new Song("제목", "비디오ID는 11글자", "이미지URL", "가수", 30, Genre.findByName("DANCE"), KILLING_PARTS));
    }

    @DisplayName("KillingPart 를 모두 저장한다.")
    @Test
    void save() {
        //given
        //when
        final List<KillingPart> savedKillingParts = killingPartRepository.saveAll(
            KILLING_PARTS.getKillingParts());

        //then
        assertThat(savedKillingParts).hasSize(3);
        assertThat(savedKillingParts).containsExactly(
            FIRST_KILLING_PART,
            SECOND_KILLING_PART,
            THIRD_KILLING_PART
        );
        assertThat(savedKillingParts).usingRecursiveComparison()
            .comparingOnlyFields("id")
            .isNotNull();
    }

    @DisplayName("KillingPart 를 저장할 때의 시간 정보로 createAt이 자동 생성된다.")
    @Test
    void createdAt() {
        //given
        //when
        final LocalDateTime prev = LocalDateTime.now().truncatedTo(ChronoUnit.MICROS);
        final KillingPart saved = killingPartRepository.save(FIRST_KILLING_PART);
        final LocalDateTime after = LocalDateTime.now().truncatedTo(ChronoUnit.MICROS);

        //then
        assertThat(FIRST_KILLING_PART).isSameAs(saved);
        assertThat(FIRST_KILLING_PART.getCreatedAt()).isBetween(prev, after);
    }

    @DisplayName("노래에 해당하는 모든 킬링파트를 조회한다.")
    @Test
    void findAllBySong() {
        //given
        killingPartRepository.saveAll(KILLING_PARTS.getKillingParts());

        //when
        saveAndClearEntityManager();
        final List<KillingPart> allBySong = killingPartRepository.findAllBySong(SAVED_SONG);

        //then
        assertThat(allBySong).containsAll(
            List.of(FIRST_KILLING_PART, SECOND_KILLING_PART, THIRD_KILLING_PART)
        );
    }
}
