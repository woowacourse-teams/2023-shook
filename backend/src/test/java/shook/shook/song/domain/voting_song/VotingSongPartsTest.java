package shook.shook.song.domain.voting_song;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shook.shook.part.domain.PartLength;
import shook.shook.part.exception.PartException;

class VotingSongPartsTest {

    @DisplayName("VotingSongParts 를 생성할 때 중복된 파트가 존재하면 예외를 던진다.")
    @Test
    void create_fail_duplicatePartExist() {
        //given
        final VotingSong votingSong = new VotingSong("제목", "비디오URL", "이미지URL", "가수", 30);
        final VotingSongPart firstPart = VotingSongPart.saved(1L, 5, PartLength.SHORT, votingSong);
        final VotingSongPart secondPart = VotingSongPart.forSave(5, PartLength.SHORT, votingSong);

        final VotingSongParts votingSongParts = new VotingSongParts();
        votingSongParts.addPart(firstPart);

        //when
        //then
        assertThatThrownBy(() -> votingSongParts.addPart(secondPart))
            .isInstanceOf(PartException.DuplicateStartAndLengthException.class);
    }
}
