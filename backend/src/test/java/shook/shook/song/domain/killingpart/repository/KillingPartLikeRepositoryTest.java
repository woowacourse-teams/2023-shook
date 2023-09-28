package shook.shook.song.domain.killingpart.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import shook.shook.member.domain.Member;
import shook.shook.member.domain.repository.MemberRepository;
import shook.shook.song.domain.killingpart.KillingPart;
import shook.shook.song.domain.killingpart.KillingPartLike;
import shook.shook.song.domain.killingpart.repository.dto.SongKillingPartKillingPartLikeCreatedAtDto;
import shook.shook.support.UsingJpaTest;

@Sql("classpath:/killingpart/initialize_killing_part_song.sql")
class KillingPartLikeRepositoryTest extends UsingJpaTest {

    private static KillingPart FIRST_SAVED_KILLING_PART;
    private static KillingPart SECOND_SAVED_KILLING_PART;
    private static Member SAVED_MEMBER;

    @Autowired
    private KillingPartRepository killingPartRepository;

    @Autowired
    private KillingPartLikeRepository killingPartLikeRepository;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        FIRST_SAVED_KILLING_PART = killingPartRepository.findById(1L).get();
        SECOND_SAVED_KILLING_PART = killingPartRepository.findById(2L).get();
        SAVED_MEMBER = memberRepository.findById(1L).get();
    }

    @DisplayName("KillingPartLike 를 저장한다.")
    @Test
    void save() {
        //given
        final KillingPartLike killingPartLike = new KillingPartLike(FIRST_SAVED_KILLING_PART,
            SAVED_MEMBER);

        //when
        final KillingPartLike like = killingPartLikeRepository.save(killingPartLike);

        //then
        assertThat(like).isSameAs(killingPartLike);
        assertThat(killingPartLike.getId()).isNotNull();
    }

    @DisplayName("KillingPartLike 를 저장할 때의 시간 정보로 createAt이 자동 생성된다.")
    @Test
    void createdAt() {
        //given
        final KillingPartLike killingPartLike = new KillingPartLike(FIRST_SAVED_KILLING_PART,
            SAVED_MEMBER);

        //when
        final LocalDateTime prev = LocalDateTime.now().truncatedTo(ChronoUnit.MICROS);
        final KillingPartLike like = killingPartLikeRepository.save(killingPartLike);
        final LocalDateTime after = LocalDateTime.now().truncatedTo(ChronoUnit.MICROS);

        //then
        assertThat(like).isSameAs(killingPartLike);
        assertThat(like.getCreatedAt()).isBetween(prev, after);
    }

    @DisplayName("KillingPart 와 Member 로 isDeleted 와 상관없이 KillingPartLike 를 조회한다.")
    @Test
    void findByKillingPartAndMember_isDeleted() {
        // given
        final KillingPartLike killingPartLike = new KillingPartLike(FIRST_SAVED_KILLING_PART,
            SAVED_MEMBER);
        killingPartLikeRepository.save(killingPartLike);
        saveAndClearEntityManager();

        // when
        final Optional<KillingPartLike> foundLike = killingPartLikeRepository.findByKillingPartAndMember(
            FIRST_SAVED_KILLING_PART, SAVED_MEMBER);

        // then
        assertThat(foundLike).isPresent();
    }

    @DisplayName("Member 와 isDeleted 로 KillingPartLike 를 조회한다.")
    @Test
    void findAllByMemberAndDeleted() {
        // given
        final KillingPartLike killingPartLike = new KillingPartLike(FIRST_SAVED_KILLING_PART,
            SAVED_MEMBER);

        killingPartLikeRepository.save(killingPartLike);
        saveAndClearEntityManager();

        //when
        final List<KillingPartLike> allByMemberAndDeleted =
            killingPartLikeRepository.findAllByMemberAndIsDeleted(SAVED_MEMBER, true);

        //then
        assertThat(allByMemberAndDeleted).usingRecursiveComparison()
            .comparingOnlyFields("id")
            .isEqualTo(List.of(killingPartLike));
    }

    @DisplayName("좋아요한 노래와 킬링파트 정보를 조회한다.")
    @Test
    void findLikedKillingPartAndSongByMember() {
        //given
        final KillingPartLike firstKillingPartLike = new KillingPartLike(
            FIRST_SAVED_KILLING_PART,
            SAVED_MEMBER
        );
        final KillingPartLike secondKillingPartLike = new KillingPartLike(
            SECOND_SAVED_KILLING_PART,
            SAVED_MEMBER
        );

        firstKillingPartLike.updateDeletion();
        secondKillingPartLike.updateDeletion();

        final KillingPartLike savedFirstKillingPartLike = killingPartLikeRepository.save(firstKillingPartLike);
        final KillingPartLike savedSecondKillingPartLIke = killingPartLikeRepository.save(secondKillingPartLike);

        saveAndClearEntityManager();

        //when
        final List<SongKillingPartKillingPartLikeCreatedAtDto> likedKillingPartAndSongByMember =
            killingPartLikeRepository.findLikedKillingPartAndSongByMember(SAVED_MEMBER);

        //then
        //interface dto projection 으로 인해 필드 비교가 불가하여 정렬하여 값을 검증합니다.
        likedKillingPartAndSongByMember.sort(
            Comparator.comparing(SongKillingPartKillingPartLikeCreatedAtDto::getKillingPartLikeCreatedAt)
        );

        assertAll(
            () -> assertThat(likedKillingPartAndSongByMember).hasSize(2),
            () -> {
                final SongKillingPartKillingPartLikeCreatedAtDto firstDto = likedKillingPartAndSongByMember.get(0);
                assertThat(firstDto.getKillingPart()).isEqualTo(FIRST_SAVED_KILLING_PART);
                assertThat(firstDto.getSong().getId()).isEqualTo(FIRST_SAVED_KILLING_PART.getSong().getId());
                assertThat(firstDto.getKillingPartLikeCreatedAt()).isEqualTo(savedFirstKillingPartLike.getCreatedAt());
            },
            () -> {
                final SongKillingPartKillingPartLikeCreatedAtDto secondDto = likedKillingPartAndSongByMember.get(1);
                assertThat(secondDto.getKillingPart()).isEqualTo(SECOND_SAVED_KILLING_PART);
                assertThat(secondDto.getSong().getId()).isEqualTo(SECOND_SAVED_KILLING_PART.getSong().getId());
                assertThat(secondDto.getKillingPartLikeCreatedAt()).isEqualTo(
                    savedSecondKillingPartLIke.getCreatedAt());
            }
        );
    }

    @DisplayName("멤버가 좋아요한 킬링파트 아이디 목록을 반환한다.")
    @Test
    void findLikedKillingPartIdsByMember() {
        //given
        final KillingPartLike firstKillingPartLike = new KillingPartLike(
            FIRST_SAVED_KILLING_PART,
            SAVED_MEMBER
        );
        final KillingPartLike secondKillingPartLike = new KillingPartLike(
            SECOND_SAVED_KILLING_PART,
            SAVED_MEMBER
        );

        firstKillingPartLike.updateDeletion();
        secondKillingPartLike.updateDeletion();

        killingPartLikeRepository.save(firstKillingPartLike);
        killingPartLikeRepository.save(secondKillingPartLike);

        saveAndClearEntityManager();

        //when
        final List<Long> likedKillingPartIds = killingPartLikeRepository.findLikedKillingPartIdsByMember(
            SAVED_MEMBER);

        //then
        assertThat(likedKillingPartIds).containsExactly(
            FIRST_SAVED_KILLING_PART.getId(),
            SECOND_SAVED_KILLING_PART.getId()
        );
    }
}
