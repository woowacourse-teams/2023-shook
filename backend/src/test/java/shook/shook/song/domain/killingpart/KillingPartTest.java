package shook.shook.song.domain.killingpart;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import shook.shook.part.domain.PartLength;
import shook.shook.song.domain.Song;
import shook.shook.song.exception.killingpart.KillingPartCommentException;

class KillingPartTest {

    private final Song song = new Song("제목", "비디오URL", "이미지URL", "가수", 30);

    @DisplayName("Id가 같은 파트는 동등성 비교에 참을 반환한다.")
    @Test
    void equals_true() {
        //given
        final KillingPart firstPart = KillingPart.saved(1L, 4, PartLength.SHORT, song);
        final KillingPart secondPart = KillingPart.saved(1L, 14, PartLength.SHORT, song);

        //when
        final boolean equals = firstPart.equals(secondPart);

        //then
        assertThat(equals).isTrue();
    }

    @DisplayName("Id가 없는 경우 동등성 비교에 항상 거짓을 반환한다.")
    @Nested
    class Equals {

        @DisplayName("비교하는 객체 중 하나만 없을 때")
        @Test
        void equals_false_nullId() {
            //given
            final KillingPart firstPart = KillingPart.saved(null, 4, PartLength.SHORT, song);
            final KillingPart secondPart = KillingPart.saved(1L, 14, PartLength.SHORT, song);

            //when
            final boolean equals = firstPart.equals(secondPart);

            //then
            assertThat(equals).isFalse();
        }

        @DisplayName("비교하는 객체 둘다 없을 때")
        @Test
        void equals_false_bothNullId() {
            //given
            final KillingPart firstPart = KillingPart.saved(null, 4, PartLength.SHORT, song);
            final KillingPart secondPart = KillingPart.saved(null, 14, PartLength.SHORT, song);

            //when
            final boolean equals = firstPart.equals(secondPart);

            //then
            assertThat(equals).isFalse();
        }
    }

    @DisplayName("파트의 시작과 끝을 담은 URL Path parameter를 반환한다.")
    @Test
    void exist() {
        //given
        final Song song = new Song("제목", "비디오URL", "이미지URL", "가수", 30);
        final KillingPart killingPart = KillingPart.saved(1L, 5, PartLength.SHORT, song);

        //when
        final String startAndEndUrlPathParameter = killingPart.getStartAndEndUrlPathParameter();

        //then
        final int startSecond = killingPart.getStartSecond();
        final int endSecond = killingPart.getEndSecond();
        final String playDuration = String.format("?start=%d&end=%d", startSecond, endSecond);
        assertThat(startAndEndUrlPathParameter).isEqualTo(playDuration);
    }

    @DisplayName("파트에 댓글을 추가한다.")
    @Nested
    class AddComment {

        @DisplayName("성공적으로 추가한 경우")
        @Test
        void success() {
            //given
            final Song song = new Song("제목", "비디오URL", "이미지URL", "가수", 30);
            final KillingPart part = KillingPart.saved(1L, 5, PartLength.SHORT, song);

            //when
            part.addComment(KillingPartComment.saved(1L, part, "댓글 내용"));

            //then
            assertThat(part.getComments()).hasSize(1);
        }

        @DisplayName("다른 파트의 댓글을 추가한 경우")
        @Test
        void belongToOtherPart() {
            //given
            final Song song = new Song("제목", "비디오URL", "이미지URL", "가수", 30);
            final KillingPart firstPart = KillingPart.saved(1L, 5, PartLength.SHORT, song);
            final KillingPart secondPart = KillingPart.saved(2L, 5, PartLength.SHORT, song);

            //when
            //then
            assertThatThrownBy(
                () -> firstPart.addComment(KillingPartComment.saved(2L, secondPart, "댓글 내용")))
                .isInstanceOf(KillingPartCommentException.CommentForOtherPartException.class);
        }
    }
}
