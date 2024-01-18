package shook.shook.legacy.voting_song.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import shook.shook.legacy.voting_song.domain.VotingSong;

@Schema(description = "파트 수집 중인 노래 응답")
@AllArgsConstructor
@Getter
public class VotingSongResponse {

    @Schema(description = "노래 id", example = "1")
    private final Long id;

    @Schema(description = "노래 제목", example = "노래제목")
    private final String title;

    @Schema(description = "가수 이름", example = "가수")
    private final String singer;

    @Schema(description = "비디오 영상 길이", example = "274")
    private final int videoLength;

    @Schema(description = "비디오 영상 id", example = "asdfasdfasdf")
    private final String songVideoId;

    @Schema(description = "앨범 자켓 이미지 url", example = "https://image.com/album_cover.jpg")
    private final String albumCoverUrl;

    public static VotingSongResponse from(final VotingSong song) {
        return new VotingSongResponse(
            song.getId(),
            song.getTitle(),
            song.getArtistName(),
            song.getLength(),
            song.getVideoId(),
            song.getAlbumCoverUrl()
        );
    }
}
