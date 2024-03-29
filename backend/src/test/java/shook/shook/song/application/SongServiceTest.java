package shook.shook.song.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import shook.shook.auth.ui.Authority;
import shook.shook.auth.ui.argumentresolver.MemberInfo;
import shook.shook.member.domain.Member;
import shook.shook.member.domain.repository.MemberRepository;
import shook.shook.member_part.domain.MemberPart;
import shook.shook.member_part.domain.repository.MemberPartRepository;
import shook.shook.song.application.dto.KillingPartRegisterRequest;
import shook.shook.song.application.dto.MemberPartResponse;
import shook.shook.song.application.dto.RecentSongCarouselResponse;
import shook.shook.song.application.dto.SongResponse;
import shook.shook.song.application.dto.SongSwipeResponse;
import shook.shook.song.application.dto.SongWithKillingPartsRegisterRequest;
import shook.shook.song.application.killingpart.dto.HighLikedSongResponse;
import shook.shook.song.domain.InMemorySongs;
import shook.shook.song.domain.Song;
import shook.shook.song.domain.killingpart.KillingPart;
import shook.shook.song.domain.killingpart.KillingPartLike;
import shook.shook.song.domain.killingpart.repository.KillingPartLikeRepository;
import shook.shook.song.domain.killingpart.repository.KillingPartRepository;
import shook.shook.song.domain.repository.ArtistRepository;
import shook.shook.song.domain.repository.SongRepository;
import shook.shook.song.exception.SongException;
import shook.shook.support.UsingJpaTest;

@Sql("classpath:/schema-test.sql")
class SongServiceTest extends UsingJpaTest {

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private KillingPartRepository killingPartRepository;

    @Autowired
    private KillingPartLikeRepository likeRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberPartRepository memberPartRepository;

    @Autowired
    private ArtistRepository artistRepository;

    private InMemorySongs inMemorySongs;

    private SongService songService;

    @BeforeEach
    public void setUp() {
        inMemorySongs = new InMemorySongs();
        songService = new SongService(
            songRepository,
            killingPartRepository,
            likeRepository,
            memberRepository,
            memberPartRepository,
            inMemorySongs,
            artistRepository,
            new SongDataExcelReader(" ", " ", " ")
        );
    }

    @DisplayName("Song 을 저장할 때, Song 과 KillingParts 가 함께 저장된다.")
    @Test
    void register() {
        // given
        final SongWithKillingPartsRegisterRequest request = new SongWithKillingPartsRegisterRequest(
            "title",
            "elevenVideo",
            "imageUrl",
            "singer",
            "image",
            300,
            "댄스",
            List.of(
                new KillingPartRegisterRequest(10, 5),
                new KillingPartRegisterRequest(15, 10),
                new KillingPartRegisterRequest(0, 10)
            )
        );

        // when
        long savedSongId = songService.register(request);
        saveAndClearEntityManager();

        //then
        final Song foundSong = songRepository.findById(savedSongId).get();

        assertAll(
            () -> assertThat(foundSong).isNotNull(),
            () -> assertThat(foundSong.getTitle()).isEqualTo("title"),
            () -> assertThat(foundSong.getVideoId()).isEqualTo("elevenVideo"),
            () -> assertThat(foundSong.getAlbumCoverUrl()).isEqualTo("imageUrl"),
            () -> assertThat(foundSong.getArtistName()).isEqualTo("singer"),
            () -> assertThat(foundSong.getCreatedAt()).isNotNull(),
            () -> assertThat(foundSong.getKillingParts()).hasSize(3)
        );
    }

    @DisplayName("로그인 된 사용자의 Id, 노래의 Id 로 존재하는 노래를 스와이프로 요청하면 좋아요 순으로 정렬된 킬링파트가 함께 조회된다.")
    @Test
    void findById_exist_login_member() {
        //given
        final Member member = createAndSaveMember("email@naver.com", "email");
        final Song song = registerNewSong("title");
        inMemorySongs.refreshSongs(List.of(song));
        addLikeToEachKillingParts(song, member);
        addMemberPartToSong(10, 5, song, member);

        //when
        saveAndClearEntityManager();
        final SongSwipeResponse response =
            songService.findSongByIdForFirstSwipe(song.getId(),
                                                  new MemberInfo(member.getId(), Authority.MEMBER));

        //then
        assertAll(
            () -> assertThat(response.getPrevSongs()).isEmpty(),
            () -> assertThat(response.getNextSongs()).isEmpty(),
            () -> assertThat(response.getCurrentSong().getKillingParts().get(0))
                .hasFieldOrPropertyWithValue("id",
                                             song.getLikeCountSortedKillingParts().get(0).getId())
                .hasFieldOrPropertyWithValue("rank", 1)
                .hasFieldOrPropertyWithValue("likeStatus", true),

            () -> assertThat(response.getCurrentSong().getKillingParts().get(1))
                .hasFieldOrPropertyWithValue("id",
                                             song.getLikeCountSortedKillingParts().get(1).getId())
                .hasFieldOrPropertyWithValue("rank", 2)
                .hasFieldOrPropertyWithValue("likeStatus", true),

            () -> assertThat(response.getCurrentSong().getKillingParts().get(2))
                .hasFieldOrPropertyWithValue("id",
                                             song.getLikeCountSortedKillingParts().get(2).getId())
                .hasFieldOrPropertyWithValue("rank", 3)
                .hasFieldOrPropertyWithValue("likeStatus", true),
            () -> assertThat(response.getCurrentSong().getMemberPart().getId()).isNotNull()
        );
    }

