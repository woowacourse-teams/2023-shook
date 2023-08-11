package shook.shook.voting_song.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shook.shook.voting_song.domain.Vote;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

}
