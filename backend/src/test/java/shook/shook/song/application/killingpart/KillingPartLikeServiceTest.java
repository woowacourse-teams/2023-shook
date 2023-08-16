package shook.shook.song.application.killingpart;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import shook.shook.member.domain.Member;
import shook.shook.member.domain.repository.MemberRepository;
import shook.shook.member.exception.MemberException;
import shook.shook.song.application.killingpart.dto.KillingPartLikeRequest;
import shook.shook.song.domain.killingpart.KillingPart;
import shook.shook.song.domain.killingpart.KillingPartLike;
import shook.shook.song.domain.killingpart.repository.KillingPartLikeRepository;
import shook.shook.song.domain.killingpart.repository.KillingPartRepository;
import shook.shook.song.exception.killingpart.KillingPartException;
import shook.shook.support.UsingJpaTest;

@Sql("classpath:/killingpart/initialize_killing_part_song.sql")
class KillingPartLikeServiceTest extends UsingJpaTest {

    private static final long UNSAVED_MEMBER_ID = Long.MAX_VALUE;
    private static final long UNSAVED_KILLING_PART_ID = Long.MAX_VALUE;
    private static KillingPart SAVED_KILLING_PART;
    private static Member SAVED_MEMBER;

    @Autowired
    private KillingPartRepository killingPartRepository;

    @Autowired
    private KillingPartLikeRepository killingPartLikeRepository;

    @Autowired
    private MemberRepository memberRepository;

    private KillingPartLikeService likeService;

    @BeforeEach
    void setUp() {
        SAVED_KILLING_PART = killingPartRepository.findById(1L).get();
        SAVED_MEMBER = memberRepository.findById(1L).get();
        likeService = new KillingPartLikeService(killingPartRepository, memberRepository,
            killingPartLikeRepository);
    }

    @DisplayName("킬링파트 좋아요를 누른다.")
    @Nested
    class Create {

        @DisplayName("좋아요 데이터가 없는 경우, 새로운 좋아요가 생성된다.")
        @Test
        void create_newLike() {
            // given
            // when
            likeService.updateLikeStatus(SAVED_KILLING_PART.getId(), SAVED_MEMBER.getId(),
                new KillingPartLikeRequest(true));
            saveAndClearEntityManager();

            // then
            final Optional<KillingPartLike> savedLike = killingPartLikeRepository.
                findByKillingPartAndMember(SAVED_KILLING_PART, SAVED_MEMBER);
            final Optional<KillingPart> updatedKillingPart = killingPartRepository.findById(
                SAVED_KILLING_PART.getId());

            assertThat(savedLike).isPresent()
                .get()
                .hasFieldOrPropertyWithValue("isDeleted", false);

            assertThat(updatedKillingPart).isPresent()
                .get()
                .hasFieldOrPropertyWithValue("likeCount", 1);
        }

        @DisplayName("좋아요 데이터가 존재하지만 삭제된 경우, 상태가 변경된다.")
        @Test
        void create_updateLike_exist() {
            // given
            likeService.updateLikeStatus(SAVED_KILLING_PART.getId(), SAVED_MEMBER.getId(),
                new KillingPartLikeRequest(true));
            likeService.updateLikeStatus(SAVED_KILLING_PART.getId(), SAVED_MEMBER.getId(),
                new KillingPartLikeRequest(false));
            saveAndClearEntityManager();

            // when
            likeService.updateLikeStatus(SAVED_KILLING_PART.getId(), SAVED_MEMBER.getId(),
                new KillingPartLikeRequest(true));
            saveAndClearEntityManager();

            // then
            final Optional<KillingPartLike> savedLike = killingPartLikeRepository.
                findByKillingPartAndMember(SAVED_KILLING_PART, SAVED_MEMBER);
            final Optional<KillingPart> updatedKillingPart = killingPartRepository.findById(
                SAVED_KILLING_PART.getId());

            assertThat(savedLike).isPresent()
                .get()
                .hasFieldOrPropertyWithValue("isDeleted", false);

            assertThat(updatedKillingPart).isPresent()
                .get()
                .hasFieldOrPropertyWithValue("likeCount", 1);
        }

        @DisplayName("좋아요 데이터가 존재하는 경우, 좋아요가 업데이트되지 않는다.")
        @Test
        void create_noAction() {
            // given
            likeService.updateLikeStatus(SAVED_KILLING_PART.getId(), SAVED_MEMBER.getId(),
                new KillingPartLikeRequest(true));
            saveAndClearEntityManager();

            // when
            likeService.updateLikeStatus(SAVED_KILLING_PART.getId(), SAVED_MEMBER.getId(),
                new KillingPartLikeRequest(true));
            saveAndClearEntityManager();

            // then
            final Optional<KillingPartLike> savedLike = killingPartLikeRepository.
                findByKillingPartAndMember(SAVED_KILLING_PART, SAVED_MEMBER);
            final Optional<KillingPart> updatedKillingPart = killingPartRepository.findById(
                SAVED_KILLING_PART.getId());

            assertThat(savedLike).isPresent()
                .get()
                .hasFieldOrPropertyWithValue("isDeleted", false);

            assertThat(updatedKillingPart).isPresent()
                .get()
                .hasFieldOrPropertyWithValue("likeCount", 1);
        }

