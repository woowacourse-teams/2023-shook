package shook.shook.song.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shook.shook.part.domain.Part;
import shook.shook.part.domain.PartLength;
import shook.shook.part.exception.PartException;

class SongTest {

    @DisplayName("노래에 파트를 등록한다. ( 노래에 해당하는 파트일 때 )")
    @Test
    void addPart_valid() {
        //given
        final Song song = new Song("노래제목", "비디오URL", "가수", 180);
        final Part part = Part.forSave(1, PartLength.STANDARD, song);

        //when
        song.addPart(part);

        //then
        assertThat(song.getParts()).hasSize(1);
    }

    @DisplayName("다른 노래의 파트를 등록할 때 예외를 발생한다.")
    @Test
    void addPart_invalid() {
        //given
        final Song firstSong = new Song("노래제목", "비디오URL", "가수", 180);
        final Song secondSong = new Song("노래제목", "비디오URL", "가수", 180);
        final Part partInSecondSong = Part.forSave(1, PartLength.STANDARD, secondSong);

        //when
        //then
        assertThatThrownBy(() -> firstSong.addPart(partInSecondSong))
            .isInstanceOf(PartException.PartForOtherSongException.class);
    }
}
