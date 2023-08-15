package shook.shook.song.domain.killingpart.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import shook.shook.song.domain.killingpart.KillingPart;
import shook.shook.song.domain.killingpart.KillingPartComment;
import shook.shook.support.UsingJpaTest;

@Sql("classpath:/killingpart/initialize_killing_part_song.sql")
class KillingPartCommentRepositoryTest extends UsingJpaTest {

    private static KillingPart SAVED_KILLING_PART;

    @Autowired
    private KillingPartRepository killingPartRepository;

    @Autowired
    private KillingPartCommentRepository killingPartCommentRepository;

    @BeforeEach
    void setUp() {
        SAVED_KILLING_PART = killingPartRepository.findById(1L).get();
    }

    @DisplayName("KillingPartComment 를 저장한다.")
    @Test
    void save() {
        //given
        final KillingPartComment partComment = KillingPartComment.forSave
            (SAVED_KILLING_PART, "댓글 내용");

        //when
        final KillingPartComment savedPartComment = killingPartCommentRepository.save(partComment);

        //then
        assertThat(savedPartComment).isSameAs(partComment);
        assertThat(partComment.getId()).isNotNull();
    }

    @DisplayName("KillingPartComment 를 저장할 때의 시간 정보로 createAt이 자동 생성된다.")
    @Test
    void createdAt() {
        //given
        final KillingPartComment partComment = KillingPartComment.forSave(SAVED_KILLING_PART,
            "댓글 내용");

        //when
        final LocalDateTime prev = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
        final KillingPartComment savedPartComment = killingPartCommentRepository.save(partComment);
        final LocalDateTime after = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);

        //then
        assertThat(savedPartComment).isSameAs(partComment);
        assertThat(partComment.getCreatedAt()).isBetween(prev, after);
    }
}
