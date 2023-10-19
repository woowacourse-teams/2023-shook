package shook.shook.song.application.killingpart.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import shook.shook.song.domain.Song;

@Schema(description = "좋아요 순 노래 응답")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class HighLikedSongResponse {

    @Schema(description = "노래 id", example = "1")
    private final Long id;

    @Schema(description = "노래 제목", example = "제목")
    private final String title;

    @Schema(description = "가수 이름", example = "가수")
    private final String singer;

    @Schema(description = "앨범 커버 이미지 url", example = "https://image.com/album-cover.jpg")
    private final String albumCoverUrl;

    @Schema(description = "총 좋아요 개수", example = "40")
    private final long totalLikeCount;

    @Schema(description = "노래 장르", example = "DANCE")
    private final String genre;

    private static HighLikedSongResponse from(final Song song) {
        return new HighLikedSongResponse(
            song.getId(),
            song.getTitle(),
            song.getArtistName(),
            song.getAlbumCoverUrl(),
            song.getTotalLikeCount(),
            song.getGenre().name()
        );
    }

    public static List<HighLikedSongResponse> ofSongs(final List<Song> songs) {
        return songs.stream()
            .map(HighLikedSongResponse::from)
            .toList();
    }
}
