package shook.shook.part.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "part_comment")
@Entity
public class PartComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private PartCommentContent content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "part_id", foreignKey = @ForeignKey(name = "none"), nullable = false, updatable = false)
    @Getter(AccessLevel.NONE)
    private Part part;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @PrePersist
    private void prePersist() {
        createdAt = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
    }

    private PartComment(final Long id, final Part part, final String content) {
        this.id = id;
        this.part = part;
        this.content = new PartCommentContent(content);
    }

    public static PartComment saved(final Long id, final Part part, final String content) {
        return new PartComment(id, part, content);
    }

    public static PartComment forSave(final Part part, final String content) {
        return new PartComment(null, part, content);
    }

    public boolean isBelongToOtherPart(final Part part) {
        return !this.part.equals(part);
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