    private MemberPart addMemberPartToSong(final int startSecond, final int length, final Song song,
                                           final Member member) {
        return memberPartRepository.save(MemberPart.forSave(startSecond, length, song, member));
    }

    @DisplayName("로그인 되지 않은 사용자의 Id, 노래의 Id 로 존재하는 노래를 검색한다.")
    @Test
    void findById_exist_not_login_member() {
        //given
        final Song song = registerNewSong("title");
        inMemorySongs.refreshSongs(List.of(song));

        //when인
        saveAndClearEntityManager();
        final SongSwipeResponse response =
            songService.findSongByIdForFirstSwipe(song.getId(),
                                                  new MemberInfo(0L, Authority.ANONYMOUS));

        //then
        assertAll(
            () -> assertThat(response.getPrevSongs()).isEmpty(),
            () -> assertThat(response.getNextSongs()).isEmpty(),
            () -> assertThat(response.getCurrentSong().getKillingParts().get(0))
                .hasFieldOrPropertyWithValue("id",
                                             song.getLikeCountSortedKillingParts().get(0).getId())
                .hasFieldOrPropertyWithValue("rank", 1)
                .hasFieldOrPropertyWithValue("likeStatus", false),

            () -> assertThat(response.getCurrentSong().getKillingParts().get(1))
                .hasFieldOrPropertyWithValue("id",
                                             song.getLikeCountSortedKillingParts().get(1).getId())
                .hasFieldOrPropertyWithValue("rank", 2)
                .hasFieldOrPropertyWithValue("likeStatus", false),

            () -> assertThat(response.getCurrentSong().getKillingParts().get(2))
                .hasFieldOrPropertyWithValue("id",
                                             song.getLikeCountSortedKillingParts().get(2).getId())
                .hasFieldOrPropertyWithValue("rank", 3)
                .hasFieldOrPropertyWithValue("likeStatus", false),
            () -> assertThat(response.getCurrentSong().getMemberPart()).isNull()
        );
    }

    @DisplayName("존재하지 않는 id로 노래를 조회했을 때 예외가 발생한다.")
    @Test
    void findById_notExist() {
        //given
        final Member member = createAndSaveMember("email@naver.com", "email");
        inMemorySongs.refreshSongs(List.of());

        //when
        //then
        assertThatThrownBy(() -> songService.findSongByIdForFirstSwipe(
                               0L,
                               new MemberInfo(member.getId(), Authority.MEMBER)
                           )
        ).isInstanceOf(SongException.SongNotExistException.class);
    }

    @DisplayName("1. 총 좋아요 수가 많은 순서, 2. id가 높은 순서로 모든 Song 을 조회한다.")
    @Test
    void showHighLikedSongs() {
        // given
        final Song firstSong = registerNewSong("title1");
        final Song secondSong = registerNewSong("title2");
        final Song thirdSong = registerNewSong("title3");
        final Song fourthSong = registerNewSong("title4");

        final Member member1 = createAndSaveMember("first@naver.com", "first");
        final Member member2 = createAndSaveMember("second@naver.com", "second");

        addLikeToEachKillingParts(thirdSong, member1);
        addLikeToEachKillingParts(firstSong, member2);
        addLikeToEachKillingParts(thirdSong, member2);
        addLikeToEachKillingParts(fourthSong, member1);

        inMemorySongs.refreshSongs(songRepository.findAllWithKillingPartsAndLikes());
        saveAndClearEntityManager();

        // when
        final List<HighLikedSongResponse> result = songService.showHighLikedSongs();

        // then
        assertAll(
            () -> assertThat(result).hasSize(4),
            () -> assertThat(result.get(0))
                .hasFieldOrPropertyWithValue("id", thirdSong.getId())
                .hasFieldOrPropertyWithValue("totalLikeCount", 6L),
            () -> assertThat(result.get(1))
                .hasFieldOrPropertyWithValue("id", fourthSong.getId())
                .hasFieldOrPropertyWithValue("totalLikeCount", 3L),
            () -> assertThat(result.get(2))
                .hasFieldOrPropertyWithValue("id", firstSong.getId())
                .hasFieldOrPropertyWithValue("totalLikeCount", 3L),
            () -> assertThat(result.get(3))
                .hasFieldOrPropertyWithValue("id", secondSong.getId())
                .hasFieldOrPropertyWithValue("totalLikeCount", 0L)
        );
    }

