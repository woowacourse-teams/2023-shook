package shook.shook.song.domain.killingpart;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import shook.shook.part.domain.PartLength;
import shook.shook.song.domain.KillingParts;
import shook.shook.song.exception.killingpart.KillingPartsException;

class KillingPartsTest {

    private static final KillingPart FIRST_PART = KillingPart.forSave(0, PartLength.SHORT);
    private static final KillingPart SECOND_PART = KillingPart.forSave(5, PartLength.SHORT);
    private static final KillingPart THIRD_PART = KillingPart.forSave(10, PartLength.SHORT);

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
            killingPartsToSave.add(KillingPart.forSave(i, PartLength.STANDARD));
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
            .isInstanceOf(KillingPartsException.EmptyKillingPartException.class);
    }

    @DisplayName("킬링파트가 3개 모두 등록되었다면 모두 등록되었음을 반환한다.")
    @Test
    void isFull() {
        // given
        final List<KillingPart> killingPartsToSave = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            killingPartsToSave.add(KillingPart.forSave(i, PartLength.STANDARD));
        }
        final KillingParts killingParts = new KillingParts(killingPartsToSave);

        // when
        final boolean result = killingParts.isFull();

        // then
        assertThat(result).isTrue();
    }
}
