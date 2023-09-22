package shook.shook.song.application;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import shook.shook.song.domain.InMemorySongs;
import shook.shook.song.domain.repository.SongRepository;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
@Component
public class InMemorySongsScheduler {

    private final SongRepository songRepository;
    private final InMemorySongs inMemorySongs;

    @PostConstruct
    public void initialize() {
        recreateCachedSong();
    }

    @Scheduled(cron = "${schedules.in-memory-song.cron}")
    public void recreateCachedSong() {
        log.info("InMemorySongsScheduler worked");
        inMemorySongs.recreate(songRepository.findAllWithKillingParts());
    }
}
