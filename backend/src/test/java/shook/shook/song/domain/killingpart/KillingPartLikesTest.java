package shook.shook.song.domain.killingpart;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import shook.shook.member.domain.Member;

class KillingPartLikesTest {

    private static Member MEMBER;
    private static KillingPart KILLING_PART;
    private static KillingPartLikes LIKES;

    @BeforeEach
    void setUp() {
        MEMBER = new Member("email@naver.com", "nickname");
        KILLING_PART = KillingPart.forSave(10, 10);
        LIKES = new KillingPartLikes();
    }

    @DisplayName("킬링파트에 좋아요를 누른다.")
    @Nested
    class AddLike {

        @DisplayName("좋아요가 삭제된 상태였다면 삭제가 취소되고 true가 반환된다.")
        @Test
        void addLike_true_prevDeleted() {
            // given
            final KillingPartLike like = new KillingPartLike(KILLING_PART, MEMBER);
            LIKES.addLike(like);
            LIKES.deleteLike(like);

            // when
            final boolean isSuccess = LIKES.addLike(like);

            // then
            assertThat(LIKES.getSize()).isEqualTo(1);
            assertThat(isSuccess).isTrue();
        }

        @DisplayName("좋아요가 없는 상태였다면 저장되고 true가 반환된다.")
        @Test
        void addLike_true_prevNonExist() {
            // given
            // when
            final KillingPartLike like = new KillingPartLike(KILLING_PART, MEMBER);
            final boolean isSuccess = LIKES.addLike(like);

            // then
            assertThat(LIKES.getSize()).isEqualTo(1);
            assertThat(isSuccess).isTrue();
        }

        @DisplayName("좋아요가 존재하는 상태였다면 false가 반환된다.")
        @Test
        void addLike_false_prevExist() {
            // given
            final KillingPartLike like = new KillingPartLike(KILLING_PART, MEMBER);
            LIKES.addLike(like);

            // when
            final boolean isSuccess = LIKES.addLike(like);

            // then
            assertThat(LIKES.getSize()).isEqualTo(1);
            assertThat(isSuccess).isFalse();
        }
    }

    @DisplayName("킬링파트에 좋아요를 취소한다.")
    @Nested
    class DeleteLike {

        @DisplayName("좋아요가 존재하는 상태였다면 삭제되고 true가 반환된다.")
        @Test
        void deleteLike_true_prevExist() {
            // given
            final KillingPartLike like = new KillingPartLike(KILLING_PART, MEMBER);
            LIKES.addLike(like);

            // when
            final boolean isSuccess = LIKES.deleteLike(like);

            // then
            assertThat(LIKES.getSize()).isEqualTo(0);
            assertThat(isSuccess).isTrue();
        }

        @DisplayName("좋아요가 없는 상태였다면 false가 반환된다.")
        @Test
        void deleteLike_false_prevNonExist() {
            // given
            final KillingPartLike like = new KillingPartLike(KILLING_PART, MEMBER);

            // when
            final boolean isSuccess = LIKES.deleteLike(like);

            // then
            assertThat(LIKES.getSize()).isEqualTo(0);
            assertThat(isSuccess).isFalse();
        }

        @DisplayName("좋아요가 삭제된 상태였다면 false가 반환된다.")
        @Test
        void deleteLike_false_prevDeleted() {
            // given
            final KillingPartLike like = new KillingPartLike(KILLING_PART, MEMBER);
            LIKES.addLike(like);
            LIKES.deleteLike(like);

            // when
            final boolean isSuccess = LIKES.deleteLike(like);

            // then
            assertThat(LIKES.getSize()).isEqualTo(0);
            assertThat(isSuccess).isFalse();
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
