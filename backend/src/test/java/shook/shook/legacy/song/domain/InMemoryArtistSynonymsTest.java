package shook.shook.legacy.song.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import shook.shook.legacy.song.domain.repository.ArtistRepository;
import shook.shook.legacy.song.domain.repository.ArtistSynonymRepository;

@SpringBootTest
@Transactional
class InMemoryArtistSynonymsTest {

    private Artist artist1;
    private Artist artist2;
    private ArtistSynonym synonym1;
    private ArtistSynonym synonym2;

    @Autowired
    private ArtistSynonymRepository artistSynonymRepository;

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private InMemoryArtistSynonyms artistSynonyms;

    @Autowired
    private InMemoryArtistSynonymsGenerator generator;

    @BeforeEach
    void setUp() {
        artist1 = new Artist("image", "name1");
        artist2 = new Artist("image", "name2");
        artistRepository.saveAll(List.of(artist1, artist2));

        synonym1 = new ArtistSynonym(artist1, new Synonym("synonym1"));
        synonym2 = new ArtistSynonym(artist2, new Synonym("synonym2"));
        artistSynonymRepository.saveAll(List.of(synonym1, synonym2));
    }

    @DisplayName("InMemoryArtistSynonymsGenerator 에 의해 InMemoryArtistSynonyms 가 초기화된다.")
    @Test
    void generator_initialize() {
        // given
        // when
        generator.initialize();

        // then
        assertThat(artistSynonyms.getArtistsBySynonym()).containsExactlyInAnyOrderEntriesOf(
            Map.of(synonym1, artist1, synonym2, artist2)
        );
    }

    @DisplayName("입력된 값으로 시작하거나 끝나는 동의어를 가진 아티스트를 모두 찾는다.")
    @Test
    void findAllArtistsHavingSynonymStartsOrEndsWith() {
        // given
        final Artist newArtist = new Artist("image", "newName");
        final ArtistSynonym newSynonym = new ArtistSynonym(newArtist, new Synonym("newTestSy"));
        artistRepository.save(newArtist);
        artistSynonymRepository.save(newSynonym);

        generator.initialize();

        // when
        final List<Artist> result = artistSynonyms.findAllArtistsHavingSynonymStartsOrEndsWith(
            "sy");

        // then
        assertThat(result).containsExactlyInAnyOrder(artist1, artist2, newArtist);
    }

    @DisplayName("입력된 값으로 시작하는 동의어를 가진 아티스트를 모두 찾는다.")
    @Test
    void findAllArtistsHavingSynonymStartsWith() {
        // given
        final Artist newArtist = new Artist("image", "newName");
        final ArtistSynonym newSynonym = new ArtistSynonym(newArtist, new Synonym("newTestSy"));
        artistRepository.save(newArtist);
        artistSynonymRepository.save(newSynonym);

        generator.initialize();

        // when
        final List<Artist> result = artistSynonyms.findAllArtistsHavingSynonymStartsWith(
            "sy");

        // then
        assertThat(result).containsExactlyInAnyOrder(artist1, artist2);
    }

    @DisplayName("동의어 검색 시, 입력된 값이 비어있다면 빈 결과가 반환된다.")
    @Test
    void findAllArtistsHavingSynonymStartsOrEndsWith_emptyInput() {
        // given
        // when
        final List<Artist> result = artistSynonyms.findAllArtistsHavingSynonymStartsOrEndsWith(
            " ");

        // then
        assertThat(result).isEmpty();
    }

    @DisplayName("입력된 값으로 시작하거나 끝나는 이름을 가진 아티스트를 모두 찾는다.")
    @Test
    void findAllArtistsNameStartsOrEndsWith() {
        // given
        final Artist newArtist = new Artist("image", "newName");
        final ArtistSynonym newSynonym = new ArtistSynonym(newArtist, new Synonym("newSynonym"));
        artistRepository.save(newArtist);
        artistSynonymRepository.save(newSynonym);

        generator.initialize();

        // when
        final List<Artist> result = artistSynonyms.findAllArtistsNameStartsOrEndsWith(
            "name");

        // then
        assertThat(result).containsExactlyInAnyOrder(artist1, artist2, newArtist);
    }

    @DisplayName("입력된 값으로 시작하는 이름을 가진 아티스트를 모두 찾는다.")
    @Test
    void findAllArtistsNameStartsWith() {
        // given
        final Artist newArtist = new Artist("image", "newName");
        final ArtistSynonym newSynonym = new ArtistSynonym(newArtist, new Synonym("newSynonym"));
        artistRepository.save(newArtist);
        artistSynonymRepository.save(newSynonym);

        generator.initialize();

        // when
        final List<Artist> result = artistSynonyms.findAllArtistsNameStartsWith(
            "name");

        // then
        assertThat(result).containsExactlyInAnyOrder(artist1, artist2);
    }

    @DisplayName("가수명 검색 시, 입력된 값이 비어있다면 빈 결과가 반환된다.")
    @Test
    void findAllArtistsNameStartsOrEndsWith_emptyInput() {
        // given
        // when
        final List<Artist> result = artistSynonyms.findAllArtistsNameStartsOrEndsWith(
            " ");

        // then
        assertThat(result).isEmpty();
    }
}
