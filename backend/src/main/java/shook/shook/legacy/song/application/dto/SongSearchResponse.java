package shook.shook.legacy.song.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import shook.shook.legacy.song.domain.Song;

@Schema(description = "검색 결과 (가수, 가수의 노래) 응답")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class SongSearchResponse {

    @Schema(description = "노래 id", example = "1")
    private final Long id;

    @Schema(description = "노래 제목", example = "제목")
    private final String title;

    @Schema(description = "노래 앨범 커버 이미지 url", example = "https://image.com/album-cover.jpg")
    private final String albumCoverUrl;

    @Schema(description = "노래 비디오 길이", example = "247")
    private final int videoLength;

    @Schema(description = "가수 이름", example = "가수")
    private final String singer;

    public static SongSearchResponse from(final Song song, final String singer) {
        return new SongSearchResponse(
            song.getId(),
            song.getTitle(),
            song.getAlbumCoverUrl(),
            song.getLength(),
            singer
        );
    }
}
