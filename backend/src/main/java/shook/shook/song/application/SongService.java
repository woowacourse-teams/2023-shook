package shook.shook.song.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shook.shook.song.application.dto.SongWithKillingPartsRegisterRequest;
import shook.shook.song.domain.Song;
import shook.shook.song.domain.killingpart.repository.KillingPartRepository;
import shook.shook.song.domain.repository.SongRepository;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class SongService {

    private final SongRepository songRepository;
    private final KillingPartRepository killingPartRepository;

    @Transactional
    public long register(final SongWithKillingPartsRegisterRequest request) {
        final Song song = request.toSong();
        final Song savedSong = songRepository.save(song);

        killingPartRepository.saveAll(song.getKillingParts());

        return savedSong.getId();
    }

}
