package shook.shook.voting_song.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import shook.shook.member.domain.Member;
import shook.shook.member.domain.repository.MemberRepository;
import shook.shook.part.domain.PartLength;
import shook.shook.support.UsingJpaTest;
import shook.shook.voting_song.domain.Vote;
import shook.shook.voting_song.domain.VotingSong;
import shook.shook.voting_song.domain.VotingSongPart;

@Sql("classpath:/killingpart/initialize_killing_part_song.sql")
class VoteRepositoryTest extends UsingJpaTest {

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private VotingSongRepository votingSongRepository;

    @Autowired
    private VotingSongPartRepository votingSongPartRepository;

    @DisplayName("투표중인 노래의 파트에 멤버의 투표가 존재하는지 반환한다.")
    @Test
    void existsByMemberAndVotingSongPart() {
        //given
        final Member member = memberRepository.findById(1L).get();
        final VotingSong votingSong = votingSongRepository.save(
            new VotingSong("제목1", "비디오ID는 11글자", "이미지URL", "가수", 20));
        final VotingSongPart votingSongPart = votingSongPartRepository.save(
            VotingSongPart.forSave(1, new PartLength(5), votingSong));
        voteRepository.save(Vote.forSave(member, votingSongPart));

        //when
        final boolean isVoteExist = voteRepository.existsByMemberAndVotingSongPart(member, votingSongPart);

        //then
        assertThat(isVoteExist).isTrue();
    }
}
