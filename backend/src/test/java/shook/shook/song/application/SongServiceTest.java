package shook.shook.song.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import shook.shook.part.domain.Part;
import shook.shook.part.domain.PartLength;
import shook.shook.part.domain.Vote;
import shook.shook.part.domain.repository.PartRepository;
import shook.shook.part.domain.repository.VoteRepository;
import shook.shook.song.application.dto.KillingPartResponse;
import shook.shook.song.application.dto.KillingPartsResponse;
import shook.shook.song.application.dto.SongRegisterRequest;
import shook.shook.song.application.dto.SongResponse;
import shook.shook.song.domain.Song;
import shook.shook.song.domain.SongTitle;
import shook.shook.song.domain.repository.SongRepository;
import shook.shook.song.exception.SongException;
import shook.shook.support.UsingJpaTest;

class SongServiceTest extends UsingJpaTest {

    private static Song SAVED_SONG;

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private PartRepository partRepository;

    @Autowired
    private VoteRepository voteRepository;

    private SongService songService;

    @BeforeEach
    void setUp() {
        songService = new SongService(songRepository);
        SAVED_SONG = songRepository.save(new Song("노래제목", "비디오URL", "가수", 180));
    }

    void addPart(final Song song, final Part part) {
        partRepository.save(part);
        song.addPart(part);
    }

    void votePart(final Part part, final Vote vote) {
        voteRepository.save(vote);
        part.vote(vote);
    }

    @DisplayName("노래를 등록한다.")
    @Test
    void register() {
        //given
        final SongRegisterRequest request = new SongRegisterRequest("새로운노래제목", "비디오URL", "가수", 180);

        //when
        songService.register(request);
        saveAndClearEntityManager();

        //then
        final Song savedSong = songRepository.findByTitle(new SongTitle("새로운노래제목")).get();
        assertAll(
            () -> assertThat(savedSong.getId()).isNotNull(),
            () -> assertThat(savedSong.getCreatedAt()).isNotNull(),
            () -> assertThat(savedSong.getTitle()).isEqualTo("새로운노래제목"),
            () -> assertThat(savedSong.getVideoUrl()).isEqualTo("비디오URL"),
            () -> assertThat(savedSong.getSinger()).isEqualTo("가수"),
            () -> assertThat(savedSong.getLength()).isEqualTo(180)
        );
    }

    @DisplayName("Id로 노래를 조회한다.(존재할 때)")
    @Test
    void findById_exist() {
        //given
        //when
        saveAndClearEntityManager();
        final SongResponse response = songService.findById(SAVED_SONG.getId());

        //then
        assertThat(response).usingRecursiveComparison().isEqualTo(SongResponse.from(SAVED_SONG));
    }

    @DisplayName("존재하지 않는 id로 노래를 조회했을 때 예외가 발생한다.")
    @Test
    void findById_notExist() {
        //given
        //when
        //then
        assertThatThrownBy(() -> songService.findById(0L))
            .isInstanceOf(SongException.SongNotExistException.class);
    }

    @DisplayName("제목으로 노래를 조회한다.(존재할 때)")
    @Test
    void findByTitle_exist() {
        //given
        //when
        saveAndClearEntityManager();
        final SongResponse response = songService.findByTitle(SAVED_SONG.getTitle());

        //then
        assertThat(response).usingRecursiveComparison().isEqualTo(SongResponse.from(SAVED_SONG));
    }

    @DisplayName("존재하지 않는 제목으로 노래를 조회했을 때 예외가 발생한다.")
    @Test
    void findByTitle() {
        //given
        //when
        //then
        assertThatThrownBy(() -> songService.findByTitle("없는제목"))
            .isInstanceOf(SongException.SongNotExistException.class);
    }

    @DisplayName("노래의 가장 인기있는 킬링파트를 보여준다.")
    @Nested
    class ShowTopKillingPart {