    private Song registerNewSong(final String title) {
        final SongWithKillingPartsRegisterRequest request = new SongWithKillingPartsRegisterRequest(
            "title",
            "elevenVideo",
            "imageUrl",
            "singer",
            "image",
            300,
            "댄스",
            List.of(
                new KillingPartRegisterRequest(10, 5),
                new KillingPartRegisterRequest(15, 10),
                new KillingPartRegisterRequest(0, 10)
            )
        );

        long savedSongId = songService.register(request);

        return songRepository.findById(savedSongId).get();
    }

    private void addLikeToEachKillingParts(final Song song, final Member member) {
        for (final KillingPart killingPart : song.getKillingParts()) {
            final KillingPartLike like = new KillingPartLike(killingPart, member);
            killingPart.like(like);
            likeRepository.save(like);
        }
    }

    private Member createAndSaveMember(final String email, final String name) {
        final Member member = new Member(email, name);
        return memberRepository.save(member);
    }

    @DisplayName("노래 정보를 조회한다.")
    @Nested
    class findSongsForSwipe {

        @DisplayName("노래를 처음 조회할 때 현재 노래와 이전 / 이후 노래 리스트를 조회한다.")
        @Test
        void firstFindByMember() {
            // given
            final Song firstSong = registerNewSong("title1");
            final Song secondSong = registerNewSong("title2");
            final Song thirdSong = registerNewSong("title3");
            final Song fourthSong = registerNewSong("title4");
            final Song fifthSong = registerNewSong("title5");

            final Member member = createAndSaveMember("first@naver.com", "first");

            // 4, 3, 5, 2, 1
            addLikeToEachKillingParts(thirdSong, member);
            addLikeToEachKillingParts(fourthSong, member);

            // 1, 2, 3 노래에 memberPart 추가
            addMemberPartToSong(10, 5, firstSong, member);
            addMemberPartToSong(10, 5, secondSong, member);
            addMemberPartToSong(10, 5, thirdSong, member);
            addMemberPartToSong(10, 5, fourthSong, member);

            saveAndClearEntityManager();
            inMemorySongs.refreshSongs(songRepository.findAllWithKillingPartsAndLikes());

            // when
            final SongSwipeResponse result =
                songService.findSongByIdForFirstSwipe(fifthSong.getId(),
                                                      new MemberInfo(member.getId(), Authority.MEMBER));

            // then
            assertAll(
                () -> assertThat(result.getCurrentSong().getId()).isEqualTo(fifthSong.getId()),
                () -> assertThat(result.getPrevSongs()).hasSize(2),
                () -> assertThat(result.getNextSongs()).hasSize(2),
                () -> assertThat(result.getPrevSongs().stream()
                                     .map(SongResponse::getId)
                                     .toList()).usingRecursiveComparison().isEqualTo(List.of(4L, 3L)),
                () -> assertThat(result.getNextSongs().stream()
                                     .map(SongResponse::getId)
                                     .toList()).usingRecursiveComparison().isEqualTo(List.of(2L, 1L)),
                () -> assertThat(result.getCurrentSong().getMemberPart()).isNull(),
                () -> assertThat(result.getPrevSongs().stream()
                                     .map(songResponse -> songResponse.getMemberPart().getId())
                                     .toList())
                    .usingRecursiveComparison()
                    .isEqualTo(List.of(4L, 3L)),
                () -> assertThat(result.getNextSongs().stream()
                                     .map(songResponse -> songResponse.getMemberPart().getId())
                                     .toList())
                    .usingRecursiveComparison()
                    .isEqualTo(List.of(2L, 1L))
            );
        }

