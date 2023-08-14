package shook.shook.song.domain.killingpart;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import shook.shook.member.domain.Member;
import shook.shook.part.domain.PartLength;
import shook.shook.song.exception.killingpart.KillingPartLikeException;

class KillingPartLikesTest {

    private static Member MEMBER;
    private static KillingPart KILLING_PART;
    private static KillingPartLikes LIKES;

    @BeforeEach
    void setUp() {
        MEMBER = new Member("email@naver.com", "nickname");
        KILLING_PART = KillingPart.forSave(10, PartLength.STANDARD);
        LIKES = new KillingPartLikes();
    }

    @DisplayName("킬링파트에 좋아요를 누른다.")
    @Nested
    class AddLike {

        @DisplayName("좋아요가 삭제된 상태였다면 좋아요 상태가 변경된다.")
        @Test
        void addLike_true_prevDeleted() {
            // given
            final KillingPartLike like = new KillingPartLike(KILLING_PART, MEMBER);
            LIKES.addLike(like);
            LIKES.deleteLike(like);

            // when
            LIKES.addLike(like);

            // then
            assertThat(LIKES.getSize()).isEqualTo(1);
        }

        @DisplayName("좋아요가 없는 상태였다면 새 좋아요가 등록된다.")
        @Test
        void addLike_true_prevNonExist() {
            // given
            // when, then
            final KillingPartLike like = new KillingPartLike(KILLING_PART, MEMBER);
            LIKES.addLike(like);

            // then
            assertThat(LIKES.getSize()).isEqualTo(1);
        }

        @DisplayName("좋아요가 존재하는 상태였다면 예외가 발생한다.")
        @Test
        void addLike_false_prevExist() {
            // given
            final KillingPartLike like = new KillingPartLike(KILLING_PART, MEMBER);
            LIKES.addLike(like);

            // when, then
            assertThatThrownBy(() -> LIKES.addLike(like))
                .isInstanceOf(KillingPartLikeException.LikeStatusNotUpdatableException.class);
        }
    }

    @DisplayName("킬링파트에 좋아요를 취소한다.")
    @Nested
    class DeleteLike {

        @DisplayName("좋아요가 존재하는 상태였다면 취소에 성공한다.")
        @Test
        void deleteLike_true_prevExist() {
            // given
            final KillingPartLike like = new KillingPartLike(KILLING_PART, MEMBER);
            LIKES.addLike(like);

            // when, then
            assertDoesNotThrow(() -> LIKES.deleteLike(like));
            assertThat(LIKES.getSize()).isEqualTo(0);


        }

        @DisplayName("좋아요가 없는 상태였다면 예외가 발생한다.")
        @Test
        void deleteLike_false_prevNonExist() {
            // given
            final KillingPartLike like = new KillingPartLike(KILLING_PART, MEMBER);

            // when, then
            assertThatThrownBy(() -> LIKES.deleteLike(like))
                .isInstanceOf(KillingPartLikeException.LikeStatusNotUpdatableException.class);
        }

        @DisplayName("좋아요가 삭제된 상태였다면 예외가 발생한다.")
        @Test
        void deleteLike_false_prevDeleted() {
            // given
            final KillingPartLike like = new KillingPartLike(KILLING_PART, MEMBER);
            LIKES.addLike(like);
            LIKES.deleteLike(like);

            // when, then
            assertThatThrownBy(() -> LIKES.deleteLike(like))
                .isInstanceOf(KillingPartLikeException.LikeStatusNotUpdatableException.class);
        }
    }

    @DisplayName("killingpart에 있는 좋아요는 삭제되지 않은 것만 조회한다.")
    @Test
    void getLikes() {
        // given
        final KillingPartLike like = new KillingPartLike(KILLING_PART, MEMBER);
        LIKES.addLike(like);

        final Member newMember = new Member("new@email.com", "new");
        final KillingPartLike newLike = new KillingPartLike(KILLING_PART, newMember);

        KILLING_PART.like(newLike);
        KILLING_PART.unlike(newLike);

        // when
        final List<KillingPartLike> likes = LIKES.getLikes();

        // then
        assertThat(LIKES.getSize()).isEqualTo(1);
        assertThat(likes).containsOnly(like);
    }
}
