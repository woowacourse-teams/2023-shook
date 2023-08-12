package shook.shook.song.domain;

import io.jsonwebtoken.lang.Collections;
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
    private List<KillingPart> killingParts = new ArrayList<>();

    public KillingParts(final List<KillingPart> killingParts) {
        validate(killingParts);
        this.killingParts = killingParts;
    }

    private void validate(final List<KillingPart> killingParts) {
        if (Collections.isEmpty(killingParts)) {
            throw new KillingPartsException.EmptyKillingPartException();
        }
        if (killingParts.size() != KILLING_PART_COUNT) {
            throw new KillingPartsException.OutOfSizeException();
        }
    }

    public boolean isFull() {
        return killingParts.size() == KILLING_PART_COUNT;
    }

    protected void setSong(final Song song) {
        for (KillingPart killingPart : killingParts) {
            killingPart.setSong(song);
        }
    }

    public List<KillingPart> getKillingParts() {
        return new ArrayList<>(killingParts);
    }

    // TODO: 2023-08-12 좋아요순으로 정렬해서 조회
}
