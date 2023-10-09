package shook.shook.song.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import shook.shook.member.domain.Member;
import shook.shook.member.domain.repository.MemberRepository;
import shook.shook.song.domain.Artist;
import shook.shook.song.domain.ArtistName;
import shook.shook.song.domain.Genre;
import shook.shook.song.domain.KillingParts;
import shook.shook.song.domain.ProfileImageUrl;
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

    @Autowired
    private ArtistRepository artistRepository;

    private Song createNewSongWithKillingParts() {
        final KillingPart firstKillingPart = KillingPart.forSave(10, 5);
        final KillingPart secondKillingPart = KillingPart.forSave(15, 5);
        final KillingPart thirdKillingPart = KillingPart.forSave(20, 5);

        final Artist artist = new Artist(new ProfileImageUrl("image"), new ArtistName("name"));
        return new Song(
            "title",
            "3rUPND6FG8A",
            "image_url",
            artist,
            230,
            Genre.from("댄스"),
            new KillingParts(List.of(firstKillingPart, secondKillingPart, thirdKillingPart))
        );
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
        final Song savedSong = saveSong(song);

        //then
        assertThat(song).isSameAs(savedSong);
        assertThat(savedSong.getId()).isNotNull();
    }

    private Song saveSong(final Song song) {
        artistRepository.save(song.getArtist());
        return songRepository.save(song);
    }

    @DisplayName("Id로 Song 을 조회한다.")
    @Test
    void findById() {
        //given
        final Song song = createNewSongWithKillingParts();
        saveSong(song);
        killingPartRepository.saveAll(song.getKillingParts());

        //when
        saveAndClearEntityManager();
        final Optional<Song> findSong = songRepository.findById(song.getId());

        //then
        assertThat(findSong).isPresent();
        assertThat(findSong.get()).isEqualTo(song);
    }

    @DisplayName("Song 을 저장할 때의 시간 정보로 createAt이 자동 생성된다.")
    @Test
    void createdAt_prePersist() {
        //given
        final Song song = createNewSongWithKillingParts();

        //when
        final LocalDateTime prev = LocalDateTime.now().truncatedTo(ChronoUnit.MICROS);
        final Song saved = saveSong(song);
        final LocalDateTime after = LocalDateTime.now().truncatedTo(ChronoUnit.MICROS);

        //then
        assertThat(song).isSameAs(saved);
        assertThat(song.getCreatedAt()).isBetween(prev, after);
    }

    @DisplayName("Song 을 KillingPart 의 총합 좋아요 수와 함께 조회한다.")
    @Test
    void findAllWithTotalLikeCount() {
        // given
        final Member firstMember = createAndSaveMember("first@naver.com", "first");
        final Member secondMember = createAndSaveMember("second@naver.com", "second");
        final Song firstSong = saveSong(createNewSongWithKillingParts());
        final Song secondSong = saveSong(createNewSongWithKillingParts());
        final Song thirdSong = saveSong(createNewSongWithKillingParts());

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
        final Song eleventhSong = saveSong(createNewSongWithKillingParts());
        final Song tenthSong = saveSong(createNewSongWithKillingParts());
        final Song ninthSong = saveSong(createNewSongWithKillingParts());
        final Song eighthSong = saveSong(createNewSongWithKillingParts());
        final Song seventhSong = saveSong(createNewSongWithKillingParts());
        final Song sixthSong = saveSong(createNewSongWithKillingParts());
        final Song fifthSong = saveSong(createNewSongWithKillingParts());
        final Song fourthSong = saveSong(createNewSongWithKillingParts());
        final Song thirdSong = saveSong(createNewSongWithKillingParts());
        final Song secondSong = saveSong(createNewSongWithKillingParts());
        final Song standardSong = saveSong(createNewSongWithKillingParts());

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
            .isEqualTo(List.of(
                    findSavedSong(secondSong),
                    findSavedSong(thirdSong),
                    findSavedSong(fourthSong),
                    findSavedSong(fifthSong),
                    findSavedSong(sixthSong),
                    findSavedSong(seventhSong),
                    findSavedSong(eighthSong),
                    findSavedSong(ninthSong),
                    findSavedSong(tenthSong),
                    findSavedSong(eleventhSong)
                )
            );
    }

    private Song findSavedSong(final Song song) {
        return songRepository.findById(song.getId()).get();
    }

    @DisplayName("주어진 id보다 좋아요가 적은 노래 10개를 조회한다. (데이터가 기준보다 적을 때)")
    @Test
    void findSongsWithLessLikeCountThanSongWithId_SmallData() {
        // given
        final Member firstMember = createAndSaveMember("first@naver.com", "first");

        final Song firstSong = saveSong(createNewSongWithKillingParts());
        final Song secondSong = saveSong(createNewSongWithKillingParts());
        final Song thirdSong = saveSong(createNewSongWithKillingParts());
        final Song standardSong = saveSong(createNewSongWithKillingParts());
        final Song fifthSong = saveSong(createNewSongWithKillingParts());

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
            .isEqualTo(List.of(findSavedSong(thirdSong)));
    }

    @DisplayName("주어진 id보다 좋아요가 많은 노래 10개를 총 좋아요 오름차순, id 오름차순으로 조회한다. (데이터가 충분할 때)")
    @Test
    void findSongsWithMoreLikeCountThanSongWithId() {
        // given
        final Member member = createAndSaveMember("first@naver.com", "first");
        final Song firstSong = saveSong(createNewSongWithKillingParts());
        final Song secondSong = saveSong(createNewSongWithKillingParts());
        final Song thirdSong = saveSong(createNewSongWithKillingParts());
        final Song fourthSong = saveSong(createNewSongWithKillingParts());
        final Song fifthSong = saveSong(createNewSongWithKillingParts());
        final Song standardSong = saveSong(createNewSongWithKillingParts());
        final Song seventhSong = saveSong(createNewSongWithKillingParts());
        final Song eighthSong = saveSong(createNewSongWithKillingParts());
        final Song ninthSong = saveSong(createNewSongWithKillingParts());
        final Song tenthSong = saveSong(createNewSongWithKillingParts());
        final Song eleventhSong = saveSong(createNewSongWithKillingParts());

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
            .ignoringFieldsOfTypes(LocalDateTime.class)
            .isEqualTo(List.of(
                findSavedSong(seventhSong),
                findSavedSong(eighthSong),
                findSavedSong(ninthSong),
                findSavedSong(tenthSong),
                findSavedSong(eleventhSong),
                findSavedSong(fourthSong),
                findSavedSong(fifthSong),
                findSavedSong(firstSong),
                findSavedSong(secondSong),
                findSavedSong(thirdSong)
            ));
    }

    @DisplayName("주어진 id보다 좋아요가 많은 노래 10개를 총 좋아요 오름차순, id 오름차순으로 조회한다. (데이터가 기준보다 부족할 때)")
    @Test
    void findSongsWithMoreLikeCountThanSongWithId_smallData() {
        // given
        final Member member = createAndSaveMember("first@naver.com", "first");
        final Song firstSong = saveSong(createNewSongWithKillingParts());
        final Song secondSong = saveSong(createNewSongWithKillingParts());
        final Song thirdSong = saveSong(createNewSongWithKillingParts());
        final Song fourthSong = saveSong(createNewSongWithKillingParts());
        final Song fifthSong = saveSong(createNewSongWithKillingParts());
        final Song standardSong = saveSong(createNewSongWithKillingParts());

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
            .isEqualTo(List.of(
                findSavedSong(fourthSong),
                findSavedSong(fifthSong),
                findSavedSong(firstSong),
                findSavedSong(secondSong),
                findSavedSong(thirdSong))
            );
    }
}
