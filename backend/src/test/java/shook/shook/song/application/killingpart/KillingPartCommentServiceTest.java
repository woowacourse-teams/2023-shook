package shook.shook.song.application.killingpart;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import shook.shook.part.domain.PartLength;
import shook.shook.song.application.killingpart.dto.KillingPartCommentRegisterRequest;
import shook.shook.song.application.killingpart.dto.KillingPartCommentResponse;
import shook.shook.song.domain.Song;
import shook.shook.song.domain.killingpart.KillingPart;
import shook.shook.song.domain.killingpart.KillingPartComment;
import shook.shook.song.domain.killingpart.repository.KillingPartCommentRepository;
import shook.shook.song.domain.killingpart.repository.KillingPartRepository;
import shook.shook.song.domain.repository.SongRepository;
import shook.shook.support.UsingJpaTest;

class KillingPartCommentServiceTest extends UsingJpaTest {

    private static KillingPart SAVED_PART;

    @Autowired
    private KillingPartRepository killingPartRepository;

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private KillingPartCommentRepository killingPartCommentRepository;

    private KillingPartCommentService killingPartCommentService;

    @BeforeEach
    void setUp() {
        final Song savedSong = songRepository.save(new Song("제목", "비디오URL", "이미지URL", "가수", 30));
        SAVED_PART = killingPartRepository.save(
            KillingPart.forSave(3, PartLength.SHORT, savedSong));
        killingPartCommentService = new KillingPartCommentService(
            killingPartRepository,
            killingPartCommentRepository
        );
    }

    @DisplayName("파트의 댓글을 등록한다.")
    @Test
    void register() {
        //given
        final KillingPartCommentRegisterRequest request = new KillingPartCommentRegisterRequest(
            "댓글 내용");

        //when
        killingPartCommentService.register(SAVED_PART.getId(), request);
        saveAndClearEntityManager();

        //then
        final KillingPart part = killingPartRepository.findById(SAVED_PART.getId()).get();
        assertThat(part.getComments()).hasSize(1);
        assertThat(part.getComments().get(0).getContent()).isEqualTo("댓글 내용");
    }

    @DisplayName("파트의 모든 댓글을 조회하여 반환한다.")
    @Test
    void findPartComments() {
        //given
        final KillingPartComment early = killingPartCommentRepository.save(
            KillingPartComment.forSave(SAVED_PART, "1"));
        final KillingPartComment late = killingPartCommentRepository.save(
            KillingPartComment.forSave(SAVED_PART, "2"));

        //when
        saveAndClearEntityManager();
        final List<KillingPartCommentResponse> partReplies = killingPartCommentService.findKillingPartComments(
            SAVED_PART.getId());

        //then
        assertThat(partReplies).usingRecursiveComparison().isEqualTo(List.of(late, early));
    }
}
