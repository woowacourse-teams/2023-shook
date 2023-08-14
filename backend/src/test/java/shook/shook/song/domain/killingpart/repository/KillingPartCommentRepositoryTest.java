package shook.shook.song.domain.killingpart.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import shook.shook.part.domain.PartLength;
import shook.shook.song.domain.Song;
import shook.shook.song.domain.killingpart.KillingPart;
import shook.shook.song.domain.killingpart.KillingPartComment;
import shook.shook.song.domain.repository.SongRepository;
import shook.shook.support.UsingJpaTest;

class KillingPartCommentRepositoryTest extends UsingJpaTest {

    private static KillingPart SAVED_PART;

    @Autowired
    private KillingPartRepository killingPartRepository;

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private KillingPartCommentRepository killingPartCommentRepository;

    @BeforeEach
    void setUp() {
        Song savedSong = songRepository.save(new Song("제목", "비디오URL", "이미지URL", "가수", 30));
        SAVED_PART = killingPartRepository.save(
            KillingPart.forSave(3, PartLength.SHORT, savedSong));
    }

    @DisplayName("KillingPartComment 를 저장한다.")
    @Test
    void save() {
        //given
        final KillingPartComment partComment = KillingPartComment.forSave(SAVED_PART, "댓글 내용");

        //when
        final KillingPartComment savedPartComment = killingPartCommentRepository.save(partComment);

        //then
        assertThat(savedPartComment).isSameAs(partComment);
        assertThat(partComment.getId()).isNotNull();
    }

    @DisplayName("KillingPart 을 저장할 때의 시간 정보로 createAt이 자동 생성된다.")
    @Test
    void createdAt() {
        //given
        final KillingPartComment partComment = KillingPartComment.forSave(SAVED_PART, "댓글 내용");

        //when
        final LocalDateTime prev = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
        final KillingPartComment savedPartComment = killingPartCommentRepository.save(partComment);
        final LocalDateTime after = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);

        //then
        assertThat(savedPartComment).isSameAs(partComment);
        assertThat(partComment.getCreatedAt()).isBetween(prev, after);
    }
}
