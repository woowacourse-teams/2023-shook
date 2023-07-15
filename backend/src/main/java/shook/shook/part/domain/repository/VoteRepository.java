package shook.shook.part.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shook.shook.part.domain.Vote;

public interface VoteRepository extends JpaRepository<Vote, Long> {

}
