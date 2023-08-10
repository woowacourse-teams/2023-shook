package shook.shook.song.domain.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shook.shook.song.domain.SongTitle;
import shook.shook.song.domain.voting_song.VotingSong;

@Repository
public interface VotingSongRepository extends JpaRepository<VotingSong, Long> {

    Optional<VotingSong> findByTitle(final SongTitle title);
}
