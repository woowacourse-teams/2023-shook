package shook.shook.voting_song.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shook.shook.song.domain.Artist;
import shook.shook.song.domain.ArtistName;
import shook.shook.song.domain.ProfileImageUrl;
import shook.shook.voting_song.domain.VotingSong;

@Schema(description = "파트 수집 중인 노래 등록 요청")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
public class VotingSongRegisterRequest {

    @Schema(description = "노래 제목", example = "제목")
    @NotBlank
    private String title;

    @Schema(description = "비디오 id", example = "asdfasdfasdf")
    @NotBlank
    private String videoId;

    @Schema(description = "앨범 자켓 이미지 url", example = "https://image.com/album_cover.jpg")
    @NotBlank
    private String imageUrl;

    @Schema(description = "가수 이름", example = "가수")
    @NotBlank
    private String artistName;

    @Schema(description = "가수 프로필 이미지", example = "https://image.com/singer-profile.jpg")
    @NotBlank
    private String profileImageUrl;

    @Schema(description = "비디오 길이", example = "274")
    @NotNull
    @Positive
    private Integer length;

    public VotingSong getVotingSong() {
        final Artist artist = new Artist(
            new ProfileImageUrl(profileImageUrl),
            new ArtistName(artistName)
        );
        
        return new VotingSong(title, videoId, imageUrl, artist, length);
    }
}
