package shook.shook.song.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shook.shook.song.exception.killingpart.KillingPartsException;

class SongTest {

    @DisplayName("Song 이 생성될 때, KillingParts 가 null 이라면 예외가 발생한다.")
    @Test
    void songCreate_nullKillingParts_fail() {
        // given
        // when, then
        assertThatThrownBy(() -> new Song("title", "videoUrl", "imageUrl", "singer", 300, null))
            .isInstanceOf(KillingPartsException.EmptyKillingPartException.class);
    }
}
