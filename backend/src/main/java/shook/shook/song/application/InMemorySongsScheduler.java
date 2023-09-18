package shook.shook.song.application;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import shook.shook.song.domain.InMemorySongs;
import shook.shook.song.domain.repository.SongRepository;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Component
public class InMemorySongsScheduler {

    private final SongRepository songRepository;
    private final InMemorySongs inMemorySongs;

    @PostConstruct
    public void initialize() {
        recreateCachedSong();
    }

    @Scheduled(cron = "0 17 21 * * ?")
    public void recreateCachedSong() {
        inMemorySongs.recreate(songRepository.findAllWithKillingParts());
    }
}
