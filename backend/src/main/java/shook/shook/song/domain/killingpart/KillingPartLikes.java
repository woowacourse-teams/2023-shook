package shook.shook.song.domain.killingpart;

import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;
import shook.shook.member.domain.Member;
import shook.shook.song.exception.killingpart.KillingPartLikeException;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Embeddable
public class KillingPartLikes {

    @OneToMany(mappedBy = "killingPart")
    @Where(clause = "is_deleted = false")
    private List<KillingPartLike> likes = new ArrayList<>();

    public void addLike(final KillingPartLike like) {
        if (like.isDeleted()) {
            like.updateDeletion();
            likes.add(like);

            return;
        }
        throw new KillingPartLikeException.LikeStatusNotUpdatableException();
    }

    public void deleteLike(final KillingPartLike like) {
        if (like.isDeleted()) {
            throw new KillingPartLikeException.LikeStatusNotUpdatableException();
        }
        like.updateDeletion();
        likes.remove(like);
    }

    // TODO: 2023-08-14 성능향상을 위해 List->Map으로 필드 변경하는 고도화 고려
    public Optional<KillingPartLike> getLikeByMember(final Member member) {
        return likes.stream()
            .filter(like -> like.isOwner(member))
            .findAny();
    }

    public int getSize() {
        return likes.size();
    }
}
