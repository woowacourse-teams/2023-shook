package shook.shook.song.application.dto.maniadb;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.List;
import lombok.Getter;

@Getter
@XmlRootElement(name = "trackartists", namespace = "http://www.maniadb.com/api")
public class SongTrackArtistsResponse {

    @XmlElement(name = "artist", namespace = "http://www.maniadb.com/api")
    private List<SongArtistResponse> artists;
}
