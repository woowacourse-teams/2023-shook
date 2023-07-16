package shook.shook.part.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import jakarta.persistence.EntityManager;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import shook.shook.part.application.dto.KillingPartResponse;
import shook.shook.part.application.dto.KillingPartsResponse;
import shook.shook.part.application.dto.PartRegisterRequest;
import shook.shook.part.domain.Part;
import shook.shook.part.domain.PartLength;
import shook.shook.part.domain.Vote;
import shook.shook.part.domain.repository.PartRepository;
import shook.shook.part.domain.repository.VoteRepository;
import shook.shook.song.domain.Song;
import shook.shook.song.domain.repository.SongRepository;
import shook.shook.song.exception.SongException;

@DataJpaTest
class PartServiceTest {

    private static Song SAVED_SONG;

    @Autowired
    private EntityManager entityManager;

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

    void saveAndClearEntityManager() {
        entityManager.flush();
        entityManager.clear();
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

        @DisplayName("등록 가능한 파트일 때")
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

        @DisplayName("이미 등록된 파트일 때")
        @Test
        void registered() {
            //given
            final Part part = Part.notPersisted(1, PartLength.SHORT, SAVED_SONG);
            addPart(SAVED_SONG, part);

            final Vote vote = Vote.notPersisted(part);
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

        @DisplayName("등록하려는 노래가 없을 때")
        @Test
        void songNotExist() {
            //given
            final PartRegisterRequest request = new PartRegisterRequest(1, 10, 0L);

            //when
            //then
            assertThatThrownBy(() -> partService.register(request))
                .isInstanceOf(SongException.SongNotExistException.class);
        }
    }

    @DisplayName("노래의 가장 인기있는 킬링파트를 보여준다.")
    @Nested
    class ShowTopKillingPart {

        @DisplayName("킬링파트가 존재할 때")
        @Test
        void exist() {
            //given
            final Part firstPart = Part.notPersisted(1, PartLength.SHORT, SAVED_SONG);
            final Part secondPart = Part.notPersisted(2, PartLength.SHORT, SAVED_SONG);
            addPart(SAVED_SONG, firstPart);
            addPart(SAVED_SONG, secondPart);

            final Vote firstVote = Vote.notPersisted(firstPart);
            final Vote secondVote = Vote.notPersisted(secondPart);
            final Vote thirdVote = Vote.notPersisted(secondPart);
            votePart(firstPart, firstVote);
            votePart(secondPart, secondVote);
            votePart(secondPart, thirdVote);

            //when
            saveAndClearEntityManager();
            final KillingPartResponse response = partService.showTopKillingPart(SAVED_SONG.getId());

            //then
            assertThat(response.getStart()).isEqualTo(2);
            assertThat(response.getEnd()).isEqualTo(7);
        }

        @DisplayName("킬링파트가 없을 때")
        @Test
        void notExist() {
            //given
            //when
            final KillingPartResponse response = partService.showTopKillingPart(SAVED_SONG.getId());

            //then
            assertThat(response).usingRecursiveComparison().isEqualTo(KillingPartResponse.empty());
        }

        @DisplayName("조회하려는 노래가 없을 때")
        @Test
        void songNotExist() {
            //given
            //when
            //then
            assertThatThrownBy(() -> partService.showTopKillingPart(0L))
                .isInstanceOf(SongException.SongNotExistException.class);
        }
    }

    @DisplayName("노래의 킬링파트들을 보여준다.")
    @Nested
    class ShowKillingParts {

        @DisplayName("킬링파트가 충분히 존재할 때")
        @Test
        void existEnough() {
            //given
            final Part firstPart = Part.notPersisted(1, PartLength.SHORT, SAVED_SONG);
            final Part secondPart = Part.notPersisted(2, PartLength.SHORT, SAVED_SONG);
            final Part thirdPart = Part.notPersisted(3, PartLength.SHORT, SAVED_SONG);
            final Part fourthPart = Part.notPersisted(4, PartLength.SHORT, SAVED_SONG);
            addPart(SAVED_SONG, firstPart);
            addPart(SAVED_SONG, secondPart);
            addPart(SAVED_SONG, thirdPart);
            addPart(SAVED_SONG, fourthPart);

            final Vote firstVote = Vote.notPersisted(firstPart);
            final Vote secondVote = Vote.notPersisted(secondPart);
            final Vote thirdVote = Vote.notPersisted(secondPart);
            final Vote fourthVote = Vote.notPersisted(secondPart);
            final Vote fifthVote = Vote.notPersisted(thirdPart);
            final Vote sixthVote = Vote.notPersisted(thirdPart);
            votePart(firstPart, firstVote);
            votePart(secondPart, secondVote);
            votePart(secondPart, thirdVote);
            votePart(secondPart, fourthVote);
            votePart(thirdPart, fifthVote);
            votePart(thirdPart, sixthVote);

            //when
            saveAndClearEntityManager();
            final KillingPartsResponse response = partService.showKillingParts(SAVED_SONG.getId());

            //then
            assertThat(response.getResponses()).usingRecursiveComparison()
                .isEqualTo(List.of(
                    KillingPartResponse.of(2, 7),
                    KillingPartResponse.of(3, 8),
                    KillingPartResponse.of(1, 6)
                ));
        }

        @DisplayName("킬링파트가 충분하지 않을 때 ( 2개일 때 )")
        @Test
        void exist_notEnough_two() {
            //given
            final Part firstPart = Part.notPersisted(1, PartLength.SHORT, SAVED_SONG);
            final Part secondPart = Part.notPersisted(2, PartLength.SHORT, SAVED_SONG);
            addPart(SAVED_SONG, firstPart);
            addPart(SAVED_SONG, secondPart);

            final Vote firstVote = Vote.notPersisted(firstPart);
            final Vote secondVote = Vote.notPersisted(secondPart);
            final Vote thirdVote = Vote.notPersisted(secondPart);
            votePart(firstPart, firstVote);
            votePart(secondPart, secondVote);
            votePart(secondPart, thirdVote);

            //when
            saveAndClearEntityManager();
            final KillingPartsResponse response = partService.showKillingParts(SAVED_SONG.getId());

            //then
            assertThat(response.getResponses()).usingRecursiveComparison()
                .isEqualTo(List.of(KillingPartResponse.of(2, 7), KillingPartResponse.of(1, 6)));
        }

        @DisplayName("킬링파트가 충분하지 않을 때 ( 1개일 때 )")
        @Test
        void exist_notEnough_one() {
            //given
            final Part firstPart = Part.notPersisted(1, PartLength.SHORT, SAVED_SONG);
            addPart(SAVED_SONG, firstPart);

            final Vote vote = Vote.notPersisted(firstPart);
            votePart(firstPart, vote);

            //when
            saveAndClearEntityManager();
            final KillingPartsResponse response = partService.showKillingParts(SAVED_SONG.getId());

            //then
            assertThat(response.getResponses()).usingRecursiveComparison()
                .isEqualTo(List.of(KillingPartResponse.of(1, 6)));
        }

        @DisplayName("킬링파트가 없을 때")
        @Test
        void notExist() {
            //given
            //when
            final KillingPartsResponse response = partService.showKillingParts(SAVED_SONG.getId());

            //then
            assertThat(response.getResponses()).isEmpty();
        }

        @DisplayName("조회할 노래가 없을 때")
        @Test
        void songNotExist() {
            //given
            //when
            //then
            assertThatThrownBy(() -> partService.showKillingParts(0L))
                .isInstanceOf(SongException.SongNotExistException.class);
        }
    }
}
