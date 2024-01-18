package shook.shook.legacy.voting_song.domain.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shook.shook.legacy.part.domain.PartLength;
import shook.shook.legacy.voting_song.domain.VotingSong;
import shook.shook.legacy.voting_song.domain.VotingSongPart;

@Repository
public interface VotingSongPartRepository extends JpaRepository<VotingSongPart, Long> {

    List<VotingSongPart> findAllByVotingSong(final VotingSong song);

    Optional<VotingSongPart> findByVotingSongAndStartSecondAndLength(final VotingSong votingSong,
                                                                     final int startSecond, final PartLength length);
}
