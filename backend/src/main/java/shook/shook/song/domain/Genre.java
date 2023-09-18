package shook.shook.song.domain;

import java.util.Arrays;

public enum Genre {

    BALLAD("발라드"),
    DANCE("댄스"),
    HIPHOP("힙합"),
    RHYTHM_AND_BLUES("R&B/Soul"),
    INDIE("인디"),
    ROCK_METAL("록/메탈"),
    TROT("트로트"),
    FOLK_BLUES("포크/블루스"),
    POP("팝"),
    JAZZ("재즈"),
    CLASSIC("클래식"),
    J_POP("JPOP"),
    EDM("EDM"),
    ETC("기타");

    private final String value;

    Genre(final String value) {
        this.value = value;
    }

    public static Genre from(final String name) {
        return Arrays.stream(values())
            .filter(value -> value.getValue().equalsIgnoreCase(name))
            .findFirst()
            .orElse(ETC);
    }

    public String getValue() {
        return value;
    }
}
