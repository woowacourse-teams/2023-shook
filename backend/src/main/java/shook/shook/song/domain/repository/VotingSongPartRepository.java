package shook.shook.song.domain.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shook.shook.song.domain.voting_song.VotingSong;
import shook.shook.song.domain.voting_song.VotingSongPart;

@Repository
public interface VotingSongPartRepository extends JpaRepository<VotingSongPart, Long> {

    List<VotingSongPart> findAllByVotingSong(final VotingSong song);
}
