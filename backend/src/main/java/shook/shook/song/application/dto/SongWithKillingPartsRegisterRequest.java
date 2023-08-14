package shook.shook.song.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shook.shook.song.domain.KillingParts;
import shook.shook.song.domain.Song;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
public class SongWithKillingPartsRegisterRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String videoUrl;

    @NotBlank
    private String imageUrl;

    @NotBlank
    private String singer;

    @NotNull
    @Positive
    private Integer length;

    @NotEmpty
    private List<KillingPartRegisterRequest> killingParts;

    public Song getSong() {
        return new Song(title, videoUrl, imageUrl, singer, length, convertToKillingParts());
    }

    private KillingParts convertToKillingParts() {
        return new KillingParts(
            killingParts.stream()
                .map(KillingPartRegisterRequest::getKillingPart)
                .toList()
        );
    }
}
