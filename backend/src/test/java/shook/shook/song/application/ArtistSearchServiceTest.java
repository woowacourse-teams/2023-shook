package shook.shook.song.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import shook.shook.member.domain.Member;
import shook.shook.member.domain.repository.MemberRepository;
import shook.shook.part.domain.PartLength;
import shook.shook.song.application.dto.ArtistResponse;
import shook.shook.song.application.dto.ArtistWithSongSearchResponse;
import shook.shook.song.domain.Artist;
import shook.shook.song.domain.ArtistSynonym;
import shook.shook.song.domain.Genre;
import shook.shook.song.domain.InMemoryArtistSynonyms;
import shook.shook.song.domain.KillingParts;
import shook.shook.song.domain.Song;
import shook.shook.song.domain.Synonym;
import shook.shook.song.domain.killingpart.KillingPart;
import shook.shook.song.domain.killingpart.KillingPartLike;
import shook.shook.song.domain.killingpart.repository.KillingPartLikeRepository;
import shook.shook.song.domain.killingpart.repository.KillingPartRepository;
import shook.shook.song.domain.repository.ArtistRepository;
import shook.shook.song.domain.repository.SongRepository;
import shook.shook.song.exception.ArtistException;
import shook.shook.support.UsingJpaTest;

@Sql("classpath:/killingpart/initialize_killing_part_song.sql")
class ArtistSearchServiceTest extends UsingJpaTest {

    private ArtistSearchService artistSearchService;
    private InMemoryArtistSynonyms artistSynonyms = new InMemoryArtistSynonyms();

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private ArtistRepository artistRepository;


    @Autowired
    private KillingPartLikeRepository likeRepository;

    @Autowired
    private KillingPartRepository killingPartRepository;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        artistSearchService = new ArtistSearchService(artistSynonyms, artistRepository,
            songRepository);
        final Song firstSong = songRepository.findById(1L).get();
        final Song secondSong = songRepository.findById(2L).get();
        final Song thirdSong = songRepository.findById(3L).get();
        final Song fourthSong = songRepository.findById(4L).get();
        final Member member = memberRepository.findById(1L).get();

        addLikeToEachKillingParts(secondSong, member);
        addLikeToEachKillingParts(thirdSong, member);

