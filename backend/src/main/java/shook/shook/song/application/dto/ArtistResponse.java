package shook.shook.song.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import shook.shook.song.domain.Artist;

@Schema(description = "아티스트를 통한 아티스트, 해당 아티스트의 노래 검색 결과")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ArtistResponse {

    @Schema(description = "아티스트 id", example = "1")
    private final Long id;

    @Schema(description = "가수 이름", example = "가수")
    private final String singer;

    @Schema(description = "가수 대표 이미지 url", example = "https://image.com/artist-profile.jpg")
    private final String profileImageUrl;

    public static ArtistResponse from(final Artist artist) {
        return new ArtistResponse(
            artist.getId(),
            artist.getArtistName(),
            artist.getProfileImageUrl()
        );
    }
}
