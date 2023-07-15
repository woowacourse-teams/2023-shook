package shook.shook.song.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shook.shook.song.application.dto.SongRegisterRequest;
import shook.shook.song.application.dto.SongResponse;
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
}
