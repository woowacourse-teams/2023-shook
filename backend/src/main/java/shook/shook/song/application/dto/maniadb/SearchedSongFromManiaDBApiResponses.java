package shook.shook.song.application.dto.maniadb;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.List;
import lombok.Getter;

@Getter
@XmlRootElement(name = "channel")
public class SearchedSongFromManiaDBApiResponses {

    @XmlElement(name = "item")
    private List<SearchedSongFromManiaDBApiResponse> songs;
}
