package shook.shook.song.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shook.shook.song.domain.killingpart.KillingPart;
import shook.shook.song.exception.killingpart.KillingPartsException;

class SongTest {

    @DisplayName("Song 이 생성될 때, KillingParts 가 null 이라면 예외가 발생한다.")
    @Test
    void songCreate_nullKillingParts_fail() {
        // given
        // when, then
        assertThatThrownBy(
            () -> new Song(
                "title",
                "videoId",
                "imageUrl",
                new Artist("image", "name"),
                300,
                Genre.from("댄스"),
                null
            )
        ).isInstanceOf(KillingPartsException.EmptyKillingPartsException.class);
    }

    @DisplayName("Song 의 KillingPart 시작 시간, 종료 시간이 지정된 재생 가능한 URL 을 반환한다.")
    @Test
    void getPartVideoUrl() {
        // given
        final KillingPart killingPart1 = KillingPart.forSave(10, 10);
        final KillingPart killingPart2 = KillingPart.forSave(20, 10);
        final KillingPart killingPart3 = KillingPart.forSave(30, 10);
        final KillingParts killingParts = new KillingParts(
            List.of(killingPart1, killingPart2, killingPart3)
        );

        final Artist artist = new Artist("image", "name");
        final Song song = new Song(
            "title",
            "3rUPND6FG8A",
            "image_url",
            artist,
            230,
            Genre.from("댄스"),
            killingParts
        );

        // when
        final String killingPart1VideoUrl = song.getPartVideoUrl(killingPart1);

        // then
        assertThat(killingPart1VideoUrl).isEqualTo("https://youtu.be/3rUPND6FG8A?start=10&end=20");
    }
}
