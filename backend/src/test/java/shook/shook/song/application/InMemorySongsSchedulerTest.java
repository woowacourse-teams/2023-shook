package shook.shook.song.application;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.test.context.jdbc.Sql;
import shook.shook.song.domain.InMemorySongs;

@Sql(value = "classpath:/killingpart/initialize_killing_part_song.sql")
@EnableScheduling
@SpringBootTest
class InMemorySongsSchedulerTest {

    @Autowired
    private InMemorySongs inMemorySongs;

    @Autowired
    private InMemorySongsScheduler scheduler;

    @DisplayName("InMemorySongs 를 재생성한다.")
    @Test
    void recreateCachedSong() {
        // given
        // when
        scheduler.recreateCachedSong();

        // then
        assertThat(inMemorySongs.getSongs()).hasSize(4);
    }

    @DisplayName("Scheduler 가 1초마다 실행된다.")
    @Test
    void schedule() throws InterruptedException {
        // given
        // when
        inMemorySongs.recreate(Collections.emptyList());
        Thread.sleep(1000);

        // then
        assertThat(inMemorySongs.getSongs()).hasSize(4);
    }
}
