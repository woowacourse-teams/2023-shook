package shook.shook.song.domain.killingpart;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shook.shook.part.domain.PartLength;
import shook.shook.song.domain.Song;

class KillingPartCommentTest {

    private final Song song = new Song("제목", "비디오URL", "이미지URL", "가수", 30);
    private final KillingPart firstPart = KillingPart.saved(1L, 4, PartLength.SHORT, song);
    private final KillingPart secondPart = KillingPart.saved(2L, 4, PartLength.SHORT, song);

    @DisplayName("새로운 댓글을 생성한다.")
    @Test
    void create_new() {
        //given
        //when
        //then
        assertDoesNotThrow(() -> KillingPartComment.forSave(firstPart, "댓글 내용"));
    }

    @DisplayName("이미 작성된 댓글을 생성한다.")
    @Test
    void create_exist() {
        //given
        //when
        //then
        assertDoesNotThrow(() -> KillingPartComment.saved(1L, firstPart, "댓글 내용"));
    }

    @DisplayName("댓글의 내용을 반환한다.")
    @Test
    void getContent() {
        //given
        final KillingPartComment partComment = KillingPartComment.forSave(firstPart, "댓글 내용");

        //when
        final String content = partComment.getContent();

        //then
        assertThat(content).isEqualTo("댓글 내용");
    }

    @DisplayName("댓글이 다른 파트에 포함 되어있는지 여부를 반환한다. ( 같은 파트일 때 false 를 반환한다. )")
    @Test
    void isBelongToOtherPart_samePart() {
        //given
        final KillingPartComment partComment = KillingPartComment.forSave(firstPart, "댓글 내용");

        //when
        final boolean isBelongTo = partComment.isBelongToOtherPart(firstPart);

        //then
        assertThat(isBelongTo).isFalse();
    }

    @DisplayName("댓글이 다른 파트에 포함 되어있는지 여부를 반환한다. ( 다른 파트일 때 true 를 반환한다. )")
    @Test
    void isBelongToOtherPart_otherPart() {
        //given
        final KillingPartComment partComment = KillingPartComment.forSave(firstPart, "댓글 내용");

        //when
        final boolean isBelongTo = partComment.isBelongToOtherPart(secondPart);

        //then
        assertThat(isBelongTo).isTrue();
    }
}
