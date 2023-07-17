package shook.shook.song.application.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import shook.shook.part.domain.Part;

@AllArgsConstructor
@Getter
public class KillingPartsResponse {

    private final List<KillingPartResponse> responses;

    public static KillingPartsResponse of(final List<Part> parts) {
        final List<KillingPartResponse> killingPartResponses = parts.stream()
            .map(KillingPartResponse::from)
            .toList();

        return new KillingPartsResponse(killingPartResponses);
    }
}
