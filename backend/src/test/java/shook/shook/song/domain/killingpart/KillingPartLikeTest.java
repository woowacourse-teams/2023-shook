package shook.shook.song.domain.killingpart;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shook.shook.member.domain.Member;
import shook.shook.song.domain.Song;

class KillingPartLikeTest {

    private static final Song EMPTY_SONG = null;
    private static final Member MEMBER = new Member("email@naver.com", "nickname");
    private static final KillingPart KILLING_PART = KillingPart.saved(1L, 10, 5,
                                                                      EMPTY_SONG);

    @DisplayName("isDeleted 상태를 반대로 변경할 수 있다.")
    @Test
    void updateDeletion() {
        // given
        final KillingPartLike killingPartLike = new KillingPartLike(KILLING_PART, MEMBER);

        // when
        killingPartLike.updateDeletion();

        // then
        assertThat(killingPartLike.isDeleted()).isFalse();
    }

    @DisplayName("해당 좋아요가 member 의 소유임을 확인할 수 있다.")
    @Test
    void isOwner() {
        // given
        final Member newMember = new Member("test@naver.com", "test");
        final KillingPartLike like = new KillingPartLike(KILLING_PART, MEMBER);

        // when
        final boolean isMemberOwner = like.isOwner(MEMBER);
        final boolean isNewMemberOwner = like.isOwner(newMember);

        // then
        assertThat(isMemberOwner).isTrue();
        assertThat(isNewMemberOwner).isFalse();
    }

    @DisplayName("해당 좋아요가 특정 KillingPart 의 소유가 아님을 확인할 수 있다.")
    @Test
    void doesNotBelongToKillingPart() {
        // given
        final KillingPartLike like = new KillingPartLike(KILLING_PART, MEMBER);

        // when
        final boolean doesNotBelongsToKillingPart = like.isBelongToOtherKillingPart(KILLING_PART);

        // then
        assertThat(doesNotBelongsToKillingPart).isFalse();
    }
}
