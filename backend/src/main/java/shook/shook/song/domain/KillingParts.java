package shook.shook.song.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shook.shook.song.domain.killingpart.KillingPart;
import shook.shook.song.exception.killingpart.KillingPartsException;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Embeddable
public class KillingParts {

    private static final int KILLING_PART_COUNT = 3;

    @OneToMany(mappedBy = "song")
    private final List<KillingPart> killingParts = new ArrayList<>();

    public KillingParts(final List<KillingPart> killingParts) {
        // TODO: 2023-08-10 저장할 때 좋아요순으로 정렬해서 저장
        validate(killingParts);
        this.killingParts.addAll(new ArrayList<>(killingParts));
    }

    private void validate(final List<KillingPart> killingParts) {
        if (killingParts.size() > KILLING_PART_COUNT) {
            throw new KillingPartsException.OverSizeLimitException();
        }
    }

    public List<KillingPart> getKillingParts() {
        return new ArrayList<>(killingParts);
    }
}
