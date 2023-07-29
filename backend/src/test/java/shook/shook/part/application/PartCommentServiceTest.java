package shook.shook.part.application;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import shook.shook.part.application.dto.PartCommentRegisterRequest;
import shook.shook.part.application.dto.PartCommentResponse;
import shook.shook.part.domain.Part;
import shook.shook.part.domain.PartComment;
import shook.shook.part.domain.PartLength;
import shook.shook.part.domain.repository.PartCommentRepository;
import shook.shook.part.domain.repository.PartRepository;
import shook.shook.song.domain.Song;
import shook.shook.song.domain.repository.SongRepository;
import shook.shook.support.UsingJpaTest;

class PartCommentServiceTest extends UsingJpaTest {

    private static Part SAVED_PART;

    @Autowired
    private PartRepository partRepository;

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private PartCommentRepository partCommentRepository;

    private PartCommentService partCommentService;

    @BeforeEach
    void setUp() {
        final Song savedSong = songRepository.save(new Song("제목", "비디오URL", "가수", 30));
        SAVED_PART = partRepository.save(Part.forSave(3, PartLength.SHORT, savedSong));
        partCommentService = new PartCommentService(partRepository, partCommentRepository);
    }

    @DisplayName("파트의 댓글을 등록한다.")
    @Test
    void register() {
        //given
        final PartCommentRegisterRequest request = new PartCommentRegisterRequest("댓글 내용");

        //when
        partCommentService.register(SAVED_PART.getId(), request);
        saveAndClearEntityManager();

        //then
        final Part part = partRepository.findById(SAVED_PART.getId()).get();
        assertThat(part.getComments()).hasSize(1);
        assertThat(part.getComments().get(0).getContent()).isEqualTo("댓글 내용");
    }

    @DisplayName("파트의 모든 댓글을 조회하여 반환한다.")
    @Test
    void findPartReplies() {
        //given
        final PartComment early = partCommentRepository.save(PartComment.forSave(SAVED_PART, "1"));
        final PartComment late = partCommentRepository.save(PartComment.forSave(SAVED_PART, "2"));

        //when
        saveAndClearEntityManager();
        final List<PartCommentResponse> partReplies = partCommentService.findPartReplies(
            SAVED_PART.getId());

        //then
        assertThat(partReplies).usingRecursiveComparison().isEqualTo(List.of(late, early));
    }
}
