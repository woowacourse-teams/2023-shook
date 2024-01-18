package shook.shook.legacy.voting_song.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shook.shook.legacy.member.domain.Member;
import shook.shook.legacy.voting_song.domain.Vote;
import shook.shook.legacy.voting_song.domain.VotingSongPart;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

    boolean existsByMemberAndVotingSongPart(final Member member, final VotingSongPart part);
}
