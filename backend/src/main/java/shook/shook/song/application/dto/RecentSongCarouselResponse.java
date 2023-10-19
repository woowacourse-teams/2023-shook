package shook.shook.song.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import shook.shook.song.domain.Song;

@Schema(description = "캐러셀에 보여질 최근 노래 응답")
@AllArgsConstructor
@Getter
public class RecentSongCarouselResponse {

    @Schema(description = "노래 id", example = "1")
    private final Long id;

    @Schema(description = "노래 제목", example = "노래제목")
    private final String title;

    @Schema(description = "가수 이름", example = "가수")
    private final String singer;

    @Schema(description = "비디오 영상 길이", example = "274")
    private final int videoLength;

    @Schema(description = "앨범 자켓 이미지 url", example = "https://image.com/album_cover.jpg")
    private final String albumCoverUrl;

    public static RecentSongCarouselResponse from(final Song song) {
        return new RecentSongCarouselResponse(
            song.getId(),
            song.getTitle(),
            song.getArtistName(),
            song.getLength(),
            song.getAlbumCoverUrl()
        );
    }
}
