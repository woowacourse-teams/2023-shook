package shook.shook.legacy.song.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shook.shook.legacy.song.domain.ArtistSynonym;

@Repository
public interface ArtistSynonymRepository extends JpaRepository<ArtistSynonym, Long> {

}
