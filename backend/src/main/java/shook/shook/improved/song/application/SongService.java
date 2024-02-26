package shook.shook.improved.song.application;

import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shook.shook.improved.artist.domain.repository.ArtistRepository;
import shook.shook.improved.artist.exception.ArtistException.NotExistException;
import shook.shook.improved.song.domain.Genre;
import shook.shook.improved.song.domain.Song;
import shook.shook.improved.song.application.dto.SongHighScoredResponse;
import shook.shook.improved.song.application.dto.SongRegisterRequest;
import shook.shook.improved.song.domain.repository.SongRepository;
import shook.shook.improved.song.domain.repository.dto.SongArtists;
import shook.shook.improved.song.song_artist.domain.SongArtist;
import shook.shook.improved.song.song_artist.domain.repository.SongArtistRepository;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class SongService {

    private final SongRepository songRepository;
    private final ArtistRepository artistRepository;
    private final SongArtistRepository songArtistRepository;

    @Transactional
    public Long register(final SongRegisterRequest songRegisterRequest) {
        if (!isExistArtist(songRegisterRequest.getArtistIds())) {
            throw new NotExistException();
        }
        final Song savedSong = songRepository.save(new Song(songRegisterRequest.getTitle(),
                                                            songRegisterRequest.getVideoId(),
                                                            songRegisterRequest.getImageUrl(),
                                                            songRegisterRequest.getLength(),
                                                            songRegisterRequest.getGenre()));
        songArtistRepository.saveAll(getSongArtists(savedSong, songRegisterRequest.getArtistIds()));
        return savedSong.getId();
    }

    private boolean isExistArtist(final List<Long> artistIds) {
        return artistIds.size() == artistRepository.countByIds(artistIds);
    }

    private List<SongArtist> getSongArtists(final Song song, final List<Long> artistIds) {
        return artistIds.stream()
            .map(i -> new SongArtist(song.getId(), i))
            .toList();
    }

    public List<SongHighScoredResponse> getHighScoredSong() {
        final List<SongArtists> songArtists = songRepository.findAllWithArtist();

        return songArtists.stream()
            .sorted(Comparator.comparing(sa -> sa.getSong().getScore()))
            .limit(10)
            .map(SongHighScoredResponse::from)
            .toList();
    }

    public List<SongHighScoredResponse> findSongsByGenre(final String genre) {
        final List<SongArtists> songArtistsByGenre = songRepository.findAllWithArtistByGenre(Genre.from(genre));

        return songArtistsByGenre.stream()
            .sorted(Comparator.comparingInt(sa -> sa.getSong().getScore()))
            .limit(10)
            .map(SongHighScoredResponse::from)
            .toList();
    }
}
