package shook.shook.improved.part.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.Map;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shook.shook.improved.part.exception.PartException;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@Embeddable
public class PartLength {

    private static final int MINIMUM_LENGTH = 5;
    private static final int MAXIMUM_LENGTH = 15;

    @Column(name = "length", nullable = false)
    private int value;

    public PartLength(final int value) {
        validate(value);
        this.value = value;
    }

    public void validate(final int second) {
        if (second < MINIMUM_LENGTH || second > MAXIMUM_LENGTH) {
            throw new PartException.InvalidLengthException(
                Map.of("PartLength", String.valueOf(second))
            );
        }
    }

    public int getEndSecond(final int start) {
        return start + this.value;
    }
}
