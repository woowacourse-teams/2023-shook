package shook.shook.part.domain.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import shook.shook.part.domain.Part;
import shook.shook.song.domain.Song;

public interface PartRepository extends JpaRepository<Part, Long> {

    List<Part> findAllBySong(final Song song);
}