        @DisplayName("존재하지 않는 킬링파트면 예외가 발생한다.")
        @Test
        void create_KillingPartException() {
            // given
            // when, then
            assertThatThrownBy(
                () -> likeService.updateLikeStatus(UNSAVED_KILLING_PART_ID, SAVED_MEMBER.getId(),
                    new KillingPartLikeRequest(true)))
                .isInstanceOf(KillingPartException.PartNotExistException.class);
        }

        @DisplayName("존재하지 않는 사용자면 예외가 발생한다.")
        @Test
        void create_memberNotExist() {
            // given
            // when, then
            assertThatThrownBy(
                () -> likeService.updateLikeStatus(SAVED_KILLING_PART.getId(), UNSAVED_MEMBER_ID,
                    new KillingPartLikeRequest(true)))
                .isInstanceOf(MemberException.MemberNotExistException.class);
        }
    }

    @DisplayName("킬링파트 좋아요를 취소한다.")
    @Nested
    class Delete {

        @DisplayName("좋아요 데이터가 없는 경우, 취소가 업데이트되지 않는다.")
        @Test
        void delete_noAction() {
            // given
            // when
            likeService.updateLikeStatus(SAVED_KILLING_PART.getId(), SAVED_MEMBER.getId(),
                new KillingPartLikeRequest(false));
            saveAndClearEntityManager();

            // then
            final Optional<KillingPartLike> savedLike = killingPartLikeRepository.
                findByKillingPartAndMember(SAVED_KILLING_PART, SAVED_MEMBER);
            final Optional<KillingPart> updatedKillingPart = killingPartRepository.findById(
                SAVED_KILLING_PART.getId());

            assertThat(savedLike).isEmpty();
            assertThat(updatedKillingPart).isPresent()
                .get()
                .hasFieldOrPropertyWithValue("likeCount", 0);
        }

        @DisplayName("좋아요 데이터가 존재하지만 삭제된 경우, 취소가 업데이트되지 않는다.")
        @Test
        void delete_alreadyDeleted_noAction() {
            // given
            likeService.updateLikeStatus(SAVED_KILLING_PART.getId(), SAVED_MEMBER.getId(),
                new KillingPartLikeRequest(true));
            likeService.updateLikeStatus(SAVED_KILLING_PART.getId(), SAVED_MEMBER.getId(),
                new KillingPartLikeRequest(false));
            saveAndClearEntityManager();

            // when
            likeService.updateLikeStatus(SAVED_KILLING_PART.getId(), SAVED_MEMBER.getId(),
                new KillingPartLikeRequest(false));
            saveAndClearEntityManager();

            // then
            final Optional<KillingPartLike> savedLike = killingPartLikeRepository.
                findByKillingPartAndMember(SAVED_KILLING_PART, SAVED_MEMBER);
            final Optional<KillingPart> updatedKillingPart = killingPartRepository.findById(
                SAVED_KILLING_PART.getId());

            assertThat(savedLike).isPresent()
                .get()
                .hasFieldOrPropertyWithValue("isDeleted", true);

            assertThat(updatedKillingPart).isPresent()
                .get()
                .hasFieldOrPropertyWithValue("likeCount", 0);
        }

        @DisplayName("좋아요 데이터가 존재하는 경우, 상태가 변경된다.")
        @Test
        void create_noAction() {
            // given
            likeService.updateLikeStatus(SAVED_KILLING_PART.getId(), SAVED_MEMBER.getId(),
                new KillingPartLikeRequest(true));
            saveAndClearEntityManager();

            // when
            likeService.updateLikeStatus(SAVED_KILLING_PART.getId(), SAVED_MEMBER.getId(),
                new KillingPartLikeRequest(false));
            saveAndClearEntityManager();

            // then
            final Optional<KillingPartLike> savedLike = killingPartLikeRepository.
                findByKillingPartAndMember(SAVED_KILLING_PART, SAVED_MEMBER);
            final Optional<KillingPart> updatedKillingPart = killingPartRepository.findById(
                SAVED_KILLING_PART.getId());

            assertThat(savedLike).isPresent()
                .get()
                .hasFieldOrPropertyWithValue("isDeleted", true);

            assertThat(updatedKillingPart).isPresent()
                .get()
                .hasFieldOrPropertyWithValue("likeCount", 0);
        }

        @DisplayName("존재하지 않는 킬링파트면 예외가 발생한다.")
        @Test
        void create_KillingPartException() {
            // given
            // when, then
            assertThatThrownBy(
                () -> likeService.updateLikeStatus(UNSAVED_KILLING_PART_ID, SAVED_MEMBER.getId(),
                    new KillingPartLikeRequest(true)))
                .isInstanceOf(KillingPartException.PartNotExistException.class);
        }

        @DisplayName("존재하지 않는 사용자면 예외가 발생한다.")
        @Test
        void create_memberNotExist() {
            // given
            // when, then
            assertThatThrownBy(
                () -> likeService.updateLikeStatus(SAVED_KILLING_PART.getId(), UNSAVED_MEMBER_ID,
                    new KillingPartLikeRequest(false)))
                .isInstanceOf(MemberException.MemberNotExistException.class);
        }
    }
}
