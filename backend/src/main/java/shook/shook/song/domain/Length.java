package shook.shook.song.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Length {

    @Column(name = "length", nullable = false)
    private int value;

    public Length(final int value) {
        validate(value);
        this.value = value;
    }

    private void validate(final int value) {
        if (value <= 0) {
            throw new IllegalArgumentException("노래 길이는 0보다 커야 합니다.");
        }
    }

    public int getValue() {
        return value;
    }
}
