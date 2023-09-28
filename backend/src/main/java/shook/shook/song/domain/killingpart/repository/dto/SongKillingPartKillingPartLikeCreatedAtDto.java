package shook.shook.song.domain.killingpart.repository.dto;

import java.time.LocalDateTime;
import shook.shook.song.domain.Song;
import shook.shook.song.domain.killingpart.KillingPart;

public interface SongKillingPartKillingPartLikeCreatedAtDto {

    Song getSong();

    KillingPart getKillingPart();

    LocalDateTime getKillingPartLikeCreatedAt();
}
