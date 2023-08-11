package shook.shook.voting_song.domain.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shook.shook.voting_song.domain.VotingSong;
import shook.shook.voting_song.domain.VotingSongPart;

@Repository
public interface VotingSongPartRepository extends JpaRepository<VotingSongPart, Long> {

    List<VotingSongPart> findAllByVotingSong(final VotingSong song);
}
