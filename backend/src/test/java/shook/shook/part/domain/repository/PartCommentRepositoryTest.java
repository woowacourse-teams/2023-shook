package shook.shook.part.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import shook.shook.part.domain.Part;
import shook.shook.part.domain.PartComment;
import shook.shook.part.domain.PartLength;
import shook.shook.song.domain.Song;
import shook.shook.song.domain.repository.SongRepository;
import shook.shook.support.UsingJpaTest;

class PartCommentRepositoryTest extends UsingJpaTest {

    private static Part SAVED_PART;

    @Autowired
    private PartRepository partRepository;

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private PartCommentRepository partCommentRepository;

    @BeforeEach
    void setUp() {
        Song SAVED_SONG = songRepository.save(new Song("제목", "비디오URL", "가수", 30));
        SAVED_PART = partRepository.save(Part.forSave(3, PartLength.SHORT, SAVED_SONG));
    }

    @DisplayName("PartReply 를 저장한다.")
    @Test
    void save() {
        //given
        final PartComment partComment = PartComment.forSave(SAVED_PART, "댓글 내용");

        //when
        final PartComment savedPartComment = partCommentRepository.save(partComment);

        //then
        assertThat(savedPartComment).isSameAs(partComment);
        assertThat(partComment.getId()).isNotNull();
    }

    @DisplayName("Part 을 저장할 때의 시간 정보로 createAt이 자동 생성된다.")
    @Test
    void createdAt() {
        //given
        final PartComment partComment = PartComment.forSave(SAVED_PART, "댓글 내용");

        //when
        final LocalDateTime prev = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
        final PartComment savedPartComment = partCommentRepository.save(partComment);
        final LocalDateTime after = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);

        //then
        assertThat(savedPartComment).isSameAs(partComment);
        assertThat(partComment.getCreatedAt()).isBetween(prev, after);
    }
}
