package shook.shook.voting_song.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import shook.shook.part.domain.PartLength;
import shook.shook.support.UsingJpaTest;
import shook.shook.voting_song.application.dto.VotingSongPartRegisterRequest;
import shook.shook.voting_song.domain.Vote;
import shook.shook.voting_song.domain.VotingSong;
import shook.shook.voting_song.domain.VotingSongPart;
import shook.shook.voting_song.domain.repository.VoteRepository;
import shook.shook.voting_song.domain.repository.VotingSongPartRepository;
import shook.shook.voting_song.domain.repository.VotingSongRepository;
import shook.shook.voting_song.exception.VotingSongException;

class VotingSongPartServiceTest extends UsingJpaTest {

    private static VotingSong SAVED_SONG;

    @Autowired
    private VotingSongRepository votingSongRepository;

    @Autowired
    private VotingSongPartRepository votingSongPartRepository;

    @Autowired
    private VoteRepository voteRepository;

    private VotingSongPartService votingSongPartService;

    @BeforeEach
    void setUp() {
        votingSongPartService = new VotingSongPartService(
            votingSongRepository,
            votingSongPartRepository,
            voteRepository
        );
        SAVED_SONG =
            votingSongRepository.save(new VotingSong("노래제목", "비디오ID는 11글자", "이미지URL", "가수", 180));
    }

    void addPart(final VotingSong votingSong, final VotingSongPart votingSongPart) {
        votingSongPartRepository.save(votingSongPart);
        votingSong.addPart(votingSongPart);
    }

    void votePart(final VotingSongPart votingSongPart, final Vote vote) {
        voteRepository.save(vote);
        votingSongPart.vote(vote);
    }

    @DisplayName("파트 수집 중인 노래의 파트를 등록한다.")
    @Nested
    class VotePart {

        @DisplayName("등록 가능한 파트일 때 새로운 파트가 등록된다.")
        @Test
        void notRegistered() {
            //given
            final VotingSongPartRegisterRequest request = new VotingSongPartRegisterRequest(1, 10);

            //when
            votingSongPartService.register(SAVED_SONG.getId(), request);
            saveAndClearEntityManager();

            //then
            final List<VotingSongPart> votingSongs =
                votingSongPartRepository.findAllByVotingSong(SAVED_SONG);
            assertThat(votingSongs).hasSize(1);
            assertThat(votingSongs.get(0).getVoteCount()).isOne();
        }

        @DisplayName("이미 등록된 파트일 때 파트의 투표수가 1 증가한다.")
        @Test
        void registered() {
            //given
            final VotingSongPart votingSongPart =
                VotingSongPart.forSave(1, PartLength.SHORT, SAVED_SONG);
            addPart(SAVED_SONG, votingSongPart);

            final Vote vote = Vote.forSave(votingSongPart);
            votePart(votingSongPart, vote);

            final VotingSongPartRegisterRequest request = new VotingSongPartRegisterRequest(1, 5);

            //when
            votingSongPartService.register(SAVED_SONG.getId(), request);
            saveAndClearEntityManager();

            //then
            final List<VotingSongPart> findParts =
                votingSongPartRepository.findAllByVotingSong(SAVED_SONG);
            assertThat(findParts).hasSize(1);
            assertThat(findParts.get(0).getVoteCount()).isEqualTo(2);
        }

        @DisplayName("등록하려는 파트의 '파트 수집 중인 노래' 가 없을 때 예외가 발생한다.")
        @Test
        void songNotExist() {
            //given
            final VotingSongPartRegisterRequest request = new VotingSongPartRegisterRequest(1, 10);
            final long notExistSongId = 0L;

            //when
            //then
            assertThatThrownBy(() -> votingSongPartService.register(notExistSongId, request))
                .isInstanceOf(VotingSongException.VotingSongNotExistException.class);
        }
    }
}
