package shook.shook.song.domain.killingpart;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shook.shook.part.domain.PartLength;
import shook.shook.song.domain.Song;
import shook.shook.song.exception.killingpart.KillingPartCommentException;

class KillingPartCommentsTest {

    @DisplayName("파트에 댓글을 성공적으로 추가한 경우")
    @Test
    void addComment_success() {
        //given
        final Song song = new Song("제목", "비디오URL", "이미지URL", "가수", 30);
        final KillingPart killingPart = KillingPart.saved(1L, 5, PartLength.SHORT, song);

        final KillingPartComments comments = new KillingPartComments();

        //when
        comments.addComment(KillingPartComment.saved(1L, killingPart, "댓글 내용"));

        //then
        assertThat(comments.getComments()).hasSize(1);
    }

    @DisplayName("파트에 댓글이 이미 존재하는 댓글인 경우")
    @Test
    void addComment_exist() {
        //given
        final Song song = new Song("제목", "비디오URL", "이미지URL", "가수", 30);
        final KillingPart killingPart = KillingPart.saved(1L, 5, PartLength.SHORT, song);
        final KillingPartComments partComments = new KillingPartComments();

        //when
        partComments.addComment(KillingPartComment.saved(1L, killingPart, "댓글 내용"));

        //then
        assertThatThrownBy(
            () -> partComments.addComment(KillingPartComment.saved(1L, killingPart, "댓글 내용"))
        )
            .isInstanceOf(KillingPartCommentException.DuplicateCommentExistException.class);
    }

    @DisplayName("최신 순으로 정렬된 댓글을 반환한다.")
    @Test
    void getRepliesInRecentOrder() {
        //given
        final Song song = new Song("제목", "비디오URL", "이미지URL", "가수", 30);
        final KillingPart part = KillingPart.saved(1L, 5, PartLength.SHORT, song);
        final KillingPartComments comments = new KillingPartComments();

        final KillingPartComment early = KillingPartComment.saved(1L, part, "댓글입니다.");
        final KillingPartComment late = KillingPartComment.saved(2L, part, "댓글이였습니다.");

        comments.addComment(late);
        comments.addComment(early);

        //when
        final List<KillingPartComment> repliesInRecentOrder = comments.getCommentsInRecentOrder();

        //then
        assertThat(repliesInRecentOrder).usingRecursiveComparison()
            .isEqualTo(List.of(late, early));
    }
}
