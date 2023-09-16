package shook.shook.song.domain;

import shook.shook.song.exception.SongException;

import java.util.*;

public class CachedSong {

    private static final int HOT_SONG_COUNT = 100;
    private static final Map<Long, Song> songsSortedInLikeCountById = new LinkedHashMap<>();

    private CachedSong() {
    }

    public static void recreate(final List<Song> songs) {
        clear();
        final List<Song> sortedSongs = getSortedSong(songs);
        for (final Song song : sortedSongs) {
            songsSortedInLikeCountById.put(song.getId(), song);
        }
    }

    private static List<Song> getSortedSong(final List<Song> songs) {
        return songs.stream()
            .sorted(
                Comparator.comparing(
                    Song::getTotalLikeCount,
                    Comparator.reverseOrder()
                ).thenComparing(Song::getId, Comparator.reverseOrder())
            ).toList();
    }

    public static List<Song> getSongs() {
        return songsSortedInLikeCountById.values()
            .stream()
            .toList();
    }

    public static Song getSongById(final Long id) {
        if (songsSortedInLikeCountById.containsKey(id)) {
            return songsSortedInLikeCountById.get(id);
        }
        throw new SongException.SongNotExistException(
            Map.of("song id", String.valueOf(id))
        );
    }

    public static List<Song> getPrevLikedSongs(final Song currentSong, final int prevSongCount) {
        final List<Long> songIds = songsSortedInLikeCountById.keySet().stream()
            .toList();
        final int currentSongIndex = songIds.indexOf(currentSong.getId());

        return songIds.subList(Math.max(0, currentSongIndex - prevSongCount), currentSongIndex).stream()
            .map(songsSortedInLikeCountById::get)
            .toList();
    }

    public static List<Song> getNextLikedSongs(final Song currentSong, final int nextSongCount) {
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

    private static void clear() {
        songsSortedInLikeCountById.clear();
    }
}
