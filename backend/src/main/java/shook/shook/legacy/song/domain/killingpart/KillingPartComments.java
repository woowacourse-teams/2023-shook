package shook.shook.legacy.song.domain.killingpart;

import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shook.shook.song.exception.legacy_killingpart.KillingPartCommentException.DuplicateCommentExistException;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Embeddable
public class KillingPartComments {

    @OneToMany(mappedBy = "killingPart")
    private final List<KillingPartComment> comments = new ArrayList<>();

    public void addComment(final KillingPartComment comment) {
        validateComment(comment);
        comments.add(comment);
    }

    private void validateComment(final KillingPartComment comment) {
        if (comments.contains(comment)) {
            throw new DuplicateCommentExistException(
                Map.of("KillingPartCommentId", String.valueOf(comment.getId()))
            );
        }
    }

    public List<KillingPartComment> getComments() {
        return new ArrayList<>(comments);
    }

    public List<KillingPartComment> getCommentsInRecentOrder() {
        return comments.stream()
            .sorted(Comparator.comparing(KillingPartComment::getCreatedAt).reversed())
            .toList();
    }
}
