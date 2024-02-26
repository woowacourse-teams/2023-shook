package shook.shook.improved.song.domain.repository.dto;

import java.util.List;
import shook.shook.improved.artist.domain.Artist;
import shook.shook.improved.song.domain.Song;

public interface SongArtists {

    Song getSong();
    List<Artist> getArtists();
}
