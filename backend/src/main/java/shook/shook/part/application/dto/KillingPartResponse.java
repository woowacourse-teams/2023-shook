package shook.shook.part.application.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import shook.shook.part.domain.Part;
import shook.shook.part.domain.PartLength;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class KillingPartResponse {

    private final boolean exist;
    private final Integer start;
    private final Integer end;

    public static KillingPartResponse from(final Part part) {
        final int startSecond = part.getStartSecond();
        final PartLength length = part.getLength();
        return new KillingPartResponse(true, startSecond, length.getEndSecond(startSecond));
    }

    public static KillingPartResponse of(final Integer start, final Integer end) {
        return new KillingPartResponse(true, start, end);
    }

    public static KillingPartResponse empty() {
        return new KillingPartResponse(false, null, null);
    }
}
