package shook.shook.song.application.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import shook.shook.part.domain.Part;
import shook.shook.song.domain.Song;

@AllArgsConstructor
@Getter
public class KillingPartsResponse {

    private final List<KillingPartResponse> responses;

    public static KillingPartsResponse of(final Song song, final List<Part> parts) {
        final List<KillingPartResponse> killingPartResponses = parts.stream().
            map((killingPart) -> KillingPartResponse.of(song, killingPart))
            .toList();

        return new KillingPartsResponse(killingPartResponses);
    }
}
