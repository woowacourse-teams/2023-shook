package shook.shook.song.domain;

import jakarta.persistence.EntityManager;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import shook.shook.song.domain.killingpart.KillingPart;
import shook.shook.song.domain.killingpart.KillingPartLike;
import shook.shook.song.exception.SongException;
import shook.shook.song.exception.killingpart.KillingPartException;

@RequiredArgsConstructor
@Repository
public class InMemorySongs {

    private Map<SongKey, Song> songsSortedInLikeCountById;
    private Map<Long, SongKey> songLikeCountById = new HashMap<>();

    private final EntityManager entityManager;

    public void recreate(final List<Song> songs) {
        songsSortedInLikeCountById = getSortedSong(songs);
        songLikeCountById = songsSortedInLikeCountById.keySet().stream()
            .collect(Collectors.toMap(SongKey::getId, key -> key));

        songsSortedInLikeCountById.values().stream()
            .peek(entityManager::detach)
            .flatMap(song -> song.getKillingParts().stream())
            .peek(entityManager::detach)
            .flatMap(killingPart -> killingPart.getKillingPartLikes().stream())
            .forEach(entityManager::detach);
    }

    private static Map<SongKey, Song> getSortedSong(final List<Song> songs) {
        return songs.stream()
            .collect(Collectors.toMap(song -> new SongKey(song.getId(), new AtomicInteger(song.getTotalLikeCount())),
                                      song -> song, (prev, update) -> update, TreeMap::new));
    }

    public List<Song> getSongs() {
        return songsSortedInLikeCountById.values().stream().toList();
    }

    public List<Song> getSongs(final int limit) {
        final List<Song> songs = getSongs();

        return songs.subList(0, Math.min(limit, songs.size()));
    }

    public List<Song> getSortedSongsByGenre(final Genre genre) {
        return songsSortedInLikeCountById.values().stream().filter(song -> song.getGenre() == genre).toList();
    }

    public List<Song> getSortedSongsByGenre(final Genre genre, final int limit) {
        final List<Song> songsByGenre = getSortedSongsByGenre(genre);

        return songsByGenre.subList(0, Math.min(limit, songsByGenre.size()));
    }

    public Song getSongById(final Long id) {
        final SongKey key = songLikeCountById.get(id);
        if (songsSortedInLikeCountById.containsKey(key)) {
            return songsSortedInLikeCountById.get(key);
        }
        throw new SongException.SongNotExistException(Map.of("song id", String.valueOf(id)));
    }

    public List<Song> getPrevLikedSongByGenre(final Song currentSong, final Genre genre, final int prevSongCount) {
        final List<Song> songsWithGenre = getSortedSongsByGenre(genre);
        final int currentSongIndex = songsWithGenre.indexOf(currentSong);

        return songsWithGenre.subList(Math.max(0, currentSongIndex - prevSongCount), currentSongIndex);
    }

    public List<Song> getNextLikedSongByGenre(final Song currentSong, final Genre genre, final int nextSongCount) {
        final List<Song> songsWithGenre = getSortedSongsByGenre(genre);
        final int currentSongIndex = songsWithGenre.indexOf(currentSong);

        if (currentSongIndex == songsWithGenre.size() - 1) {
            return Collections.emptyList();
        }

        return songsWithGenre.subList(Math.min(currentSongIndex + 1, songsWithGenre.size() - 1),
                                      Math.min(songsWithGenre.size(), currentSongIndex + nextSongCount + 1));
    }

    public List<Song> getPrevLikedSongs(final Song currentSong, final int prevSongCount) {
        final List<Long> songIds = songsSortedInLikeCountById.keySet().stream()
            .map(SongKey::getId)
            .toList();
        final int currentSongIndex = songIds.indexOf(currentSong.getId());

        return songIds.subList(Math.max(0, currentSongIndex - prevSongCount), currentSongIndex).stream()
            .map(songLikeCountById::get)
            .map(songsSortedInLikeCountById::get)
            .toList();
    }

    public List<Song> getNextLikedSongs(final Song currentSong, final int nextSongCount) {
        final List<Long> songIds = songsSortedInLikeCountById.keySet().stream()
            .map(SongKey::getId)
            .toList();
        final int currentSongIndex = songIds.indexOf(currentSong.getId());

        if (currentSongIndex == songIds.size() - 1) {
            return Collections.emptyList();
        }

        return songIds.subList(Math.min(currentSongIndex + 1, songIds.size() - 1),
                               Math.min(songIds.size(), currentSongIndex + nextSongCount + 1)).stream()
            .map(songLikeCountById::get)
            .map(songsSortedInLikeCountById::get)
            .toList();
    }

    public void pressLike(final KillingPart killingPart, final KillingPartLike likeOnKillingPart) {
        final SongKey songKey = songLikeCountById.remove(killingPart.getSong().getId());
        final Song song = songsSortedInLikeCountById.remove(songKey);
        songKey.likeCount.incrementAndGet();
        songLikeCountById.put(song.getId(), songKey);
        songsSortedInLikeCountById.put(songKey, song);
        final KillingPart killingPartById = findKillingPart(killingPart, song);
        killingPartById.like(likeOnKillingPart);
    }

    private static KillingPart findKillingPart(final KillingPart killingPart, final Song song) {
        return song.getKillingParts().stream().filter(kp -> kp.equals(killingPart)).findAny().orElseThrow(
            () -> new KillingPartException.PartNotExistException(
                Map.of("killing part id", String.valueOf(killingPart.getId()))));
    }

    public void cancelLike(final KillingPart killingPart, final KillingPartLike unlikeOnKillingPart) {
        final SongKey songKey = songLikeCountById.remove(killingPart.getSong().getId());
        final Song song = songsSortedInLikeCountById.remove(songKey);
        songKey.likeCount.decrementAndGet();
        songLikeCountById.put(song.getId(), songKey);
        songsSortedInLikeCountById.put(songKey, song);
        final KillingPart killingPartById = findKillingPart(killingPart, song);
        killingPartById.unlike(unlikeOnKillingPart);
    }

    private static class SongKey implements Comparable<SongKey> {

        private final Long id;
        private final AtomicInteger likeCount;

        public SongKey(final Long id, final AtomicInteger likeCount) {
            this.id = id;
            this.likeCount = likeCount;
        }

        public Long getId() {
            return id;
        }

        @Override
        public int compareTo(final SongKey o) {
            if (o.likeCount.get() == this.likeCount.get()) {
                return o.id.compareTo(this.id);
            }
            return o.likeCount.get() - this.likeCount.get();
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            final SongKey songKey = (SongKey) o;
            return Objects.equals(id, songKey.id) && Objects.equals(likeCount.get(), songKey.likeCount.get());
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, likeCount);
        }
    }
}
