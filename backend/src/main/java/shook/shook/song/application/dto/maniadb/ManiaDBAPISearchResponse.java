package shook.shook.song.application.dto.maniadb;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;

@Getter
@XmlRootElement(name = "rss")
public class ManiaDBAPISearchResponse {

    @XmlElement(name = "channel")
    private UnregisteredSongResponses songs;
}
