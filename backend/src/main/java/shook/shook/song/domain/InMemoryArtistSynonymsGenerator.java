package shook.shook.song.domain;

import jakarta.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import shook.shook.song.domain.repository.ArtistSynonymRepository;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
@Component
public class InMemoryArtistSynonymsGenerator {

    private final InMemoryArtistSynonyms artistSynonyms;
    private final ArtistSynonymRepository artistSynonymRepository;

    @PostConstruct
    public void initialize() {
        log.info("Initialize ArtistWithSynonym");
        final List<ArtistSynonym> synonymsWithArtist = artistSynonymRepository.findAll();
        final Map<ArtistSynonym, Artist> artistsBySynonym = new HashMap<>();
        for (final ArtistSynonym artistSynonym : synonymsWithArtist) {
            artistsBySynonym.put(artistSynonym, artistSynonym.getArtist());
        }
        artistSynonyms.initialize(artistsBySynonym);
    }
}
