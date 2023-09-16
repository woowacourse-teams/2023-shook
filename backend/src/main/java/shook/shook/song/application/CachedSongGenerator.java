package shook.shook.song.application;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import shook.shook.song.domain.CachedSong;
import shook.shook.song.domain.repository.SongRepository;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Component
public class CachedSongGenerator {

    private final SongRepository songRepository;

    @Scheduled(cron = "0 17 21 * * ?")
    public void recreateCachedSong() {
        CachedSong.recreate(songRepository.findAllWithKillingParts());
    }
}
