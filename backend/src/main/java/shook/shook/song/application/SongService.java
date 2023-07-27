package shook.shook.song.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shook.shook.part.domain.Part;
import shook.shook.song.application.dto.KillingPartResponse;
import shook.shook.song.application.dto.KillingPartsResponse;
import shook.shook.song.application.dto.SearchedSongResponse;
import shook.shook.song.application.dto.SongRegisterRequest;
import shook.shook.song.application.dto.SongResponse;
import shook.shook.song.domain.Singer;
import shook.shook.song.domain.Song;
import shook.shook.song.domain.SongTitle;
import shook.shook.song.domain.repository.SongRepository;
import shook.shook.song.exception.SongException;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class SongService {

    private final SongRepository songRepository;

    @Transactional
    public void register(final SongRegisterRequest songRegisterRequest) {
        songRepository.save(songRegisterRequest.getSong());
    }

    public SongResponse findById(final Long songId) {
        final Song song = songRepository.findById(songId)
            .orElseThrow(SongException.SongNotExistException::new);

        return SongResponse.from(song);
    }

    public SongResponse findByTitle(final String name) {
        final Song song = songRepository.findByTitle(new SongTitle(name))
            .orElseThrow(SongException.SongNotExistException::new);

        return SongResponse.from(song);
    }

    public List<SearchedSongResponse> findAllBySinger(final String singer) {
        final List<Song> songs = songRepository.findAllBySinger(new Singer(singer));

        return songs.stream()
            .map(SearchedSongResponse::from)
            .toList();
    }

    public List<SearchedSongResponse> findAllByTitle(final String title) {
        final List<Song> songs = songRepository.findAllByTitle(new SongTitle(title));

        return songs.stream()
            .map(SearchedSongResponse::from)
            .toList();
    }

    public KillingPartResponse showTopKillingPart(final Long songId) {
        final Song song = songRepository.findById(songId)
            .orElseThrow(SongException.SongNotExistException::new);

        return song.getTopKillingPart()
            .map((killingPart) -> KillingPartResponse.of(song, killingPart))
            .orElseGet(KillingPartResponse::empty);
    }

    public KillingPartsResponse showKillingParts(final Long songId) {
        final Song song = songRepository.findById(songId)
            .orElseThrow(SongException.SongNotExistException::new);

        final List<Part> killingParts = song.getKillingParts();

        return KillingPartsResponse.of(song, killingParts);
    }
}
