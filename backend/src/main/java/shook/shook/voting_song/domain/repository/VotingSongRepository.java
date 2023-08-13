package shook.shook.voting_song.domain.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shook.shook.song.domain.SongTitle;
import shook.shook.voting_song.domain.VotingSong;

@Repository
public interface VotingSongRepository extends JpaRepository<VotingSong, Long> {

    Optional<VotingSong> findByTitle(final SongTitle title);

    List<VotingSong> findByIdLessThanOrderByIdDesc(final Long id, final Pageable page);

    List<VotingSong> findByIdGreaterThanOrderByIdAsc(final Long id, final Pageable page);
}