        @DisplayName("노래를 조회할 때 현재 노래가 존재하지 않는다면 예외를 반환한다.")
        @Test
        void firstFindByAnonymous() {
            // given
            final Member member = createAndSaveMember("first@naver.com", "first");
            final Long notExistSongId = Long.MAX_VALUE;

            saveAndClearEntityManager();

            // when
            // then
            assertThatThrownBy(
                () -> songService.findSongByIdForFirstSwipe(notExistSongId,
                                                            new MemberInfo(member.getId(), Authority.MEMBER)))
                .isInstanceOf(SongException.SongNotExistException.class);
            assertThatThrownBy(
                () -> songService.findSongByIdForBeforeSwipe(notExistSongId,
                                                             new MemberInfo(member.getId(), Authority.MEMBER)))
                .isInstanceOf(SongException.SongNotExistException.class);
            assertThatThrownBy(
                () -> songService.findSongByIdForAfterSwipe(notExistSongId,
                                                            new MemberInfo(member.getId(), Authority.MEMBER)))
                .isInstanceOf(SongException.SongNotExistException.class);
        }

        @DisplayName("이전 노래를 1. 좋아요 순 내림차순, 2. id 내림차순으로 조회한다.")
        @Test
        void findSongByIdForBeforeSwipe() {
            // given
            final Song firstSong = registerNewSong("title1");
            final Song secondSong = registerNewSong("title2");
            final Song standardSong = registerNewSong("title3");
            final Song fourthSong = registerNewSong("title4");
            final Song fifthSong = registerNewSong("title5");

            final Member member = createAndSaveMember("first@naver.com", "first");
            final Member member2 = createAndSaveMember("first@naver.com", "first");

            addLikeToEachKillingParts(secondSong, member);
            addLikeToEachKillingParts(secondSong, member2);
            addLikeToEachKillingParts(fourthSong, member2);
            addLikeToEachKillingParts(firstSong, member2);

            addMemberPartToSong(10, 5, firstSong, member);
            addMemberPartToSong(10, 5, secondSong, member);
            addMemberPartToSong(10, 5, standardSong, member);
            addMemberPartToSong(10, 5, fourthSong, member);
            addMemberPartToSong(10, 5, fifthSong, member);

            // 정렬 순서: 2L, 4L, 1L, 5L, 3L
            saveAndClearEntityManager();
            inMemorySongs.refreshSongs(songRepository.findAllWithKillingPartsAndLikes());

            // when
            final List<SongResponse> beforeResponses =
                songService.findSongByIdForBeforeSwipe(standardSong.getId(),
                                                       new MemberInfo(member.getId(), Authority.MEMBER));

            // then
            assertThat(beforeResponses.stream()
                           .map(SongResponse::getId)
                           .toList()).usingRecursiveComparison().isEqualTo(List.of(2L, 4L, 1L, 5L));
            assertThat(beforeResponses.stream()
                           .map(SongResponse::getMemberPart)
                           .map(MemberPartResponse::getId)
                           .toList()).usingRecursiveComparison().isEqualTo(List.of(2L, 4L, 1L, 5L));
        }

        @DisplayName("이후 노래를 1. 좋아요 순 내림차순, 2. id 내림차순으로 조회한다.")
        @Test
        void findSongByIdForAfterSwipe() {
            // given
            final Song firstSong = registerNewSong("title1");
            final Song secondSong = registerNewSong("title2");
            final Song thirdSong = registerNewSong("title3");
            final Song standardSong = registerNewSong("title4");
            final Song fifthSong = registerNewSong("title5");

            final Member member = createAndSaveMember("first@naver.com", "first");
            final Member member2 = createAndSaveMember("first@naver.com", "first");

            addLikeToEachKillingParts(secondSong, member);
            addLikeToEachKillingParts(secondSong, member2);
            addLikeToEachKillingParts(standardSong, member2);
            addLikeToEachKillingParts(firstSong, member2);

            addMemberPartToSong(10, 5, firstSong, member);
            addMemberPartToSong(10, 5, secondSong, member);
            addMemberPartToSong(10, 5, thirdSong, member);
            addMemberPartToSong(10, 5, standardSong, member);
            addMemberPartToSong(10, 5, fifthSong, member);

            // 정렬 순서: 2L, 4L, 1L, 5L, 3L
            saveAndClearEntityManager();
            inMemorySongs.refreshSongs(songRepository.findAllWithKillingPartsAndLikes());

            // when
            final List<SongResponse> afterResponses =
                songService.findSongByIdForAfterSwipe(standardSong.getId(),
                                                      new MemberInfo(member.getId(), Authority.MEMBER));

            // then
            assertThat(afterResponses.stream()
                           .map(SongResponse::getId)
                           .toList()).usingRecursiveComparison().isEqualTo(List.of(1L, 5L, 3L));
            assertThat(afterResponses.stream()
                           .map(SongResponse::getMemberPart)
                           .map(MemberPartResponse::getId)
                           .toList()).usingRecursiveComparison().isEqualTo(List.of(1L, 5L, 3L));
        }
    }

