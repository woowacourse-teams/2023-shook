package shook.shook.part.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import shook.shook.part.domain.Part;
import shook.shook.song.domain.Song;

@AllArgsConstructor
@Getter
public class PartRegisterResponse {

    private final int rank;
    private final int voteCount;
    private final String partVideoUrl;

    public static PartRegisterResponse of(final Song song, final Part part) {
        return new PartRegisterResponse(
            song.getRank(part),
            part.getVoteCount(),
            song.getPartVideoUrl(part)
        );
    }
}
