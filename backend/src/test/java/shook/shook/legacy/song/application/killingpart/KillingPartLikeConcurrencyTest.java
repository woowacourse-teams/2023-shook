package shook.shook.legacy.song.application.killingpart;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;
import shook.shook.legacy.member.domain.Member;
import shook.shook.legacy.member.domain.repository.MemberRepository;
import shook.shook.legacy.song.application.killingpart.dto.KillingPartLikeRequest;
import shook.shook.legacy.song.domain.InMemorySongs;
import shook.shook.legacy.song.domain.Song;
import shook.shook.legacy.song.domain.killingpart.KillingPart;
import shook.shook.legacy.song.domain.killingpart.repository.KillingPartLikeRepository;
import shook.shook.legacy.song.domain.killingpart.repository.KillingPartRepository;
import shook.shook.legacy.song.domain.repository.SongRepository;

@Sql("classpath:/killingpart/initialize_killing_part_song.sql")
@SpringBootTest
class KillingPartLikeConcurrencyTest {

    private static KillingPart SAVED_KILLING_PART;
    private static Member SAVED_MEMBER;
    private static Song SAVED_SONG;

    @Autowired
    private KillingPartRepository killingPartRepository;

    @Autowired
    private KillingPartLikeRepository killingPartLikeRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private InMemorySongs inMemorySongs;

    @Autowired
    private SongRepository songRepository;

    private KillingPartLikeService likeService;
    private TransactionTemplate transactionTemplate;

    @BeforeEach
    void setUp() {
        SAVED_SONG = songRepository.findById(1L).get();
        SAVED_KILLING_PART = killingPartRepository.findById(1L).get();
        SAVED_MEMBER = memberRepository.findById(1L).get();
        likeService = new KillingPartLikeService(killingPartRepository, memberRepository, killingPartLikeRepository,
                                                 inMemorySongs);
        transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
    }

    @DisplayName("두 사용자가 동시에 좋아요를 누르면 좋아요 개수가 2 증가한다.")
    @Test
    void likeByMultiplePeople() throws InterruptedException {
        // given
        final Member first = SAVED_MEMBER;
        final Member second = memberRepository.save(new Member("second@gmail.com", "second"));
        inMemorySongs.refreshSongs(songRepository.findAllWithKillingPartsAndLikes());

        // when
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        CountDownLatch latch = new CountDownLatch(2);
        final KillingPartLikeRequest request = new KillingPartLikeRequest(true);

        executorService.execute(() ->
                                    transactionTemplate.execute((status -> {
                                        likeService.updateLikeStatus(SAVED_KILLING_PART.getId(), first.getId(),
                                                                     request);
                                        latch.countDown();
                                        return null;
                                    }))
        );
        executorService.execute(() ->
                                    transactionTemplate.execute((status -> {
                                        likeService.updateLikeStatus(SAVED_KILLING_PART.getId(), second.getId(),
                                                                     request);
                                        latch.countDown();
                                        return null;
                                    }))
        );
        latch.await();
        Thread.sleep(1000);

        // then
        final KillingPart killingPart = inMemorySongs.getSongById(SAVED_SONG.getId()).getKillingParts().stream()
            .filter(kp -> kp.getId().equals(SAVED_KILLING_PART.getId()))
            .findAny().get();
        assertThat(killingPart.getLikeCount()).isEqualTo(2);
    }

    @Disabled("UPDATE + 1 사용 시 한 사용자의 동시에 도착하는 좋아요 요청 동시성 문제 발생")
    @DisplayName("한 사용자가 좋아요, 취소, 좋아요를 누르면 좋아요 개수가 1 증가한다.")
    @Test
    void likeByOnePersonMultipleTimes() throws InterruptedException {
        // given
        final Member first = SAVED_MEMBER;

        // when
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        CountDownLatch latch = new CountDownLatch(3);
        final KillingPartLikeRequest likeRequest = new KillingPartLikeRequest(true);
        final KillingPartLikeRequest unlikeRequest = new KillingPartLikeRequest(false);

        executorService.execute(() -> transactionTemplate.execute((status -> {
                                    likeService.updateLikeStatus(SAVED_KILLING_PART.getId(), first.getId(), likeRequest);
                                    latch.countDown();
                                    return null;
                                }))
        );
        executorService.execute(() -> transactionTemplate.execute((status -> {
                                    likeService.updateLikeStatus(SAVED_KILLING_PART.getId(), first.getId(), unlikeRequest);
                                    latch.countDown();
                                    return null;
                                }))
        );
        executorService.execute(() -> transactionTemplate.execute((status -> {
                                    likeService.updateLikeStatus(SAVED_KILLING_PART.getId(), first.getId(), likeRequest);
                                    latch.countDown();
                                    return null;
                                }))
        );

        latch.await();
        Thread.sleep(1000);

        // then
        final KillingPart killingPart = killingPartRepository.findById(SAVED_KILLING_PART.getId()).get();
        assertThat(killingPart.getLikeCount()).isEqualTo(1); // 예상: 2, 결과: 1
    }
}
