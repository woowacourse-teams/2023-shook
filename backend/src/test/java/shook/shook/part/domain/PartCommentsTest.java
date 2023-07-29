package shook.shook.part.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shook.shook.part.exception.PartCommentException;
import shook.shook.song.domain.Song;

class PartCommentsTest {

    @DisplayName("파트에 댓글을 성공적으로 추가한 경우")
    @Test
    void addComment_success() {
        //given
        final Song song = new Song("제목", "비디오URL", "가수", 30);
        final Part part = Part.saved(1L, 5, PartLength.SHORT, song);

        final PartComments comments = new PartComments();

        //when
        comments.addComment(PartComment.saved(1L, part, "댓글 내용"));

        //then
        assertThat(comments.getComments()).hasSize(1);
    }

    @DisplayName("파트에 댓글이 이미 존재하는 댓글인 경우")
    @Test
    void addComment_exist() {
        //given
        final Song song = new Song("제목", "비디오URL", "가수", 30);
        final Part part = Part.saved(1L, 5, PartLength.SHORT, song);
        final PartComments partComments = new PartComments();

        //when
        partComments.addComment(PartComment.saved(1L, part, "댓글 내용"));

        //then
        assertThatThrownBy(() -> partComments.addComment(PartComment.saved(1L, part, "댓글 내용")))
            .isInstanceOf(PartCommentException.DuplicateCommentExistException.class);
    }

    @DisplayName("최신 순으로 정렬된 댓글을 반환한다.")
    @Test
    void getRepliesInRecentOrder() {
        //given
        final Song song = new Song("제목", "비디오URL", "가수", 30);
        final Part part = Part.saved(1L, 5, PartLength.SHORT, song);
        final PartComments comments = new PartComments();

        final PartComment early = PartComment.saved(1L, part, "댓글입니다.");
        final PartComment late = PartComment.saved(2L, part, "댓글이였습니다.");

        comments.addComment(late);
        comments.addComment(early);

        //when
        final List<PartComment> repliesInRecentOrder = comments.getCommentsInRecentOrder();

        //then
        assertThat(repliesInRecentOrder).usingRecursiveComparison().isEqualTo(List.of(late, early));
    }
}
