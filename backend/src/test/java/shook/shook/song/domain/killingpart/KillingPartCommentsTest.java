package shook.shook.song.domain.killingpart;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shook.shook.member.domain.Member;
import shook.shook.part.domain.PartLength;
import shook.shook.song.domain.Song;
import shook.shook.song.exception.killingpart.KillingPartCommentException;

class KillingPartCommentsTest {

    private static final Song EMPTY_SONG = null;
    private static final Member MEMBER = new Member("email@naver.com", "nickname");

    @DisplayName("새 댓글이 킬링파트에 추가된다.")
    @Test
    void addComment_success() {
        //given
        final KillingPart killingPart = KillingPart.saved(1L, 5, PartLength.SHORT, EMPTY_SONG);

        final KillingPartComments comments = new KillingPartComments();

        //when
        comments.addComment(KillingPartComment.saved(1L, killingPart, "댓글 내용", MEMBER));

        //then
        assertThat(comments.getComments()).hasSize(1);
    }

    @DisplayName("킬링파트에 댓글이 이미 존재하는 댓글인 경우 예외가 발생한다.")
    @Test
    void addComment_exist_fail() {
        //given
        final KillingPart killingPart = KillingPart.saved(1L, 5, PartLength.SHORT, EMPTY_SONG);
        final KillingPartComments partComments = new KillingPartComments();

        //when
        partComments.addComment(KillingPartComment.saved(1L, killingPart, "댓글 내용", MEMBER));

        //then
        assertThatThrownBy(
            () -> partComments.addComment(
                KillingPartComment.saved(1L, killingPart, "댓글 내용", MEMBER))
        ).isInstanceOf(KillingPartCommentException.DuplicateCommentExistException.class);
    }

    @DisplayName("최신 순으로 정렬된 댓글을 반환한다.")
    @Test
    void getCommentsInRecentOrder() {
        //given
        final KillingPart killingPart = KillingPart.saved(1L, 5, PartLength.SHORT, EMPTY_SONG);
        final KillingPartComments comments = new KillingPartComments();

        final KillingPartComment early = KillingPartComment.saved(1L, killingPart, "댓글입니다.",
            MEMBER);
        final KillingPartComment late = KillingPartComment.saved(2L, killingPart, "댓글이였습니다.",
            MEMBER);

        comments.addComment(late);
        comments.addComment(early);

        //when
        final List<KillingPartComment> repliesInRecentOrder = comments.getCommentsInRecentOrder();

        //then
        assertThat(repliesInRecentOrder).usingRecursiveComparison()
            .isEqualTo(List.of(late, early));
    }
}
