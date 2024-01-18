package shook.shook.legacy.song.application;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import shook.shook.legacy.song.domain.InMemorySongs;
import shook.shook.legacy.song.domain.Song;
import shook.shook.legacy.song.domain.killingpart.KillingPart;
import shook.shook.legacy.song.domain.repository.SongRepository;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
@Component
public class InMemorySongsScheduler {

    private final SongRepository songRepository;
    private final InMemorySongs inMemorySongs;
    private final EntityManager entityManager;

    @PostConstruct
    public void initialize() {
        recreateCachedSong();
    }

    @Scheduled(cron = "${schedules.in-memory-song.cron}")
    public void recreateCachedSong() {
        log.info("InMemorySongsScheduler worked");
        final List<Song> songs = songRepository.findAllWithKillingPartsAndLikes();
        detachSongs(songs);
        inMemorySongs.refreshSongs(songs);
    }

    private void detachSongs(final List<Song> songs) {
        songs.stream()
            .peek(entityManager::detach)
            .flatMap(song -> song.getKillingParts().stream())
            .forEach(entityManager::detach);
    }

    @Transactional
    @Scheduled(cron = "${schedules.in-memory-song.update-cron}")
    public void updateCachedSong() {
        log.info("InMemorySongsScheduler LikeCount update progressed");
        final List<KillingPart> killingParts = inMemorySongs.getSongs().stream()
            .flatMap(song -> song.getKillingParts().stream())
            .toList();
        killingParts.forEach(entityManager::merge);
    }
}
