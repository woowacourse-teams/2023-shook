package shook.shook.song.application.dto.maniadb;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;

@Getter
@XmlRootElement(name = "artist", namespace = "http://www.maniadb.com/api")
public class SongArtistResponse {

    @XmlElement(name = "name")
    private String name;
}