        initializeAllArtistsWithSynonyms();
    }

    private void addLikeToEachKillingParts(final Song song, final Member member) {
        for (final KillingPart killingPart : song.getKillingParts()) {
            final KillingPartLike like = new KillingPartLike(killingPart, member);
            killingPart.like(like);
            likeRepository.save(like);
        }
    }

    private void initializeAllArtistsWithSynonyms() {
        final Artist newJeans = artistRepository.findById(1L).get();
        final Artist 가수 = artistRepository.findById(2L).get();
        final Artist 정국 = artistRepository.findById(3L).get();

        artistSynonyms.initialize(
            Map.of(
                new ArtistSynonym(newJeans, new Synonym("뉴진스")), newJeans,
                new ArtistSynonym(가수, new Synonym("인기가수")), 가수,
                new ArtistSynonym(정국, new Synonym("방탄인기")), 정국
            )
        );
    }

    @DisplayName("동의어 또는 이름이 키워드로 시작하는 아티스트 목록을 가나다 순으로 정렬하여 검색한다.")
    @Test
    void searchArtistsByKeyword() {
        // given
        // when
        final List<ArtistResponse> artists = artistSearchService.searchArtistsByKeyword("인기");

        // then
        final Artist artist = artistRepository.findById(2L).get();

        assertThat(artists).usingRecursiveComparison()
            .isEqualTo(List.of(ArtistResponse.from(artist)));
    }

    @DisplayName("아티스 목록을 모두 조회할 때 키워드가 비어있다면 빈 결과를 반환한다.")
    @Test
    void searchArtistsByKeyword_emptyKeyword() {
        // given
        // when
        final List<ArtistResponse> artists = artistSearchService.searchArtistsByKeyword("  ");

        // then
        assertThat(artists).isEmpty();
    }

    @DisplayName("키워드로 시작하거나 끝나는 아티스트를 가나다 순으로 정렬, 해당 아티스트의 TOP 곡 목록을 모두 조회한다.")
    @ParameterizedTest
    @ValueSource(strings = {"국", "방탄"})
    void searchArtistsAndTopSongsByKeyword(final String keyword) {
        // given
        final Artist artist = artistRepository.findById(3L).get();
        final Song anotherSong1 = createNewSongWithArtist(artist);
        final Song anotherSong2 = createNewSongWithArtist(artist);
        final Song anotherSong3 = createNewSongWithArtist(artist);
        saveSong(anotherSong1);
        saveSong(anotherSong2);
        saveSong(anotherSong3);

        addLikeToEachKillingParts(anotherSong1, memberRepository.findById(1L).get());
        // 예상 TOP3: anotherSong1, anotherSong3, anotherSong2
        saveAndClearEntityManager();

        // when
        final List<ArtistWithSongSearchResponse> artistsWithSong = artistSearchService.searchArtistsAndTopSongsByKeyword(
            keyword);

        // then
        final Artist foundArtist = artistRepository.findById(3L).get();
        final Song expectedFirstSong = songRepository.findById(anotherSong1.getId()).get();
        final Song expectedThirdSong = songRepository.findById(anotherSong3.getId()).get();
        final Song expectedSecondSong = songRepository.findById(anotherSong2.getId()).get();

        assertThat(artistsWithSong).usingRecursiveComparison()
            .isEqualTo(List.of(ArtistWithSongSearchResponse.of(
                foundArtist,
                4,
                List.of(expectedFirstSong, expectedThirdSong, expectedSecondSong))
            ));
    }

    private void saveSong(final Song song) {
        songRepository.save(song);
        killingPartRepository.saveAll(song.getKillingParts());
    }

    private Song createNewSongWithArtist(final Artist artist) {
        final KillingPart firstKillingPart = KillingPart.forSave(10, PartLength.SHORT);
        final KillingPart secondKillingPart = KillingPart.forSave(15, PartLength.SHORT);
        final KillingPart thirdKillingPart = KillingPart.forSave(20, PartLength.SHORT);

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

    @DisplayName("아티스트, 해당 아티스트의 TOP 곡 목록을 모두 조회할 때 키워드가 비어있다면 빈 결과를 반환한다.")
    @Test
    void searchArtistsAndTopSongsByKeyword_emptyKeyword() {
        // given
        // when
        final List<ArtistWithSongSearchResponse> response = artistSearchService.searchArtistsAndTopSongsByKeyword(
            "  ");

        // then
        assertThat(response).isEmpty();
    }

    @DisplayName("아티스트의 모든 곡 목록을 좋아요 순으로 정렬하여 조회한다.")
    @Test
    void searchAllSongsByArtist() {
        // given
        final Artist artist = artistRepository.findById(3L).get();
        final Song anotherSong1 = createNewSongWithArtist(artist);
        final Song anotherSong2 = createNewSongWithArtist(artist);
        final Song anotherSong3 = createNewSongWithArtist(artist);
        saveSong(anotherSong1);
        saveSong(anotherSong2);
        saveSong(anotherSong3);

        addLikeToEachKillingParts(anotherSong1, memberRepository.findById(1L).get());
        // 예상 TOP3: anotherSong1, anotherSong3, anotherSong2, 4L Song
        saveAndClearEntityManager();

        // when
        final ArtistWithSongSearchResponse artistSongsResponse = artistSearchService.searchAllSongsByArtist(
            artist.getId());

        // then
        final Artist expectedArtist = artistRepository.findById(artist.getId()).get();
        final Song expectedFirstSong = songRepository.findById(anotherSong1.getId()).get();
        final Song expectedSecondSong = songRepository.findById(anotherSong3.getId()).get();
        final Song expectedThirdSong = songRepository.findById(anotherSong2.getId()).get();
        final Song expectedFourthSong = songRepository.findById(4L).get();

        assertThat(artistSongsResponse).usingRecursiveComparison()
            .isEqualTo(ArtistWithSongSearchResponse.of(
                expectedArtist,
                4,
                List.of(
                    expectedFirstSong,
                    expectedSecondSong,
                    expectedThirdSong,
                    expectedFourthSong)
            ));
    }

    @DisplayName("존재하지 않는 아티스트를 요청하면 예외가 발생한다.")
    @Test
    void searchAllSongsByArtist_artistNotExist() {
        // given
        final Long artistIdNotExist = Long.MAX_VALUE;

        // when, then
        assertThatThrownBy(() -> artistSearchService.searchAllSongsByArtist(artistIdNotExist))
            .isInstanceOf(ArtistException.NotExistException.class);
    }
}
