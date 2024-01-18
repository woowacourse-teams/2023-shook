package shook.shook.legacy.song.domain.killingpart.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import shook.shook.legacy.member.domain.Member;
import shook.shook.legacy.member.domain.repository.MemberRepository;
import shook.shook.legacy.song.domain.killingpart.KillingPart;
import shook.shook.legacy.song.domain.killingpart.KillingPartComment;
import shook.shook.legacy.support.UsingJpaTest;

@Sql("classpath:/killingpart/initialize_killing_part_song.sql")
class KillingPartCommentRepositoryTest extends UsingJpaTest {

    private static KillingPart SAVED_KILLING_PART;
    private static Member MEMBER;

    @Autowired
    private KillingPartRepository killingPartRepository;

    @Autowired
    private KillingPartCommentRepository killingPartCommentRepository;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        SAVED_KILLING_PART = killingPartRepository.findById(1L).get();
        MEMBER = memberRepository.findById(1L).get();
    }

    @DisplayName("KillingPartComment 를 저장한다.")
    @Test
    void save() {
        //given
        final KillingPartComment partComment = KillingPartComment.forSave
            (SAVED_KILLING_PART, "댓글 내용", MEMBER);

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
                                                                          "댓글 내용", MEMBER);

        //when
        final LocalDateTime prev = LocalDateTime.now().truncatedTo(ChronoUnit.MICROS);
        final KillingPartComment savedPartComment = killingPartCommentRepository.save(partComment);
        final LocalDateTime after = LocalDateTime.now().truncatedTo(ChronoUnit.MICROS);

        //then
        assertThat(savedPartComment).isSameAs(partComment);
        assertThat(partComment.getCreatedAt()).isBetween(prev, after);
    }

    @DisplayName("멤버가 작성한 모든 파트 댓글을 삭제한다.")
    @Test
    void deleteAllByMember() {
        //given
        killingPartRepository.deleteAll();
        final KillingPartComment partComment1 = KillingPartComment.forSave(SAVED_KILLING_PART,
                                                                           "댓글 내용", MEMBER);
        final KillingPartComment partComment2 = KillingPartComment.forSave(SAVED_KILLING_PART,
                                                                           "댓글 내용", MEMBER);
        killingPartCommentRepository.save(partComment1);
        killingPartCommentRepository.save(partComment2);

        //when
        killingPartCommentRepository.deleteAllByMember(MEMBER);

        //then
        final List<KillingPart> all = killingPartRepository.findAll();
        assertThat(all).isEmpty();
    }
}
