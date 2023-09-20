package shook.shook.song.domain;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;
import shook.shook.song.exception.SongException;

@Repository
public class InMemorySongs {

    private Map<Long, Song> songsSortedInLikeCountById = new LinkedHashMap<>();

    public void recreate(final List<Song> songs) {
        songsSortedInLikeCountById = getSortedSong(songs);
    }

    private static Map<Long, Song> getSortedSong(final List<Song> songs) {
        songs.sort(Comparator.comparing(
            Song::getTotalLikeCount,
            Comparator.reverseOrder()
        ).thenComparing(Song::getId, Comparator.reverseOrder()));

        return songs.stream()
            .collect(Collectors.toMap(
                Song::getId,
                song -> song,
                (prev, update) -> update,
                LinkedHashMap::new
            ));
    }

    public List<Song> getSongs() {
        return songsSortedInLikeCountById.values()
            .stream()
            .toList();
    }

    public Song getSongById(final Long id) {
        if (songsSortedInLikeCountById.containsKey(id)) {
            return songsSortedInLikeCountById.get(id);
        }
        throw new SongException.SongNotExistException(
            Map.of("song id", String.valueOf(id))
        );
    }

    public List<Song> getPrevLikedSongs(final Song currentSong, final int prevSongCount) {
        final List<Long> songIds = songsSortedInLikeCountById.keySet()
            .stream()
            .toList();
        final int currentSongIndex = songIds.indexOf(currentSong.getId());

        return songIds.subList(Math.max(0, currentSongIndex - prevSongCount), currentSongIndex).stream()
            .map(songsSortedInLikeCountById::get)
            .toList();
    }

    public List<Song> getNextLikedSongs(final Song currentSong, final int nextSongCount) {
        final List<Long> songIds = songsSortedInLikeCountById.keySet().stream()
            .toList();
        final int currentSongIndex = songIds.indexOf(currentSong.getId());

        if (currentSongIndex == songIds.size() - 1) {
            return Collections.emptyList();
        }

        return songIds.subList(Math.min(currentSongIndex + 1, songIds.size() - 1), Math.min(songIds.size(), currentSongIndex + nextSongCount + 1))
            .stream()
            .map(songsSortedInLikeCountById::get)
            .toList();
    }
}
