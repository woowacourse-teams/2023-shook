package shook.shook.part.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shook.shook.part.domain.Vote;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

}
