package shook.shook.legacy.song.domain.killingpart.repository.dto;

import java.time.LocalDateTime;
import shook.shook.legacy.song.domain.Song;
import shook.shook.legacy.song.domain.killingpart.KillingPart;

public interface SongKillingPartKillingPartLikeCreatedAtDto {

    Song getSong();

    KillingPart getKillingPart();

    LocalDateTime getKillingPartLikeCreatedAt();
}
