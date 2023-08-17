package shook.shook.voting_song.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shook.shook.voting_song.domain.VotingSong;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
public class VotingSongRegisterRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String videoId;

    @NotBlank
    private String imageUrl;

    @NotBlank
    private String singer;

    @NotNull
    @Positive
    private Integer length;

    public VotingSong getVotingSong() {
        return new VotingSong(title, videoId, imageUrl, singer, length);
    }
}
