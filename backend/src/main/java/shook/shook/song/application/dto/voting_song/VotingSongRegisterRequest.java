package shook.shook.song.application.dto.voting_song;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shook.shook.song.domain.voting_song.VotingSong;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
public class VotingSongRegisterRequest {

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

    public VotingSong getVotingSong() {
        return new VotingSong(title, videoUrl, imageUrl, singer, length);
    }
}
