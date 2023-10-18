package shook.shook.song.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import shook.shook.song.domain.Artist;
import shook.shook.song.domain.Song;

@Schema(description = "아티스트를 통한 아티스트, 해당 아티스트의 노래 검색 결과")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ArtistWithSongSearchResponse {

    @Schema(description = "아티스트 id", example = "1")
    private final Long id;

    @Schema(description = "가수 이름", example = "가수")
    private final String singer;

    @Schema(description = "가수 대표 이미지 url", example = "https://image.com/artist-profile.jpg")
    private final String profileImageUrl;

    @Schema(description = "가수 노래 총 개수", example = "10")
    private final int totalSongCount;

    @Schema(description = "아티스트의 노래 목록")
    private final List<SongSearchResponse> songs;

    public static ArtistWithSongSearchResponse of(final Artist artist, final int totalSongCount,
                                                  final List<Song> songs) {
        return new ArtistWithSongSearchResponse(
            artist.getId(),
            artist.getArtistName(),
            artist.getProfileImageUrl(),
            totalSongCount,
            convertToSongSearchResponse(songs, artist.getArtistName())
        );
    }

    private static List<SongSearchResponse> convertToSongSearchResponse(final List<Song> songs,
                                                                        final String singer) {
        return songs.stream()
            .map(song -> SongSearchResponse.from(song, singer))
            .toList();
    }
}