        @DisplayName("킬링파트가 존재할 때")
        @Test
        void exist() {
            //given
            final Part firstPart = Part.forSave(1, PartLength.SHORT, SAVED_SONG);
            final Part secondPart = Part.forSave(2, PartLength.SHORT, SAVED_SONG);
            addPart(SAVED_SONG, firstPart);
            addPart(SAVED_SONG, secondPart);

            final Vote firstVote = Vote.forSave(firstPart);
            final Vote secondVote = Vote.forSave(secondPart);
            final Vote thirdVote = Vote.forSave(secondPart);
            votePart(firstPart, firstVote);
            votePart(secondPart, secondVote);
            votePart(secondPart, thirdVote);

            //when
            saveAndClearEntityManager();
            final KillingPartResponse response = songService.showTopKillingPart(SAVED_SONG.getId());

            //then
            assertThat(response.getStart()).isEqualTo(2);
            assertThat(response.getEnd()).isEqualTo(7);
        }

        @DisplayName("킬링파트가 없을 때")
        @Test
        void notExist() {
            //given
            //when
            final KillingPartResponse response = songService.showTopKillingPart(SAVED_SONG.getId());

            //then
            assertThat(response).usingRecursiveComparison().isEqualTo(KillingPartResponse.empty());
        }

        @DisplayName("조회하려는 노래가 없을 때 예외가 발생한다.")
        @Test
        void songNotExist() {
            //given
            //when
            //then
            assertThatThrownBy(() -> songService.showTopKillingPart(0L))
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
            final Part firstPart = Part.forSave(1, PartLength.SHORT, SAVED_SONG);
            final Part secondPart = Part.forSave(2, PartLength.SHORT, SAVED_SONG);
            final Part thirdPart = Part.forSave(3, PartLength.SHORT, SAVED_SONG);
            final Part fourthPart = Part.forSave(4, PartLength.SHORT, SAVED_SONG);
            addPart(SAVED_SONG, firstPart);
            addPart(SAVED_SONG, secondPart);
            addPart(SAVED_SONG, thirdPart);
            addPart(SAVED_SONG, fourthPart);

            final Vote firstVote = Vote.forSave(firstPart);
            final Vote secondVote = Vote.forSave(secondPart);
            final Vote thirdVote = Vote.forSave(secondPart);
            final Vote fourthVote = Vote.forSave(secondPart);
            final Vote fifthVote = Vote.forSave(thirdPart);
            final Vote sixthVote = Vote.forSave(thirdPart);
            votePart(firstPart, firstVote);
            votePart(secondPart, secondVote);
            votePart(secondPart, thirdVote);
            votePart(secondPart, fourthVote);
            votePart(thirdPart, fifthVote);
            votePart(thirdPart, sixthVote);

            //when
            saveAndClearEntityManager();
            final KillingPartsResponse response = songService.showKillingParts(SAVED_SONG.getId());

            //then
            assertThat(response.getResponses()).usingRecursiveComparison()
                .isEqualTo(List.of(
                    KillingPartResponse.of(2, 7),
                    KillingPartResponse.of(3, 8),
                    KillingPartResponse.of(1, 6)
                ));
        }

        @DisplayName("킬링파트가 충분하지 않을 때")
        @Test
        void exist_notEnough_two() {
            //given
            final Part firstPart = Part.forSave(1, PartLength.SHORT, SAVED_SONG);
            final Part secondPart = Part.forSave(2, PartLength.SHORT, SAVED_SONG);
            addPart(SAVED_SONG, firstPart);
            addPart(SAVED_SONG, secondPart);

            final Vote firstVote = Vote.forSave(firstPart);
            final Vote secondVote = Vote.forSave(secondPart);
            final Vote thirdVote = Vote.forSave(secondPart);
            votePart(firstPart, firstVote);
            votePart(secondPart, secondVote);
            votePart(secondPart, thirdVote);

            //when
            saveAndClearEntityManager();
            final KillingPartsResponse response = songService.showKillingParts(SAVED_SONG.getId());

            //then
            assertThat(response.getResponses()).usingRecursiveComparison()
                .isEqualTo(List.of(KillingPartResponse.of(2, 7), KillingPartResponse.of(1, 6)));
        }

        @DisplayName("킬링파트가 없을 때")
        @Test
        void notExist() {
            //given
            //when
            final KillingPartsResponse response = songService.showKillingParts(SAVED_SONG.getId());

            //then
            assertThat(response.getResponses()).isEmpty();
        }

        @DisplayName("조회할 노래가 없을 때 예외가 발생한다.")
        @Test
        void songNotExist() {
            //given
            //when
            //then
            assertThatThrownBy(() -> songService.showKillingParts(0L))
                .isInstanceOf(SongException.SongNotExistException.class);
        }
    }
}
