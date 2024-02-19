package shook.shook.part.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.Map;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shook.shook.part.exception.PartException;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode
@Embeddable
public class StartSecond {

    private static final int MINIMUM_START = 0;

    @Column(name = "start_second", nullable = false)
    private int value;

    public StartSecond(final int value) {
        validateStartSecond(value);
        this.value = value;
    }

    private void validateStartSecond(final int value) {
        if (value < MINIMUM_START) {
            throw new PartException.StartLessThanZeroException(
                Map.of("startSecond", String.valueOf(value))
            );
        }
    }
}
