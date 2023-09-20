package shook.shook.song.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shook.shook.song.domain.Genre;
import shook.shook.song.domain.KillingParts;
import shook.shook.song.domain.Song;

@Schema(description = "노래와 킬링파트 등록 요청")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
public class SongWithKillingPartsRegisterRequest {

    @Schema(description = "노래 제목", example = "제목")
    @NotBlank
    private String title;

    @Schema(description = "노래 비디오 url", example = "https://www.youtube.com/watch?v=asdfasdfasdf")
    @NotBlank
    private String videoId;

    @Schema(description = "앨범 커버 이미지 url", example = "https://image.com/album-cover.jpg")
    @NotBlank
    private String imageUrl;

    @Schema(description = "가수 이름", example = "가수")
    @NotBlank
    private String singer;

    @Schema(description = "노래 길이", example = "247")
    @NotNull
    @Positive
    private Integer length;

    @Schema(description = "노래 장르", example = "댄스")
    @NotBlank
    private String genre;

    @Schema(description = "킬링파트 3개")
    @NotEmpty
    private List<KillingPartRegisterRequest> killingParts;

    public Song convertToSong() {
        return new Song(title, videoId, imageUrl, singer, length, Genre.from(genre),
            convertToKillingParts());
    }

    private KillingParts convertToKillingParts() {
        return new KillingParts(
            killingParts.stream()
                .map(KillingPartRegisterRequest::toKillingPart)
                .toList()
        );
    }
}
