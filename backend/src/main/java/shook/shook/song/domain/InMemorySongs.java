package shook.shook.song.domain;

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

    private Map<Long, Song> songs = new HashMap<>();
    private List<Long> sortedSongIds = new ArrayList<>();

    public void refreshSongs(final List<Song> songs) {
        this.songs = songs.stream()
            .collect(Collectors.toMap(Song::getId, song -> song, (prev, update) -> update, HashMap::new));
        this.sortedSongIds = new ArrayList<>(this.songs.keySet().stream()
                                                 .sorted(Comparator.comparing(this.songs::get, COMPARATOR))
                                                 .toList());
    }

    public List<Song> getSongs() {
        return sortedSongIds.stream()
            .map(songs::get)
            .toList();
    }

    public List<Song> getSongs(final int limit) {
        final List<Long> topSongIds = sortedSongIds.subList(0, Math.min(limit, sortedSongIds.size()));

        return topSongIds.stream()
            .map(songs::get)
            .toList();
    }

    public List<Song> getSortedSongsByGenre(final Genre genre) {
        return sortedSongIds.stream()
            .map(songs::get)
            .filter(song -> song.getGenre() == genre)
            .toList();
    }

    public List<Song> getSortedSongsByGenre(final Genre genre, final int limit) {
        final List<Song> songsByGenre = getSortedSongsByGenre(genre);

        return songsByGenre.subList(0, Math.min(limit, songsByGenre.size()));
    }

    public Song getSongById(final Long id) {
        if (songs.containsKey(id)) {
            return songs.get(id);
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
        final int currentSongIndex = sortedSongIds.indexOf(currentSong.getId());

        return sortedSongIds.subList(Math.max(0, currentSongIndex - prevSongCount), currentSongIndex).stream()
            .map(songs::get)
            .toList();
    }

    public List<Song> getNextLikedSongs(final Song currentSong, final int nextSongCount) {
        final int currentSongIndex = sortedSongIds.indexOf(currentSong.getId());

        if (currentSongIndex == sortedSongIds.size() - 1) {
            return Collections.emptyList();
        }

        return sortedSongIds.subList(Math.min(currentSongIndex + 1, sortedSongIds.size() - 1),
                                     Math.min(sortedSongIds.size(), currentSongIndex + nextSongCount + 1)).stream()
            .map(songs::get)
            .toList();
    }

    public void like(final KillingPart killingPart, final KillingPartLike likeOnKillingPart) {
        final Song song = songs.get(killingPart.getSong().getId());
        final KillingPart killingPartById = findKillingPart(killingPart, song);
        final boolean updated = killingPartById.like(likeOnKillingPart);
        if (updated) {
            reorder(song);
        }
    }

    private static KillingPart findKillingPart(final KillingPart killingPart, final Song song) {
        return song.getKillingParts().stream()
            .filter(kp -> kp.equals(killingPart))
            .findAny()
            .orElseThrow(
                () -> new KillingPartException.PartNotExistException(
                    Map.of("killing part id", String.valueOf(killingPart.getId()))));
    }

    public void reorder(final Song updatedSong) {
        int currentSongIndex = sortedSongIds.indexOf(updatedSong.getId());

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

        final Long prevSongId = sortedSongIds.get(index - 1);
        final Song prevSong = songs.get(prevSongId);

        return index > 0 && shouldSwapWithPrevious(song, prevSong);
    }

    private boolean shouldMoveBackward(final Song song, final int index) {
        if (index == sortedSongIds.size() - 1) {
            return false;
        }

        final Long nextSongId = sortedSongIds.get(index + 1);
        final Song nextSong = songs.get(nextSongId);

        return index < sortedSongIds.size() - 1 && shouldSwapWithNext(song, nextSong);
    }

    private void moveLeft(final Song changedSong, final int songIndex) {
        int currentSongIndex = songIndex;

        while (currentSongIndex > 0 && currentSongIndex < sortedSongIds.size() &&
            shouldSwapWithPrevious(changedSong,
                                   songs.get(sortedSongIds.get(currentSongIndex - 1)))) {
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
        final Long prevIndex = sortedSongIds.get(currentIndex);
        sortedSongIds.set(currentIndex, sortedSongIds.get(otherIndex));
        sortedSongIds.set(otherIndex, prevIndex);
    }

    private void moveRight(final Song changedSong, final int songIndex) {
        int currentSongIndex = songIndex;

        while (currentSongIndex < sortedSongIds.size() - 1 && currentSongIndex > 0
            && shouldSwapWithNext(changedSong, songs.get(sortedSongIds.get(currentSongIndex - 1)))) {
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

    public void unlike(final KillingPart killingPart, final KillingPartLike unlikeOnKillingPart) {
        final Song song = songs.get(killingPart.getSong().getId());
        final KillingPart killingPartById = findKillingPart(killingPart, song);
        final boolean updated = killingPartById.unlike(unlikeOnKillingPart);
        if (updated) {
            reorder(song);
        }
    }
}