    @Nested
    class Genre {

        @DisplayName("장르별 노래를 조회한다.")
        @Test
        void findSongsByGenre() {
            // given
            final Song song1 = registerNewSong("노래1");
            final Song song2 = registerNewSong("노래2");
            final Song song3 = registerNewSong("노래3");
            final Song song4 = registerNewSong("노래4");
            final Song song5 = registerNewSong("노래5");

            final Member member = createAndSaveMember("first@naver.com", "first");
            final Member secondMember = createAndSaveMember("second@naver.com", "second");
            final Member thirdMember = createAndSaveMember("third@naver.com", "third");

            addLikeToEachKillingParts(song2, member);
            addLikeToEachKillingParts(song2, secondMember);
            addLikeToEachKillingParts(song2, thirdMember);
            addLikeToEachKillingParts(song1, member);
            addLikeToEachKillingParts(song1, secondMember);
            addLikeToEachKillingParts(song3, member);

            // 정렬 순서: 2L, 1L, 3L, 5L, 4L
            inMemorySongs.refreshSongs(songRepository.findAllWithKillingPartsAndLikes());
            saveAndClearEntityManager();

            // when
            final List<HighLikedSongResponse> response = songService.findSongsByGenre("DANCE");

            // then
            assertAll(
                () -> assertThat(response).hasSize(5),
                () -> assertThat(response.stream()
                                     .map(HighLikedSongResponse::getId).toList())
                    .containsExactly(2L, 1L, 3L, 5L, 4L)
            );
        }
    }

    @DisplayName("노래 id 로 노래 하나를 조회한다.")
    @Test
    void findSongById() {
        // given
        final Song song = registerNewSong("title");
        final Member member = createAndSaveMember("email@email.com", "nickname");
        addLikeToEachKillingParts(song, member);
        addMemberPartToSong(10, 5, song, member);
        saveAndClearEntityManager();
        inMemorySongs.refreshSongs(songRepository.findAllWithKillingPartsAndLikes());

        // when
        final SongResponse response = songService.findSongById(song.getId(),
                                                               new MemberInfo(member.getId(), Authority.MEMBER));

        // then
        assertAll(
            () -> assertThat(response.getId()).isEqualTo(song.getId()),
            () -> assertThat(response.getTitle()).isEqualTo(song.getTitle()),
            () -> assertThat(response.getAlbumCoverUrl()).isEqualTo(song.getAlbumCoverUrl()),
            () -> assertThat(response.getSinger()).isEqualTo(song.getArtistName()),
            () -> assertThat(response.getKillingParts()).hasSize(3),
            () -> assertThat(response.getKillingParts().get(0))
                .hasFieldOrPropertyWithValue("id",
                                             song.getLikeCountSortedKillingParts().get(0).getId())
                .hasFieldOrPropertyWithValue("rank", 1)
                .hasFieldOrPropertyWithValue("likeStatus", true),

            () -> assertThat(response.getKillingParts().get(1))
                .hasFieldOrPropertyWithValue("id",
                                             song.getLikeCountSortedKillingParts().get(1).getId())
                .hasFieldOrPropertyWithValue("rank", 2)
                .hasFieldOrPropertyWithValue("likeStatus", true),

            () -> assertThat(response.getKillingParts().get(2))
                .hasFieldOrPropertyWithValue("id",
                                             song.getLikeCountSortedKillingParts().get(2).getId())
                .hasFieldOrPropertyWithValue("rank", 3)
                .hasFieldOrPropertyWithValue("likeStatus", true),
            () -> assertThat(response.getMemberPart().getId()).isNotNull()
        );
    }

    @DisplayName("최근에 등록된 순으로 노래 5개를 조회한다.")
    @Test
    void findRecentRegisteredSongsForCarousel() {
        // given
        registerNewSong("노래1");
        registerNewSong("노래2");
        registerNewSong("노래3");
        registerNewSong("노래4");
        registerNewSong("노래5");
        registerNewSong("노래6");
        registerNewSong("노래7");

        saveAndClearEntityManager();

        // when
        final List<RecentSongCarouselResponse> songs = songService.findRecentRegisteredSongsForCarousel(5);

        // then
        assertThat(songs.stream()
                       .map(RecentSongCarouselResponse::getId)
                       .toList())
            .containsExactly(7L, 6L, 5L, 4L, 3L);
    }
}
