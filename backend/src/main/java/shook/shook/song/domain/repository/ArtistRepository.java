package shook.shook.song.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shook.shook.song.domain.Artist;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {

}
