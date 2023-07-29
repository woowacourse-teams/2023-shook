package shook.shook.song.application.dto.maniadb;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;

@Getter
@XmlRootElement(name = "item")
public class UnregisteredSongResponse {

    @XmlElement(name = "title")
    private String title;

    @XmlElement(name = "trackartists", namespace = "http://www.maniadb.com/api")
    private SongTrackArtistsResponse trackArtists;

    @XmlElement(name = "album", namespace = "http://www.maniadb.com/api")
    private SongAlbumResponse album;
}
