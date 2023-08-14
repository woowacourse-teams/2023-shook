package shook.shook.song.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shook.shook.song.domain.Song;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {

}
