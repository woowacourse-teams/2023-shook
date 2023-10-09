package shook.shook.song.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shook.shook.song.domain.Artist;

public interface ArtistRepository extends JpaRepository<Artist, Long> {

}
