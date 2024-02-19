package shook.shook.legacy.song.application.killingpart;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import shook.shook.legacy.member.domain.Member;
import shook.shook.legacy.member.domain.repository.MemberRepository;
import shook.shook.legacy.song.application.killingpart.dto.KillingPartCommentRegisterRequest;
import shook.shook.legacy.song.application.killingpart.dto.KillingPartCommentResponse;
import shook.shook.legacy.song.domain.killingpart.KillingPart;
import shook.shook.legacy.song.domain.killingpart.KillingPartComment;
import shook.shook.legacy.song.domain.killingpart.repository.KillingPartCommentRepository;
import shook.shook.legacy.song.domain.killingpart.repository.KillingPartRepository;
import shook.shook.song.exception.legacy_killingpart.KillingPartException;
import shook.shook.legacy.support.UsingJpaTest;

@Sql("classpath:/killingpart/initialize_killing_part_song.sql")
class KillingPartCommentServiceTest extends UsingJpaTest {

    private static final long UNSAVED_PART_ID = Long.MAX_VALUE;
    private static final long MEMBER_ID = 1L;
    private static Member MEMBER;
    private static KillingPart SAVED_PART;

    @Autowired
    private KillingPartRepository killingPartRepository;

    @Autowired
    private KillingPartCommentRepository killingPartCommentRepository;

    @Autowired
    private MemberRepository memberRepository;

    private KillingPartCommentService killingPartCommentService;

    @BeforeEach
    void setUp() {
        SAVED_PART = killingPartRepository.findById(1L).get();
        MEMBER = memberRepository.findById(MEMBER_ID).get();
        killingPartCommentService = new KillingPartCommentService(killingPartRepository,
                                                                  killingPartCommentRepository, memberRepository);
    }

    @DisplayName("킬링파트에 댓글을 등록한다.")
    @Test
    void register() {
        //given
        final KillingPartCommentRegisterRequest request = new KillingPartCommentRegisterRequest("댓글 내용");

        //when
        killingPartCommentService.register(SAVED_PART.getId(), request, MEMBER_ID);
        saveAndClearEntityManager();

        //then
        final KillingPart part = killingPartRepository.findById(SAVED_PART.getId()).get();
        assertThat(part.getComments()).hasSize(1);
        assertThat(part.getComments().get(0).getContent()).isEqualTo("댓글 내용");
    }

    @DisplayName("킬링파트에 댓글을 등록할 때, 존재하지 않는 킬링파트라면 예외가 발생한다.")
    @Test
    void register_noKillingPart_fail() {
        // given
        final KillingPartCommentRegisterRequest request = new KillingPartCommentRegisterRequest(
            "댓글 내용");

        // when, then
        assertThatThrownBy(
            () -> killingPartCommentService.register(UNSAVED_PART_ID, request, MEMBER_ID))
            .isInstanceOf(KillingPartException.PartNotExistException.class);
    }

    @DisplayName("킬링파트의 모든 댓글을 조회하여 반환한다.")
    @Test
    void findKillingPartComments() {
        //given
        final KillingPartComment early = killingPartCommentRepository.save(
            KillingPartComment.forSave(SAVED_PART, "1", MEMBER));
        final KillingPartComment late = killingPartCommentRepository.save(
            KillingPartComment.forSave(SAVED_PART, "2", MEMBER));

        //when
        saveAndClearEntityManager();
        final List<KillingPartCommentResponse> partReplies = killingPartCommentService.findKillingPartComments(
            SAVED_PART.getId());

        final List<KillingPartCommentResponse> expected = KillingPartCommentResponse.ofComments(
            List.of(late, early));

        //then
        assertThat(partReplies).usingRecursiveComparison().isEqualTo(expected);
    }

    @DisplayName("킬링파트의 모든 댓글을 조회할 때, 존재하지 않는 킬링파트라면 예외가 발생한다.")
    @Test
    void findKillingPartComments_noKillingPart_fail() {
        // given
        // when, then
        assertThatThrownBy(() -> killingPartCommentService.findKillingPartComments(UNSAVED_PART_ID))
            .isInstanceOf(KillingPartException.PartNotExistException.class);
    }
}
