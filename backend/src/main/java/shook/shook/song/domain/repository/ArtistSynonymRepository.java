package shook.shook.song.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shook.shook.song.domain.ArtistSynonym;

@Repository
public interface ArtistSynonymRepository extends JpaRepository<ArtistSynonym, Long> {

}
