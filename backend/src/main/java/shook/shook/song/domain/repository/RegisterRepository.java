package shook.shook.song.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shook.shook.song.domain.voting_song.Register;

@Repository
public interface RegisterRepository extends JpaRepository<Register, Long> {
    // TODO: 2023/08/10 VoteRepository 으로 변경

}
