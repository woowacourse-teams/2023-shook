package shook.shook.song.domain.killingpart.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shook.shook.song.domain.Song;
import shook.shook.song.domain.killingpart.KillingPart;

@Repository
public interface KillingPartRepository extends JpaRepository<KillingPart, Long> {

    List<KillingPart> findAllBySong(final Song song);
}
