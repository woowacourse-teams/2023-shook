package shook.shook.song.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import shook.shook.member.domain.Member;
import shook.shook.member.domain.repository.MemberRepository;
import shook.shook.part.domain.PartLength;
import shook.shook.song.domain.KillingParts;
import shook.shook.song.domain.Song;
import shook.shook.song.domain.killingpart.KillingPart;
import shook.shook.song.domain.killingpart.KillingPartLike;
import shook.shook.song.domain.killingpart.repository.KillingPartLikeRepository;
import shook.shook.song.domain.killingpart.repository.KillingPartRepository;
import shook.shook.song.domain.repository.dto.SongTotalLikeCountDto;
import shook.shook.support.UsingJpaTest;

class SongRepositoryTest extends UsingJpaTest {

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private KillingPartRepository killingPartRepository;

    @Autowired
    private KillingPartLikeRepository likeRepository;

    @Autowired
    private MemberRepository memberRepository;

    private Song createNewSongWithKillingParts() {
        final KillingPart firstKillingPart = KillingPart.forSave(10, PartLength.SHORT);
        final KillingPart secondKillingPart = KillingPart.forSave(15, PartLength.SHORT);
        final KillingPart thirdKillingPart = KillingPart.forSave(20, PartLength.SHORT);

        return new Song(
            "제목", "비디오URL", "이미지URL", "가수", 5,
            new KillingParts(List.of(firstKillingPart, secondKillingPart, thirdKillingPart)));
    }

    private Member createAndSaveMember(final String email, final String name) {
        final Member member = new Member(email, name);
        return memberRepository.save(member);
    }

    @DisplayName("Song 을 저장한다.")
    @Test
    void save() {
        //given
        final Song song = createNewSongWithKillingParts();

        //when
        final Song savedSong = songRepository.save(song);

        //then
        assertThat(song).isSameAs(savedSong);
        assertThat(savedSong.getId()).isNotNull();
    }

    @DisplayName("Id로 Song 을 조회한다.")
    @Test
    void findById() {
        //given
        final Song song = createNewSongWithKillingParts();
        songRepository.save(song);
        killingPartRepository.saveAll(song.getKillingParts());

        //when
        saveAndClearEntityManager();
        final Optional<Song> findSong = songRepository.findById(song.getId());

        //then
        assertThat(findSong).isPresent();
        assertThat(findSong.get()).usingRecursiveComparison()
            .isEqualTo(song);
    }

    @DisplayName("Song 을 저장할 때의 시간 정보로 createAt이 자동 생성된다.")
    @Test
    void createdAt_prePersist() {
        //given
        final Song song = createNewSongWithKillingParts();

        //when
        final LocalDateTime prev = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
        final Song saved = songRepository.save(song);
        final LocalDateTime after = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);

        //then
        assertThat(song).isSameAs(saved);
        assertThat(song.getCreatedAt()).isBetween(prev, after);
    }

    @Sql("classpath:/song/drop_create_empty_schema.sql")
    @DisplayName("Song 을 KillingPart 의 총합 좋아요 수와 함께 조회한다.")
    @Test
    void findAllWithTotalLikeCount() {
        // given
        final Member firstMember = createAndSaveMember("first@naver.com", "first");
        final Member secondMember = createAndSaveMember("second@naver.com", "second");
        final Song firstSong = songRepository.save(createNewSongWithKillingParts());
        final Song secondSong = songRepository.save(createNewSongWithKillingParts());
        final Song thirdSong = songRepository.save(createNewSongWithKillingParts());

        killingPartRepository.saveAll(firstSong.getKillingParts());
        killingPartRepository.saveAll(secondSong.getKillingParts());
        killingPartRepository.saveAll(thirdSong.getKillingParts());

        addLikeToKillingPart(firstSong.getKillingParts().get(0), firstMember);
        addLikeToKillingPart(firstSong.getKillingParts().get(0), secondMember);
        addLikeToKillingPart(firstSong.getKillingParts().get(1), firstMember);
        addLikeToKillingPart(firstSong.getKillingParts().get(2), firstMember);

        addLikeToKillingPart(secondSong.getKillingParts().get(0), firstMember);
        addLikeToKillingPart(secondSong.getKillingParts().get(2), secondMember);

        // when
        saveAndClearEntityManager();
        final List<SongTotalLikeCountDto> songsWithLikeCount = songRepository.findAllWithTotalLikeCount();

        // then
        assertThat(songsWithLikeCount).extracting(SongTotalLikeCountDto::getTotalLikeCount)
            .containsExactlyInAnyOrder(4L, 2L, 0L);
    }

    private void addLikeToKillingPart(final KillingPart killingPart, final Member member) {
        final KillingPartLike like = new KillingPartLike(killingPart, member);
        killingPart.like(like);
        likeRepository.save(like);
    }
}
