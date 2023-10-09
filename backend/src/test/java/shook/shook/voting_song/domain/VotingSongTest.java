package shook.shook.voting_song.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shook.shook.part.domain.PartLength;
import shook.shook.song.domain.Artist;
import shook.shook.song.domain.ArtistName;
import shook.shook.song.domain.ProfileImageUrl;
import shook.shook.voting_song.exception.VotingSongPartException;

class VotingSongTest {

    private final Artist artist = new Artist(new ProfileImageUrl("profile"), new ArtistName("가수"));

    @DisplayName("파트 수집 중인 노래에 파트를 등록한다. ( 노래에 해당하는 파트일 때 )")
    @Test
    void addPart_valid() {
        //given
        final VotingSong votingSong = new VotingSong(
            "노래제목",
            "비디오ID는 11글자",
            "이미지URL",
            artist,
            180
        );
        final VotingSongPart votingSongPart = VotingSongPart.forSave(1, PartLength.STANDARD,
            votingSong);

        //when
        votingSong.addPart(votingSongPart);
        final String artistName = votingSong.getArtistName();

        //then
        assertThat(votingSong.getParts()).hasSize(1);
    }

    @DisplayName("파트 수집 중인 다른 노래의 파트를 등록할 때 예외가 발생한다.")
    @Test
    void addPart_invalid() {
        //given
        final VotingSong firstSong = new VotingSong(
            "노래제목",
            "비디오ID는 11글자",
            "이미지URL",
            artist,
            180
        );
        final VotingSong secondSong = new VotingSong(
            "노래제목",
            "비디오ID는 11글자",
            "이미지URL",
            artist,
            180
        );
        final VotingSongPart partInSecondSong = VotingSongPart.forSave(1, PartLength.STANDARD,
            secondSong);

        //when
        //then
        assertThatThrownBy(() -> firstSong.addPart(partInSecondSong))
            .isInstanceOf(VotingSongPartException.PartForOtherSongException.class);
    }
}
