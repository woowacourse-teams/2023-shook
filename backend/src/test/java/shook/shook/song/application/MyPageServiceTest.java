package shook.shook.song.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import shook.shook.auth.ui.Authority;
import shook.shook.auth.ui.argumentresolver.MemberInfo;
import shook.shook.member.domain.Member;
import shook.shook.member.domain.repository.MemberRepository;
import shook.shook.song.application.dto.LikedKillingPartResponse;
import shook.shook.song.domain.killingpart.KillingPart;
import shook.shook.song.domain.killingpart.KillingPartLike;
import shook.shook.song.domain.killingpart.repository.KillingPartLikeRepository;
import shook.shook.song.domain.killingpart.repository.KillingPartRepository;
import shook.shook.support.UsingJpaTest;

@Sql(scripts = "classpath:killingpart/initialize_killing_part_song.sql")
class MyPageServiceTest extends UsingJpaTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private KillingPartRepository killingPartRepository;

    @Autowired
    private KillingPartLikeRepository killingPartLikeRepository;

    private MyPageService myPageService;

    @BeforeEach
    void setUp() {
        myPageService = new MyPageService(killingPartLikeRepository, memberRepository);
    }

    @DisplayName("멤버가 좋아요한 모든 킬링파트에 대한 정보를 조회한다.")
    @Test
    void findLikedKillingPartByMemberId() {
        //given
        final Member member = memberRepository.findById(1L).get();
        final KillingPart killingPart = killingPartRepository.findById(1L).get();
        final KillingPartLike killingPartLike = new KillingPartLike(killingPart, member);
        killingPartLike.updateDeletion();
        killingPartLikeRepository.save(killingPartLike);

        saveAndClearEntityManager();

        //when
        final List<LikedKillingPartResponse> likedKillingPartByMemberId =
            myPageService.findLikedKillingPartByMemberId(new MemberInfo(member.getId(), Authority.MEMBER));

        //then
        assertAll(
            () -> assertThat(likedKillingPartByMemberId).hasSize(1),
            () -> {
                final LikedKillingPartResponse likedKillingPartResponse = likedKillingPartByMemberId.get(0);
                final KillingPart firstKillingPart = killingPartRepository.findById(1L).get();
                assertThat(likedKillingPartResponse)
                    .usingRecursiveComparison()
                    .isEqualTo(LikedKillingPartResponse.of(firstKillingPart.getSong(), killingPart));
            }
        );
    }
}
