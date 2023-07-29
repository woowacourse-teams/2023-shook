package shook.shook.song.application.dto.maniadb;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;

@Getter
@XmlRootElement(name = "album", namespace = "http://www.maniadb.com/api")
public class SongAlbumResponse {

    @XmlElement(name = "image")
    private String image;
}
