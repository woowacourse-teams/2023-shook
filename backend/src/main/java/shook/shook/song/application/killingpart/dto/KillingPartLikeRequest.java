package shook.shook.song.application.killingpart.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
public class KillingPartLikeRequest {

    @NotNull
    private Boolean likeStatus;

    public boolean isLikeCreateRequest() {
        return likeStatus;
    }
}
