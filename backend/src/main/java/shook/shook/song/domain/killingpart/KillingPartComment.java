package shook.shook.song.domain.killingpart;

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
@Table(name = "killing_part_comment")
@Entity
public class KillingPartComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private KillingPartCommentContent content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "killing_part_id", foreignKey = @ForeignKey(name = "none"), nullable = false, updatable = false)
    @Getter(AccessLevel.NONE)
    private KillingPart killingPart;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @PrePersist
    private void prePersist() {
        createdAt = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
    }

    private KillingPartComment(final Long id, final KillingPart killingPart, final String content) {
        this.id = id;
        this.killingPart = killingPart;
        this.content = new KillingPartCommentContent(content);
    }

    public static KillingPartComment saved(
        final Long id,
        final KillingPart part,
        final String content
    ) {
        return new KillingPartComment(id, part, content);
    }

    public static KillingPartComment forSave(final KillingPart part, final String content) {
        return new KillingPartComment(null, part, content);
    }

    public boolean isBelongToOtherPart(final KillingPart part) {
        return !this.killingPart.equals(part);
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
        final KillingPartComment partComment = (KillingPartComment) o;
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
