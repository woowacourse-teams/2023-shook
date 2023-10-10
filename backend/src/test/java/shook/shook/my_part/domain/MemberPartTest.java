package shook.shook.my_part.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shook.shook.member.domain.Member;
import shook.shook.my_part.exception.MemberPartException;
import shook.shook.song.domain.Genre;
import shook.shook.song.domain.KillingParts;
import shook.shook.song.domain.Song;
import shook.shook.song.domain.killingpart.KillingPart;

class MemberPartTest {

    private static Song SONG;
    private static Member MEMBER;

    @BeforeEach
    void setUp() {
        final KillingParts killingParts = new KillingParts(
            List.of(
                KillingPart.forSave(1, 10),
                KillingPart.forSave(1, 10),
                KillingPart.forSave(1, 10)
            )
        );

        SONG = new Song("title", "12345678901", "albumCover", "singer", 300, Genre.DANCE, killingParts);
        MEMBER = new Member("shook@email.com", "shook");
    }

    @DisplayName("멤버 파트의 시작 시간이 음수이면 예외가 발생한다")
    @Test
    void create_failNegativeStartSecond() {
        // given
        final int negativeStartSecond = -1;

        // when
        // then
        assertThatThrownBy(() -> MemberPart.forSave(negativeStartSecond, 10, SONG, MEMBER))
            .isInstanceOf(MemberPartException.MemberPartStartSecondNegativeException.class);
    }

    @DisplayName("멤버 파트의 끝 초가 노래 길이보다 길면 예외가 발생한다")
    @Test
    void create_failEndSecondOverSongLength() {
        // given
        final int longLength = 15;

        // when
        // then
        assertThatThrownBy(() -> MemberPart.forSave(340, longLength, SONG, MEMBER))
            .isInstanceOf(MemberPartException.MemberPartEndOverSongLengthException.class);
    }
}
