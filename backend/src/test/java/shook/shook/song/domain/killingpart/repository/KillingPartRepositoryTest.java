package shook.shook.song.domain.killingpart.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import shook.shook.song.domain.Artist;
import shook.shook.song.domain.Genre;
import shook.shook.song.domain.KillingParts;
import shook.shook.song.domain.Song;
import shook.shook.song.domain.killingpart.KillingPart;
import shook.shook.song.domain.repository.ArtistRepository;
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
    private ArtistRepository artistRepository;

    @Autowired
    private SongRepository songRepository;

    @BeforeEach
    void setUp() {
        FIRST_KILLING_PART = KillingPart.forSave(0, 5);
        SECOND_KILLING_PART = KillingPart.forSave(10, 5);
        THIRD_KILLING_PART = KillingPart.forSave(14, 10);
        KILLING_PARTS = new KillingParts(
            List.of(
                FIRST_KILLING_PART,
                SECOND_KILLING_PART,
                THIRD_KILLING_PART
            )
        );
        final Artist artist = new Artist("image", "name");
        final Song song = new Song(
            "title",
            "3rUPND6FG8A",
            "image_url",
            artist,
            230,
            Genre.from("댄스"),
            KILLING_PARTS
        );
        artistRepository.save(song.getArtist());
        SAVED_SONG = songRepository.save(song);
    }

    @DisplayName("KillingPart 를 모두 저장한다.")
    @Test
    void save() {
        //given
        //when
        final List<KillingPart> savedKillingParts = killingPartRepository.saveAll(
            KILLING_PARTS.getKillingParts());

        //then
        assertThat(savedKillingParts).hasSize(3)
            .containsExactly(
                FIRST_KILLING_PART,
                SECOND_KILLING_PART,
                THIRD_KILLING_PART
            ).usingRecursiveComparison()
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

//    @DisplayName("한 킬링파트에 UPDATE + 1로 좋아요 수를 증가시킨다.")
//    @Test
//    void increaseLikeCount() {
//        // given
//        killingPartRepository.saveAll(KILLING_PARTS.getKillingParts());
//        final KillingPart killingPart = killingPartRepository.findById(FIRST_KILLING_PART.getId()).get();
//        final int initialLikeCount = killingPart.getLikeCount();
//
//        // when
//        saveAndClearEntityManager();
//        killingPartRepository.increaseLikeCount(killingPart.getId());
//
//        // then
//        final KillingPart foundKillingPart = killingPartRepository.findById(killingPart.getId()).get();
//
//        assertThat(foundKillingPart.getLikeCount()).isEqualTo(initialLikeCount + 1);
//    }
//
//    @DisplayName("한 킬링파트에 UPDATE - 1로 좋아요 수를 감소시킨다.")
//    @Test
//    void decreaseLikeCount() {
//        // given
//        killingPartRepository.saveAll(KILLING_PARTS.getKillingParts());
//        killingPartRepository.increaseLikeCount(FIRST_KILLING_PART.getId());
//        final KillingPart killingPart = killingPartRepository.findById(FIRST_KILLING_PART.getId()).get();
//        final int initialLikeCount = killingPart.getLikeCount();
//
//        // when
//        saveAndClearEntityManager();
//        killingPartRepository.decreaseLikeCount(killingPart.getId());
//
//        // then
//        final KillingPart foundKillingPart = killingPartRepository.findById(killingPart.getId()).get();
//
//        assertThat(foundKillingPart.getLikeCount()).isEqualTo(initialLikeCount - 1);
//    }
}
