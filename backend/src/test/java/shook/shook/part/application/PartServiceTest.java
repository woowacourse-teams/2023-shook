package shook.shook.part.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import shook.shook.part.application.dto.PartRegisterRequest;
import shook.shook.part.domain.Part;
import shook.shook.part.domain.PartLength;
import shook.shook.part.domain.Vote;
import shook.shook.part.domain.repository.PartRepository;
import shook.shook.part.domain.repository.VoteRepository;
import shook.shook.song.domain.Song;
import shook.shook.song.domain.repository.SongRepository;
import shook.shook.song.exception.SongException;
import shook.shook.support.UsingJpaTest;

class PartServiceTest extends UsingJpaTest {

    private static final long NOT_EXIST_SONG_ID = 0L;
    private static Song SAVED_SONG;

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private PartRepository partRepository;

    @Autowired
    private VoteRepository voteRepository;

    private PartService partService;

    @BeforeEach
    void setUp() {
        partService = new PartService(songRepository, partRepository, voteRepository);
        SAVED_SONG = songRepository.save(new Song("노래제목", "비디오URL", "가수", 180));
    }

    void addPart(final Song song, final Part part) {
        song.addPart(part);
        partRepository.save(part);
    }

    void votePart(final Part part, final Vote vote) {
        part.vote(vote);
        voteRepository.save(vote);
    }

    @DisplayName("노래의 파트를 등록한다.")
    @Nested
    class Register {

        @DisplayName("등록 가능한 파트일 때 새로운 파트가 등록된다.")
        @Test
        void notRegistered() {
            //given
            final PartRegisterRequest request = new PartRegisterRequest(1, 10, SAVED_SONG.getId());

            //when
            partService.register(request);
            saveAndClearEntityManager();

            //then
            final List<Part> findParts = partRepository.findAllBySong(SAVED_SONG);
            assertThat(findParts).hasSize(1);
            assertThat(findParts.get(0).getVoteCount()).isOne();
        }

        @DisplayName("이미 등록된 파트일 때 파트의 투표수가 1 증가한다.")
        @Test
        void registered() {
            //given
            final Part part = Part.forSave(1, PartLength.SHORT, SAVED_SONG);
            addPart(SAVED_SONG, part);

            final Vote vote = Vote.forSave(part);
            votePart(part, vote);

            final PartRegisterRequest request = new PartRegisterRequest(1, 5, SAVED_SONG.getId());

            //when
            partService.register(request);
            saveAndClearEntityManager();

            //then
            final List<Part> findParts = partRepository.findAllBySong(SAVED_SONG);
            assertThat(findParts).hasSize(1);
            assertThat(findParts.get(0).getVoteCount()).isEqualTo(2);
        }

        @DisplayName("등록하려는 파트의 노래가 없을 때 예외가 발생한다.")
        @Test
        void songNotExist() {
            //given
            final PartRegisterRequest request = new PartRegisterRequest(1, 10, NOT_EXIST_SONG_ID);

            //when
            //then
            assertThatThrownBy(() -> partService.register(request))
                .isInstanceOf(SongException.SongNotExistException.class);
        }
    }
}