package shook.shook.song.domain;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shook.shook.member.domain.Member;
import shook.shook.part.domain.PartLength;
import shook.shook.song.domain.killingpart.KillingPart;
import shook.shook.song.domain.killingpart.KillingPartLike;
import shook.shook.support.UsingJpaTest;

class InMemorySongsTest extends UsingJpaTest {

    // TODO: 2023/09/16 CachedSong테스트
    @DisplayName("CachedSong 을 1.좋아요 순, 2. id 순으로 정렬된 노래로 초기화한다.")
    @Test
    void recreate() {
        final Member member = new Member("email@naver.com", "nickname");
        final KillingPart song1KillingPart1 = KillingPart.forSave(10, PartLength.STANDARD);
        song1KillingPart1.like(new KillingPartLike(song1KillingPart1, member));
        final KillingPart song1KillingPart2 = KillingPart.forSave(15, PartLength.STANDARD);
        song1KillingPart1.like(new KillingPartLike(song1KillingPart2, member));
        final KillingPart song1KillingPart3 = KillingPart.forSave(20, PartLength.STANDARD);
        song1KillingPart1.like(new KillingPartLike(song1KillingPart3, member));
        final KillingParts song1KillingParts = new KillingParts(List.of(song1KillingPart1, song1KillingPart2, song1KillingPart3));
        final Song song1 = new Song("노래1", "비디오1", "앨범자캣1", "가수1", 100,
            song1KillingParts);


    }

    @Test
    void getTop100Songs() {
    }


    @Test
    void getSongById() {
    }

    @Test
    void getPrevLikedSongs() {
    }

    @Test
    void getNextLikedSongs() {
    }
}
