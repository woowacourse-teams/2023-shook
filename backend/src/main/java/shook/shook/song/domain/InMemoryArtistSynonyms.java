package shook.shook.song.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BiPredicate;
import java.util.stream.Stream;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryArtistSynonyms {

    private Map<ArtistSynonym, Artist> artistsBySynonym = new HashMap<>();

    public void initialize(final Map<ArtistSynonym, Artist> artistsBySynonym) {
        this.artistsBySynonym = new HashMap<>(artistsBySynonym);
    }

    public List<Artist> findAllArtistsHavingSynonymStartsOrEndsWith(final String keyword) {
        return Stream.concat(
                findAllArtistsHavingSynonymStartsWith(keyword).stream(),
                findAllArtistsHavingSynonymEndsWith(keyword).stream())
            .distinct()
            .toList();
    }

    public List<Artist> findAllArtistsHavingSynonymStartsWith(final String keyword) {
        return filterBySynonymCondition(keyword, ArtistSynonym::startsWith);
    }

    private List<Artist> filterBySynonymCondition(final String keyword,
                                                  final BiPredicate<ArtistSynonym, String> filter) {
        return artistsBySynonym.entrySet().stream()
            .filter(entry -> filter.test(entry.getKey(), keyword))
            .map(Entry::getValue)
            .distinct()
            .toList();
    }

    private List<Artist> findAllArtistsHavingSynonymEndsWith(final String keyword) {
        return filterBySynonymCondition(keyword, ArtistSynonym::endsWith);
    }

    public List<Artist> findAllArtistsNameStartsOrEndsWith(final String keyword) {
        return Stream.concat(
                findAllArtistsNameStartsWith(keyword).stream(),
                findAllArtistsNameEndsWith(keyword).stream())
            .distinct()
            .toList();
    }

    public List<Artist> findAllArtistsNameStartsWith(final String keyword) {
        return filterByNameCondition(keyword, Artist::nameStartsWith);
    }

    private List<Artist> filterByNameCondition(final String keyword,
                                               final BiPredicate<Artist, String> filter) {
        return artistsBySynonym.values().stream()
            .filter(artist -> filter.test(artist, keyword))
            .distinct()
            .toList();
    }

    private List<Artist> findAllArtistsNameEndsWith(final String keyword) {
        return filterByNameCondition(keyword, Artist::nameEndsWith);
    }

    public Map<ArtistSynonym, Artist> getArtistsBySynonym() {
        return new HashMap<>(artistsBySynonym);
    }
}
