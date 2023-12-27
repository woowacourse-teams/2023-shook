package shook.shook.song.application;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.test.context.jdbc.Sql;
import shook.shook.member.domain.Member;
import shook.shook.member.domain.repository.MemberRepository;
import shook.shook.song.domain.InMemorySongs;
import shook.shook.song.domain.Song;
import shook.shook.song.domain.killingpart.KillingPart;
import shook.shook.song.domain.killingpart.KillingPartLike;
import shook.shook.song.domain.killingpart.repository.KillingPartLikeRepository;
import shook.shook.song.domain.killingpart.repository.KillingPartRepository;

@Sql(value = "classpath:/killingpart/initialize_killing_part_song.sql")
@EnableScheduling
@SpringBootTest
class InMemorySongsSchedulerTest {

    @Autowired
    private InMemorySongs inMemorySongs;

    @Autowired
    private InMemorySongsScheduler scheduler;

    @Autowired
    private KillingPartLikeRepository likeRepository;

    @Autowired
    private KillingPartRepository killingPartRepository;

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("InMemorySongs 를 재생성한다.")
    @Test
    void recreateCachedSong() {
        // given
        // when
        scheduler.recreateCachedSong();

        // then
        assertThat(inMemorySongs.getSongs()).hasSize(4);
    }

    @DisplayName("InMemorySongs 의 상태로 데이터베이스를 업데이트한다.")
    @Test
    void updateCachedSong() {
        // given
        scheduler.recreateCachedSong();
        final Song song = inMemorySongs.getSongById(1L);
        final KillingPart killingPart = song.getKillingParts().get(0);
        final Member member = memberRepository.save(new Member("email@email.com", "nickname"));
        inMemorySongs.like(killingPart, likeRepository.save(
            new KillingPartLike(killingPart, member)
        ));

        // when
        scheduler.updateCachedSong();

        // then
        killingPartRepository.findById(killingPart.getId())
            .ifPresent(updatedKillingPart -> assertThat(updatedKillingPart.getLikeCount()).isEqualTo(1));
    }
}
