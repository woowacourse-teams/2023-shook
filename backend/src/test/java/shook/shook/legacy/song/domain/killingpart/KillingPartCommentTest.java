package shook.shook.legacy.song.domain.killingpart;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shook.shook.legacy.member.domain.Member;
import shook.shook.legacy.song.domain.Song;

class KillingPartCommentTest {

    private static final Song EMPTY_SONG = null;
    private static final Member MEMBER = new Member("email@naver.com", "nickname");
    private static final KillingPart FIRST_KILLING_PART =
        KillingPart.saved(1L, 4, 5, EMPTY_SONG);
    private static final KillingPart SECOND_KILLING_PART =
        KillingPart.saved(2L, 10, 5, EMPTY_SONG);

    @DisplayName("새로운 댓글을 생성한다.")
    @Test
    void create_new() {
        //given
        //when
        //then
        assertDoesNotThrow(() -> KillingPartComment.forSave(FIRST_KILLING_PART, "댓글 내용", MEMBER));
    }

    @DisplayName("이미 작성된 댓글을 생성한다.")
    @Test
    void create_exist() {
        //given
        //when
        //then
        assertDoesNotThrow(() -> KillingPartComment.saved(1L, FIRST_KILLING_PART, "댓글 내용", MEMBER));
    }

    @DisplayName("댓글의 내용을 반환한다.")
    @Test
    void getContent() {
        //given
        final KillingPartComment killingPartComment = KillingPartComment.forSave(FIRST_KILLING_PART,
                                                                                 "댓글 내용", MEMBER);

        //when
        final String content = killingPartComment.getContent();

        //then
        assertThat(content).isEqualTo("댓글 내용");
    }

    @DisplayName("댓글이 다른 킬링파트에 포함 되어있는지 여부를 반환한다. ( 같은 킬링파트일 때 false 를 반환한다. )")
    @Test
    void isBelongToOtherPart_samePart() {
        //given
        final KillingPartComment killingPartComment = KillingPartComment.forSave(FIRST_KILLING_PART,
                                                                                 "댓글 내용", MEMBER);

        //when
        final boolean isBelongTo = killingPartComment.isBelongToOtherKillingPart(
            FIRST_KILLING_PART);

        //then
        assertThat(isBelongTo).isFalse();
    }

    @DisplayName("댓글이 다른 킬링파트에 포함 되어있는지 여부를 반환한다. ( 다른 킬링파트일 때 true 를 반환한다. )")
    @Test
    void isBelongToOtherPart_otherPart() {
        //given
        final KillingPartComment partComment = KillingPartComment.forSave(FIRST_KILLING_PART,
                                                                          "댓글 내용", MEMBER);

        //when
        final boolean isBelongTo = partComment.isBelongToOtherKillingPart(SECOND_KILLING_PART);

        //then
        assertThat(isBelongTo).isTrue();
    }
}
