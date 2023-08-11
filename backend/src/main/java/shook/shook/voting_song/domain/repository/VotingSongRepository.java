package shook.shook.voting_song.domain.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shook.shook.song.domain.SongTitle;
import shook.shook.voting_song.domain.VotingSong;

@Repository
public interface VotingSongRepository extends JpaRepository<VotingSong, Long> {

    Optional<VotingSong> findByTitle(final SongTitle title);
}
