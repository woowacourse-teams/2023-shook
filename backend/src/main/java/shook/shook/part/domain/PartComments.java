package shook.shook.part.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shook.shook.part.exception.PartCommentException;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Embeddable
public class PartComments {

    @OneToMany(mappedBy = "part")
    private final List<PartComment> comments = new ArrayList<>();

    public void addComment(final PartComment comment) {
        validateComment(comment);
        comments.add(comment);
    }

    private void validateComment(final PartComment comment) {
        if (comments.contains(comment)) {
            throw new PartCommentException.DuplicateCommentExistException();
        }
    }

    public List<PartComment> getComments() {
        return new ArrayList<>(comments);
    }

    public List<PartComment> getCommentsInRecentOrder() {
        return comments.stream()
            .sorted(Comparator.comparing((PartComment::getCreatedAt)))
            .toList();
    }
}
