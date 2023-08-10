package shook.shook.song.domain.killingpart;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shook.shook.part.domain.PartLength;
import shook.shook.song.domain.KillingParts;
import shook.shook.song.domain.Song;
import shook.shook.song.exception.killingpart.KillingPartsException;

class KillingPartsTest {

    private static final Song SONG = new Song("제목", "url", "image", "가수", 500);
    private static final KillingPart FIRST_PART = KillingPart.forSave(0, PartLength.SHORT, SONG);
    private static final KillingPart SECOND_PART = KillingPart.forSave(0, PartLength.SHORT, SONG);
    private static final KillingPart THIRD_PART = KillingPart.forSave(0, PartLength.SHORT, SONG);

    @DisplayName("한 노래의 킬링파트는 총 3개로 구성된다.")
    @Test
    void createKillingPart_success() {
        // given
        final List<KillingPart> killingPartsToSave = List.of(FIRST_PART, SECOND_PART, THIRD_PART);

        // when, then
        assertDoesNotThrow(() -> new KillingParts(killingPartsToSave));
    }

    @DisplayName("킬링파트가 3개를 초과하면 예외가 발생한다.")
    @Test
    void createKillingPart_fail() {
        // given
        final KillingPart fourthPart = KillingPart.forSave(0, PartLength.SHORT, SONG);
        final List<KillingPart> killingPartsToSave = List.of(FIRST_PART, SECOND_PART, THIRD_PART,
            fourthPart);

        // when, then
        assertThatThrownBy(() -> new KillingParts(killingPartsToSave))
            .isInstanceOf(KillingPartsException.OverSizeLimitException.class);
    }
}
