package shook.shook.voting_song.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shook.shook.member.domain.Member;
import shook.shook.part.domain.PartLength;
import shook.shook.part.exception.PartException;
import shook.shook.song.domain.Artist;

class VotingSongPartsTest {

    private static Member MEMBER = new Member("a@a.com", "nickname");

    @DisplayName("VotingSongParts 를 생성할 때 중복된 파트가 존재하면 예외를 던진다.")
    @Test
    void create_fail_duplicatePartExist() {
        //given
        final Artist artist = new Artist("profile", "가수");
        final VotingSong votingSong = new VotingSong(
            "제목",
            "비디오ID는 11글자",
            "이미지URL",
            artist,
            30
        );
        final VotingSongPart firstPart = VotingSongPart.saved(1L, 5, new PartLength(5), votingSong);
        final VotingSongPart secondPart = VotingSongPart.forSave(5, new PartLength(5), votingSong);

        final VotingSongParts votingSongParts = new VotingSongParts();
        votingSongParts.addPart(firstPart);

        //when
        //then
        assertThatThrownBy(() -> votingSongParts.addPart(secondPart))
            .isInstanceOf(PartException.DuplicateStartAndLengthException.class);
    }
}
