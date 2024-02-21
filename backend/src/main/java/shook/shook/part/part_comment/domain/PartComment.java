package shook.shook.part.part_comment.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "part_comment")
@Entity
public class PartComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private PartCommentContent content;

    @Column(name = "part_id", nullable = false, updatable = false)
    private Long partId;

    @Column(name = "member_id", nullable = false, updatable = false)
    private Long memberId;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now().truncatedTo(ChronoUnit.MICROS);

    private PartComment(final Long id, final String content, final Long partId, final Long memberId) {
        this.id = id;
        this.content = new PartCommentContent(content);
        this.partId = partId;
        this.memberId = memberId;
    }

    public PartComment(final String content, final Long partId, Long memberId) {
        this(null, content, partId, memberId);
    }

    @PrePersist
    private void prePersist() {
        createdAt = LocalDateTime.now().truncatedTo(ChronoUnit.MICROS);
    }

    public String getContent() {
        return content.getValue();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PartComment partComment = (PartComment) o;
        if (Objects.isNull(partComment.id) || Objects.isNull(this.id)) {
            return false;
        }
        return Objects.equals(id, partComment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
