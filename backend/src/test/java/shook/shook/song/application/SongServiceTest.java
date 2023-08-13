package shook.shook.song.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import shook.shook.member.domain.Member;
import shook.shook.member.domain.repository.MemberRepository;
import shook.shook.song.application.dto.KillingPartRegisterRequest;
import shook.shook.song.application.dto.SongResponse;
import shook.shook.song.application.dto.SongWithKillingPartsRegisterRequest;
import shook.shook.song.application.killingpart.dto.HighLikedSongResponse;
import shook.shook.song.domain.Song;
import shook.shook.song.domain.killingpart.KillingPart;
import shook.shook.song.domain.killingpart.KillingPartLike;
import shook.shook.song.domain.killingpart.repository.KillingPartLikeRepository;
import shook.shook.song.domain.killingpart.repository.KillingPartRepository;
import shook.shook.song.domain.repository.SongRepository;
import shook.shook.song.exception.SongException;
import shook.shook.support.UsingJpaTest;

@Sql("classpath:/song/drop_create_empty_schema.sql")
class SongServiceTest extends UsingJpaTest {

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private KillingPartRepository killingPartRepository;

    @Autowired
    private KillingPartLikeRepository likeRepository;

    @Autowired
    private MemberRepository memberRepository;

    private SongService songService;

    @BeforeEach
    void setUp() {
        songService = new SongService(songRepository, killingPartRepository);
    }

    @DisplayName("Song 을 저장할 때, Song 과 KillingParts 가 함께 저장된다.")
    @Test
    void register() {
        // given
        final SongWithKillingPartsRegisterRequest request = new SongWithKillingPartsRegisterRequest(
            "title", "videoUrl", "imageUrl", "singer", 300,
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
            () -> assertThat(foundSong.getVideoUrl()).isEqualTo("videoUrl"),
            () -> assertThat(foundSong.getAlbumCoverUrl()).isEqualTo("imageUrl"),
            () -> assertThat(foundSong.getSinger()).isEqualTo("singer"),
            () -> assertThat(foundSong.getCreatedAt()).isNotNull(),
            () -> assertThat(foundSong.getKillingParts()).hasSize(3)
        );
    }

    @DisplayName("Id로 노래를 조회한다.(존재할 때)")
    @Test
    void findById_exist() {
        //given
        final Song song = registerNewSong();

        //when
        saveAndClearEntityManager();
        final SongResponse response = songService.findById(song.getId());

        //then
        assertThat(response).usingRecursiveComparison().isEqualTo(SongResponse.from(song));
    }

    @DisplayName("존재하지 않는 id로 노래를 조회했을 때 예외가 발생한다.")
    @Test
    void findById_notExist() {
        //given
        //when
        //then
        assertThatThrownBy(() -> songService.findById(0L))
            .isInstanceOf(SongException.SongNotExistException.class);
    }

    @DisplayName("1. 총 좋아요 수가 많은 순서, 2. id가 높은 순서로 모든 Song 을 조회한다.")
    @Test
    void showHighLikedSongs() {
        // given
        final Song firstSong = registerNewSong();
        final Song secondSong = registerNewSong();
        final Song thirdSong = registerNewSong();

        addLikeToEachKillingParts(secondSong, "firstUser");
        addLikeToEachKillingParts(firstSong, "secondUser");

        // when
        final List<HighLikedSongResponse> result = songService.showHighLikedSongs();

        // then
        assertAll(
            () -> assertThat(result).hasSize(3),
            () -> assertThat(result.get(0))
                .hasFieldOrPropertyWithValue("id", secondSong.getId())
                .hasFieldOrPropertyWithValue("totalLikeCount", 3L),
            () -> assertThat(result.get(1))
                .hasFieldOrPropertyWithValue("id", firstSong.getId())
                .hasFieldOrPropertyWithValue("totalLikeCount", 3L),
            () -> assertThat(result.get(2))
                .hasFieldOrPropertyWithValue("id", thirdSong.getId())
                .hasFieldOrPropertyWithValue("totalLikeCount", 0L)
        );
    }

    private Song registerNewSong() {
        final SongWithKillingPartsRegisterRequest request = new SongWithKillingPartsRegisterRequest(
            "title", "videoUrl", "imageUrl", "singer", 300,
            List.of(
                new KillingPartRegisterRequest(10, 5),
                new KillingPartRegisterRequest(15, 10),
                new KillingPartRegisterRequest(0, 10)
            )
        );

        long savedSongId = songService.register(request);

        return songRepository.findById(savedSongId).get();
    }

    private void addLikeToEachKillingParts(final Song song, final String nickname) {
        final Member member = createAndSaveMember(nickname + "@naver.com", nickname);
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
}
