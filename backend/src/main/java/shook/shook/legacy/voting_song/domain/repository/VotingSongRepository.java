package shook.shook.legacy.voting_song.domain.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shook.shook.legacy.song.domain.SongTitle;
import shook.shook.legacy.voting_song.domain.VotingSong;

@Repository
public interface VotingSongRepository extends JpaRepository<VotingSong, Long> {

    Optional<VotingSong> findByTitle(final SongTitle title);

    List<VotingSong> findByIdGreaterThanEqualAndIdLessThanEqual(final Long start, final Long end);
}
