package shook.shook.song.domain;

import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    private static final Comparator<Song> COMPARATOR =
        Comparator.comparing(Song::getTotalLikeCount, Comparator.reverseOrder())
            .thenComparing(Song::getId, Comparator.reverseOrder());
    private Map<Long, Song> songsSortedInLikeCountById = new HashMap<>();
    private List<Long> sortedIds = new ArrayList<>();

    private final EntityManager entityManager;

    public void recreate(final List<Song> songs) {
        songsSortedInLikeCountById = getSortedSong(songs);
        sortedIds = new ArrayList<>(songsSortedInLikeCountById.keySet().stream()
                                        .sorted(Comparator.comparing(songsSortedInLikeCountById::get, COMPARATOR))
                                        .toList());

        songsSortedInLikeCountById.values().stream()
            .peek(entityManager::detach)
            .flatMap(song -> song.getKillingParts().stream())
            .peek(entityManager::detach)
            .flatMap(killingPart -> killingPart.getKillingPartLikes().stream())
            .forEach(entityManager::detach);
    }

    private static Map<Long, Song> getSortedSong(final List<Song> songs) {
        return songs.stream()
            .collect(Collectors.toMap(Song::getId, song -> song, (prev, update) -> update, HashMap::new));
    }

    public List<Song> getSongs() {
        return sortedIds.stream()
            .map(songsSortedInLikeCountById::get)
            .toList();
    }

    public List<Song> getSongs(final int limit) {
        final List<Long> topSongIds = sortedIds.subList(0, Math.min(limit, sortedIds.size()));

        return topSongIds.stream()
            .map(songsSortedInLikeCountById::get)
            .toList();
    }

    public List<Song> getSortedSongsByGenre(final Genre genre) {
        return sortedIds.stream()
            .map(songsSortedInLikeCountById::get)
            .filter(song -> song.getGenre() == genre)
            .toList();
    }

    public List<Song> getSortedSongsByGenre(final Genre genre, final int limit) {
        final List<Song> songsByGenre = getSortedSongsByGenre(genre);

        return songsByGenre.subList(0, Math.min(limit, songsByGenre.size()));
    }

    public Song getSongById(final Long id) {
        if (songsSortedInLikeCountById.containsKey(id)) {
            return songsSortedInLikeCountById.get(id);
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
        final int currentSongIndex = sortedIds.indexOf(currentSong.getId());

        return sortedIds.subList(Math.max(0, currentSongIndex - prevSongCount), currentSongIndex).stream()
            .map(songsSortedInLikeCountById::get)
            .toList();
    }

    public List<Song> getNextLikedSongs(final Song currentSong, final int nextSongCount) {
        final int currentSongIndex = sortedIds.indexOf(currentSong.getId());

        if (currentSongIndex == sortedIds.size() - 1) {
            return Collections.emptyList();
        }

        return sortedIds.subList(Math.min(currentSongIndex + 1, sortedIds.size() - 1),
                                 Math.min(sortedIds.size(), currentSongIndex + nextSongCount + 1)).stream()
            .map(songsSortedInLikeCountById::get)
            .toList();
    }

    public void like(final KillingPart killingPart, final KillingPartLike likeOnKillingPart) {
        final Song song = songsSortedInLikeCountById.get(killingPart.getSong().getId());
        final KillingPart killingPartById = findKillingPart(killingPart, song);
        final boolean updated = killingPartById.like(likeOnKillingPart);
        if (updated) {
            reorder(song);
        }
    }

    public void reorder(final Song updatedSong) {
        int currentSongIndex = sortedIds.indexOf(updatedSong.getId());

        if (currentSongIndex == -1) {
            return;
        }

        if (shouldMoveForward(updatedSong, currentSongIndex)) {
            moveLeft(updatedSong, currentSongIndex);
        }

        if (shouldMoveBackward(updatedSong, currentSongIndex)) {
            moveRight(updatedSong, currentSongIndex);
        }
    }

    private boolean shouldMoveForward(final Song song, final int index) {
        if (index == 0) {
            return false;
        }

        final Long prevSongId = sortedIds.get(index - 1);
        final Song prevSong = songsSortedInLikeCountById.get(prevSongId);

        return index > 0 && shouldSwapWithPrevious(song, prevSong);
    }

    private boolean shouldMoveBackward(final Song song, final int index) {
        if (index == sortedIds.size() - 1) {
            return false;
        }

        final Long nextSongId = sortedIds.get(index + 1);
        final Song nextSong = songsSortedInLikeCountById.get(nextSongId);

        return index < sortedIds.size() - 1 && shouldSwapWithNext(song, nextSong);
    }

    private void moveLeft(final Song changedSong, final int songIndex) {
        int currentSongIndex = songIndex;

        while (currentSongIndex > 0 && currentSongIndex < sortedIds.size() &&
            shouldSwapWithPrevious(changedSong,
                                   songsSortedInLikeCountById.get(sortedIds.get(currentSongIndex - 1)))) {
            swap(currentSongIndex, currentSongIndex - 1);
            currentSongIndex--;
        }
    }

    private boolean shouldSwapWithPrevious(final Song song, final Song prevSong) {
        final boolean hasSameTotalLikeCountAndLargerIdThanPrevSong =
            song.getTotalLikeCount() == prevSong.getTotalLikeCount() && song.getId() > prevSong.getId();
        final boolean hasLargerTotalLikeCountThanPrevSong = song.getTotalLikeCount() > prevSong.getTotalLikeCount();

        return hasLargerTotalLikeCountThanPrevSong || hasSameTotalLikeCountAndLargerIdThanPrevSong;
    }

    private void swap(final int currentIndex, final int otherIndex) {
        final Long prevIndex = sortedIds.get(currentIndex);
        sortedIds.set(currentIndex, sortedIds.get(otherIndex));
        sortedIds.set(otherIndex, prevIndex);
    }

    private void moveRight(final Song changedSong, final int songIndex) {
        int currentSongIndex = songIndex;

        while (currentSongIndex < sortedIds.size() - 1 && currentSongIndex > 0
            && shouldSwapWithNext(changedSong, songsSortedInLikeCountById.get(sortedIds.get(currentSongIndex - 1)))) {
            swap(currentSongIndex, currentSongIndex + 1);
            currentSongIndex++;
        }
    }

    private boolean shouldSwapWithNext(final Song song, final Song nextSong) {
        final boolean hasSameTotalLikeCountAndSmallerIdThanNextSong =
            song.getTotalLikeCount() == nextSong.getTotalLikeCount() && song.getId() < nextSong.getId();
        final boolean hasSmallerTotalLikeCountThanNextSong = song.getTotalLikeCount() < nextSong.getTotalLikeCount();

        return hasSmallerTotalLikeCountThanNextSong || hasSameTotalLikeCountAndSmallerIdThanNextSong;
    }

    private static KillingPart findKillingPart(final KillingPart killingPart, final Song song) {
        return song.getKillingParts().stream()
            .filter(kp -> kp.equals(killingPart))
            .findAny()
            .orElseThrow(
                () -> new KillingPartException.PartNotExistException(
                    Map.of("killing part id", String.valueOf(killingPart.getId()))));
    }

    public void unlike(final KillingPart killingPart, final KillingPartLike unlikeOnKillingPart) {
        final Song song = songsSortedInLikeCountById.get(killingPart.getSong().getId());
        final KillingPart killingPartById = findKillingPart(killingPart, song);
        final boolean updated = killingPartById.unlike(unlikeOnKillingPart);
        if (updated) {
            reorder(song);
        }
    }
}
