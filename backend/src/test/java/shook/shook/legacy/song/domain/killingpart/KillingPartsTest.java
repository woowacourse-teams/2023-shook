package shook.shook.legacy.song.domain.killingpart;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import shook.shook.legacy.member.domain.Member;
import shook.shook.legacy.song.domain.KillingParts;
import shook.shook.legacy.song.domain.Song;
import shook.shook.legacy.song.exception.killingpart.KillingPartsException;

class KillingPartsTest {

    private static final KillingPart FIRST_PART = KillingPart.forSave(0, 5);
    private static final KillingPart SECOND_PART = KillingPart.forSave(5, 5);
    private static final KillingPart THIRD_PART = KillingPart.forSave(10, 5);
    private static final Song EMPTY_SONG = null;

    @DisplayName("한 노래의 킬링파트는 총 3개로 구성된다.")
    @Test
    void createKillingPart_success() {
        // given
        final List<KillingPart> killingPartsToSave = List.of(FIRST_PART, SECOND_PART, THIRD_PART);

        // when, then
        assertDoesNotThrow(() -> new KillingParts(killingPartsToSave));
    }

    @DisplayName("킬링파트가 3개가 아니라면 예외가 발생한다.")
    @ValueSource(ints = {2, 4})
    @ParameterizedTest
    void createKillingPart_fail(final int size) {
        // given
        final List<KillingPart> killingPartsToSave = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            killingPartsToSave.add(KillingPart.forSave(i, 10));
        }

        // when, then
        assertThatThrownBy(() -> new KillingParts(killingPartsToSave))
            .isInstanceOf(KillingPartsException.OutOfSizeException.class);
    }

    @DisplayName("킬링파트 리스트가 비어있거나 null 이라면 예외가 발생한다.")
    @Test
    void createKillingPart_empty_fail() {
        // given
        final List<KillingPart> killingParts = null;

        // when, then
        assertThatThrownBy(() -> new KillingParts(killingParts))
            .isInstanceOf(KillingPartsException.EmptyKillingPartsException.class);
    }

    @DisplayName("킬링파트가 3개 모두 등록되었다면 모두 등록되었음을 반환한다.")
    @Test
    void isFull() {
        // given
        final List<KillingPart> killingPartsToSave = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            killingPartsToSave.add(KillingPart.forSave(i, 10));
        }
        final KillingParts killingParts = new KillingParts(killingPartsToSave);

        // when
        final boolean result = killingParts.isFull();

        // then
        assertThat(result).isTrue();
    }

    @DisplayName("좋아요 순, 시작 시간 오름차순으로 정렬된 킬링파트를 조회한다.")
    @Test
    void getKillingPartsSortedByLikeCount() {
        // given
        final Member member = new Member("email@naver.com", "nickname");
        final KillingPart killingPart1 = KillingPart.saved(1L, 15, 5, EMPTY_SONG);
        final KillingPart killingPart2 = KillingPart.saved(2L, 10, 5, EMPTY_SONG);
        final KillingPart killingPart3 = KillingPart.saved(3L, 20, 5, EMPTY_SONG);

        killingPart1.like(new KillingPartLike(killingPart1, member));
        killingPart2.like(new KillingPartLike(killingPart2, member));

        final KillingParts killingParts = new KillingParts(List.of(killingPart1, killingPart3,
                                                                   killingPart2));

        // when
        final List<KillingPart> result = killingParts.getKillingPartsSortedByLikeCount();

        // then
        assertAll(
            () -> assertThat(result.get(0).getId()).isEqualTo(2L),
            () -> assertThat(result.get(1).getId()).isEqualTo(1L),
            () -> assertThat(result.get(2).getId()).isEqualTo(3L)
        );
    }

    @DisplayName("한 킬링파트 묶음의 총 좋아요 개수를 계산한다.")
    @Test
    void getTotalLikeCount() {
        // given
        final Member member = new Member("email@naver.com", "nickname");
        final KillingPart killingPart1 = KillingPart.saved(1L, 15, 5, EMPTY_SONG);
        final KillingPart killingPart2 = KillingPart.saved(2L, 10, 5, EMPTY_SONG);
        final KillingPart killingPart3 = KillingPart.saved(3L, 20, 5, EMPTY_SONG);

        killingPart1.like(new KillingPartLike(killingPart1, member));
        killingPart2.like(new KillingPartLike(killingPart2, member));

        final KillingParts killingParts = new KillingParts(List.of(killingPart1, killingPart3,
                                                                   killingPart2));

        // when
        final int totalLikeCount = killingParts.getKillingPartsTotalLikeCount();

        // then
        assertThat(totalLikeCount).isEqualTo(2);
    }
}
