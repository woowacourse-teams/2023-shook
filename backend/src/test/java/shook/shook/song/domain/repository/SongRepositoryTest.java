package shook.shook.song.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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

    @DisplayName("일치하는 가수를 가진 모든 Song 목록을 조회한다. (가수는 대소문자를 무시한다.)")
    @ParameterizedTest(name = "가수의  이름이 {0} 일 때")
    @ValueSource(strings = {"redvelvet", "Redvelvet"})
    void findAllBySingerIgnoringCase_exist(final String singer) {
        //given
        final Song song1 = new Song("노래제목", "비디오URL", "image", "RedVelvet", 180);
        final Song song2 = new Song("노래제목2", "비디오URL", "image", "RedVelvet", 100);
        songRepository.save(song1);
        songRepository.save(song2);

        //when
        saveAndClearEntityManager();
        final List<Song> songs = songRepository.findAllBySingerIgnoringCase(singer);

        //then
        assertThat(songs).usingRecursiveComparison()
            .isEqualTo(List.of(song1, song2));
    }

    @DisplayName("일치하는 가수가 없다면 빈 Song 목록이 조회된다")
    @Test
    void findAllBySingerIgnoringCase_noExist() {
        //given
        final Song song = new Song("노래제목", "비디오URL", "image", "RedVelvet", 180);
        songRepository.save(song);

        //when
        saveAndClearEntityManager();
        final List<Song> songs = songRepository.findAllBySingerIgnoringCase("뉴진스");

        //then
        assertThat(songs).isEmpty();
    }

    @DisplayName("일치하는 제목을 가진 모든 Song 목록을 조회한다. (제목은 대소문자를 무시한다.)")
    @ParameterizedTest(name = "제목이 {0} 일 때")
    @ValueSource(strings = {"hi", "HI"})
    void findAllByTitleIgnoringCase_exist(final String title) {
        //given
        final Song song1 = new Song("Hi", "비디오URL", "image", "가수", 180);
        final Song song2 = new Song("hI", "비디오URL", "image", "가수", 100);
        songRepository.save(song1);
        songRepository.save(song2);

        //when
        saveAndClearEntityManager();
        final List<Song> songs = songRepository.findAllByTitleIgnoringCase(title);

        //then
        assertThat(songs).usingRecursiveComparison()
            .isEqualTo(List.of(song1, song2));
    }

    @DisplayName("일치하는 제목이 없다면 빈 Song 목록이 조회된다")
    @Test
    void findAllByTitleIgnoringCase_noExist() {
        //given
        final Song song = new Song("노래제목", "비디오URL", "image", "RedVelvet", 180);
        songRepository.save(song);

        //when
        saveAndClearEntityManager();
        final List<Song> songs = songRepository.findAllByTitleIgnoringCase("다른제목");

        //then
        assertThat(songs).isEmpty();
    }

    @DisplayName("제목과 가수가 모두 일치하는 모든 Song 목록을 조회한다. (가수와 제목은 대소문자를 무시한다.)")
    @Test
    void findAllByTitleAndSingerIgnoringCase_exist() {
        //given
        final Song song1 = new Song("Hi", "비디오URL", "image", "가수", 180);
        final Song song2 = new Song("hI", "비디오URL", "image", "가수", 100);
        final Song song3 = new Song("hI", "비디오URL", "image", "다른가수", 100);
        songRepository.save(song1);
        songRepository.save(song2);
        songRepository.save(song3);

        //when
        saveAndClearEntityManager();
        final List<Song> songs = songRepository.findAllByTitleAndSingerIgnoringCase("hi", "가수");

        //then
        assertThat(songs).usingRecursiveComparison()
            .isEqualTo(List.of(song1, song2));
    }

    @DisplayName("제목과 가수가 모두 일치하는 Song 이 없다면 빈 Song 목록이 조회된다")
    @Test
    void findAllByTitleAndSingerIgnoringCase_noExist() {
        //given
        final Song song1 = new Song("노래제목", "비디오URL", "image", "뉴진스", 180);
        final Song song2 = new Song("다른제목", "비디오URL", "image", "RedVelvet", 180);
        songRepository.save(song1);
        songRepository.save(song2);

        //when
        saveAndClearEntityManager();
        final List<Song> songs = songRepository.findAllByTitleAndSingerIgnoringCase("다른제목", "뉴진스");

        //then
        assertThat(songs).isEmpty();
    }

    @DisplayName("모든 노래와 노래의 총 득표수를 가진 객체 리스트를 반환한다.")

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

    @DisplayName("주어진 id보다 좋아요가 적은 노래 10개를 조회한다. (데이터가 충분할 때)")
    @Test
    void findSongsWithLessLikeCountThanSongWithId() {
        // given
        final Member member = createAndSaveMember("first@naver.com", "first");
        final Song eleventhSong = songRepository.save(createNewSongWithKillingParts());
        final Song tenthSong = songRepository.save(createNewSongWithKillingParts());
        final Song ninthSong = songRepository.save(createNewSongWithKillingParts());
        final Song eighthSong = songRepository.save(createNewSongWithKillingParts());
        final Song seventhSong = songRepository.save(createNewSongWithKillingParts());
        final Song sixthSong = songRepository.save(createNewSongWithKillingParts());
        final Song fifthSong = songRepository.save(createNewSongWithKillingParts());
        final Song fourthSong = songRepository.save(createNewSongWithKillingParts());
        final Song thirdSong = songRepository.save(createNewSongWithKillingParts());
        final Song secondSong = songRepository.save(createNewSongWithKillingParts());
        final Song standardSong = songRepository.save(createNewSongWithKillingParts());

        killingPartRepository.saveAll(standardSong.getKillingParts());
        killingPartRepository.saveAll(secondSong.getKillingParts());
        killingPartRepository.saveAll(thirdSong.getKillingParts());
        killingPartRepository.saveAll(fourthSong.getKillingParts());
        killingPartRepository.saveAll(fifthSong.getKillingParts());
        killingPartRepository.saveAll(sixthSong.getKillingParts());
        killingPartRepository.saveAll(seventhSong.getKillingParts());
        killingPartRepository.saveAll(eighthSong.getKillingParts());
        killingPartRepository.saveAll(ninthSong.getKillingParts());
        killingPartRepository.saveAll(tenthSong.getKillingParts());
        killingPartRepository.saveAll(eleventhSong.getKillingParts());

        addLikeToKillingPart(standardSong.getKillingParts().get(0), member);
        addLikeToKillingPart(standardSong.getKillingParts().get(1), member);
        addLikeToKillingPart(standardSong.getKillingParts().get(2), member);

        addLikeToKillingPart(secondSong.getKillingParts().get(0), member);
        addLikeToKillingPart(secondSong.getKillingParts().get(1), member);
        addLikeToKillingPart(secondSong.getKillingParts().get(2), member);

        addLikeToKillingPart(thirdSong.getKillingParts().get(0), member);
        addLikeToKillingPart(thirdSong.getKillingParts().get(1), member);
        addLikeToKillingPart(thirdSong.getKillingParts().get(2), member);

        addLikeToKillingPart(fourthSong.getKillingParts().get(0), member);
        addLikeToKillingPart(fourthSong.getKillingParts().get(1), member);

        addLikeToKillingPart(fifthSong.getKillingParts().get(0), member);
        addLikeToKillingPart(fifthSong.getKillingParts().get(1), member);

        // when
        saveAndClearEntityManager();
        final List<Song> songs = songRepository.findSongsWithLessLikeCountThanSongWithId(
            standardSong.getId(),
            PageRequest.of(0, 10)
        );

        // then
        assertThat(songs).usingRecursiveComparison()
            .ignoringFieldsOfTypes(LocalDateTime.class)
            .isEqualTo(List.of(secondSong, thirdSong, fourthSong, fifthSong, sixthSong, seventhSong,
                eighthSong, ninthSong, tenthSong, eleventhSong)
            );
    }

    @DisplayName("주어진 id보다 좋아요가 적은 노래 10개를 조회한다. (데이터가 기준보다 적을 때)")
    @Test
    void findSongsWithLessLikeCountThanSongWithId_SmallData() {
        // given
        final Member firstMember = createAndSaveMember("first@naver.com", "first");

        final Song firstSong = songRepository.save(createNewSongWithKillingParts());
        final Song secondSong = songRepository.save(createNewSongWithKillingParts());
        final Song thirdSong = songRepository.save(createNewSongWithKillingParts());
        final Song standardSong = songRepository.save(createNewSongWithKillingParts());
        final Song fifthSong = songRepository.save(createNewSongWithKillingParts());

        killingPartRepository.saveAll(firstSong.getKillingParts());
        killingPartRepository.saveAll(secondSong.getKillingParts());
        killingPartRepository.saveAll(thirdSong.getKillingParts());
        killingPartRepository.saveAll(standardSong.getKillingParts());
        killingPartRepository.saveAll(fifthSong.getKillingParts());

        addLikeToKillingPart(firstSong.getKillingParts().get(0), firstMember);
        addLikeToKillingPart(firstSong.getKillingParts().get(1), firstMember);
        addLikeToKillingPart(firstSong.getKillingParts().get(2), firstMember);

        addLikeToKillingPart(secondSong.getKillingParts().get(0), firstMember);
        addLikeToKillingPart(secondSong.getKillingParts().get(1), firstMember);
        addLikeToKillingPart(secondSong.getKillingParts().get(2), firstMember);

        addLikeToKillingPart(thirdSong.getKillingParts().get(0), firstMember);
        addLikeToKillingPart(thirdSong.getKillingParts().get(1), firstMember);

        addLikeToKillingPart(standardSong.getKillingParts().get(0), firstMember);
        addLikeToKillingPart(standardSong.getKillingParts().get(1), firstMember);

        addLikeToKillingPart(fifthSong.getKillingParts().get(0), firstMember);
        addLikeToKillingPart(fifthSong.getKillingParts().get(1), firstMember);

        // when
        saveAndClearEntityManager();
        final List<Song> songs = songRepository.findSongsWithLessLikeCountThanSongWithId(
            standardSong.getId(),
            PageRequest.of(0, 10)
        );

        // then
        assertThat(songs).usingRecursiveComparison()
            .ignoringFieldsOfTypes(LocalDateTime.class)
            .isEqualTo(List.of(thirdSong));
    }

    @DisplayName("주어진 id보다 좋아요가 많은 노래 10개를 조회한다. (데이터가 충분할 때)")
    @Test
    void findSongsWithMoreLikeCountThanSongWithId() {
        // given
        final Member member = createAndSaveMember("first@naver.com", "first");
        final Song firstSong = songRepository.save(createNewSongWithKillingParts());
        final Song secondSong = songRepository.save(createNewSongWithKillingParts());
        final Song thirdSong = songRepository.save(createNewSongWithKillingParts());
        final Song fourthSong = songRepository.save(createNewSongWithKillingParts());
        final Song fifthSong = songRepository.save(createNewSongWithKillingParts());
        final Song standardSong = songRepository.save(createNewSongWithKillingParts());
        final Song seventhSong = songRepository.save(createNewSongWithKillingParts());
        final Song eighthSong = songRepository.save(createNewSongWithKillingParts());
        final Song ninthSong = songRepository.save(createNewSongWithKillingParts());
        final Song tenthSong = songRepository.save(createNewSongWithKillingParts());
        final Song eleventhSong = songRepository.save(createNewSongWithKillingParts());

        killingPartRepository.saveAll(firstSong.getKillingParts());
        killingPartRepository.saveAll(secondSong.getKillingParts());
        killingPartRepository.saveAll(thirdSong.getKillingParts());
        killingPartRepository.saveAll(fourthSong.getKillingParts());
        killingPartRepository.saveAll(fifthSong.getKillingParts());
        killingPartRepository.saveAll(standardSong.getKillingParts());
        killingPartRepository.saveAll(seventhSong.getKillingParts());
        killingPartRepository.saveAll(eighthSong.getKillingParts());
        killingPartRepository.saveAll(ninthSong.getKillingParts());
        killingPartRepository.saveAll(tenthSong.getKillingParts());
        killingPartRepository.saveAll(eleventhSong.getKillingParts());

        addLikeToKillingPart(firstSong.getKillingParts().get(0), member);
        addLikeToKillingPart(firstSong.getKillingParts().get(1), member);
        addLikeToKillingPart(firstSong.getKillingParts().get(2), member);

        addLikeToKillingPart(secondSong.getKillingParts().get(0), member);
        addLikeToKillingPart(secondSong.getKillingParts().get(1), member);
        addLikeToKillingPart(secondSong.getKillingParts().get(2), member);

        addLikeToKillingPart(thirdSong.getKillingParts().get(0), member);
        addLikeToKillingPart(thirdSong.getKillingParts().get(1), member);
        addLikeToKillingPart(thirdSong.getKillingParts().get(2), member);

        addLikeToKillingPart(fourthSong.getKillingParts().get(0), member);
        addLikeToKillingPart(fourthSong.getKillingParts().get(1), member);

        addLikeToKillingPart(fifthSong.getKillingParts().get(0), member);
        addLikeToKillingPart(fifthSong.getKillingParts().get(1), member);

        // when
        saveAndClearEntityManager();
        final List<Song> songs = songRepository.findSongsWithMoreLikeCountThanSongWithId(
            standardSong.getId(),
            PageRequest.of(0, 10)
        );

        // then
        assertThat(songs).usingRecursiveComparison()

            .isEqualTo(expectedSongs);
        assertThat(totalVotes).usingRecursiveComparison()
            .isEqualTo(expectedTotalVotes);

            .ignoringFieldsOfTypes(LocalDateTime.class)
            .isEqualTo(
                List.of(thirdSong, secondSong, firstSong, fifthSong, fourthSong, eleventhSong,
                    tenthSong, ninthSong, eighthSong, seventhSong));
    }

    @DisplayName("주어진 id보다 좋아요가 많은 노래 10개를 조회한다. (데이터가 기준보다 부족할 때)")
    @Test
    void findSongsWithMoreLikeCountThanSongWithId_smallData() {
        // given
        final Member member = createAndSaveMember("first@naver.com", "first");
        final Song firstSong = songRepository.save(createNewSongWithKillingParts());
        final Song secondSong = songRepository.save(createNewSongWithKillingParts());
        final Song thirdSong = songRepository.save(createNewSongWithKillingParts());
        final Song fourthSong = songRepository.save(createNewSongWithKillingParts());
        final Song fifthSong = songRepository.save(createNewSongWithKillingParts());
        final Song standardSong = songRepository.save(createNewSongWithKillingParts());

        killingPartRepository.saveAll(firstSong.getKillingParts());
        killingPartRepository.saveAll(secondSong.getKillingParts());
        killingPartRepository.saveAll(thirdSong.getKillingParts());
        killingPartRepository.saveAll(fourthSong.getKillingParts());
        killingPartRepository.saveAll(fifthSong.getKillingParts());
        killingPartRepository.saveAll(standardSong.getKillingParts());

        addLikeToKillingPart(firstSong.getKillingParts().get(0), member);
        addLikeToKillingPart(firstSong.getKillingParts().get(1), member);
        addLikeToKillingPart(firstSong.getKillingParts().get(2), member);

        addLikeToKillingPart(secondSong.getKillingParts().get(0), member);
        addLikeToKillingPart(secondSong.getKillingParts().get(1), member);
        addLikeToKillingPart(secondSong.getKillingParts().get(2), member);

        addLikeToKillingPart(thirdSong.getKillingParts().get(0), member);
        addLikeToKillingPart(thirdSong.getKillingParts().get(1), member);
        addLikeToKillingPart(thirdSong.getKillingParts().get(2), member);

        addLikeToKillingPart(fourthSong.getKillingParts().get(0), member);
        addLikeToKillingPart(fourthSong.getKillingParts().get(1), member);

        addLikeToKillingPart(fifthSong.getKillingParts().get(0), member);
        addLikeToKillingPart(fifthSong.getKillingParts().get(1), member);

        // when
        saveAndClearEntityManager();
        final List<Song> songs = songRepository.findSongsWithMoreLikeCountThanSongWithId(
            standardSong.getId(),
            PageRequest.of(0, 10)
        );

        // then
        assertThat(songs).usingRecursiveComparison()
            .ignoringFieldsOfTypes(LocalDateTime.class)
            .isEqualTo(
                List.of(thirdSong, secondSong, firstSong, fifthSong, fourthSong));
    }
}
