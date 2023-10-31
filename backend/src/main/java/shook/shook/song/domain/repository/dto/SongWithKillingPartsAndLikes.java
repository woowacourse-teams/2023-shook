package shook.shook.song.domain.repository.dto;

import java.util.List;
import shook.shook.song.domain.Song;
import shook.shook.song.domain.killingpart.KillingPart;
import shook.shook.song.domain.killingpart.KillingPartLike;

public interface SongWithKillingPartsAndLikes {

    Song getSong();

    default List<KillingPart> getKillingPart() {
        return getSong().getKillingParts();
    }

    default List<KillingPartLike> getKillingPartLikes() {
        return getKillingPart().stream()
            .map(KillingPart::getKillingPartLikes)
            .flatMap(List::stream)
            .toList();
    }
}
