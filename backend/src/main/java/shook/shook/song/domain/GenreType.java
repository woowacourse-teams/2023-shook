package shook.shook.song.domain;

import java.util.Arrays;
import java.util.Map;
import shook.shook.song.exception.SongException;

public enum GenreType {

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

    GenreType(final String value) {
        this.value = value;
    }

    public static GenreType from(final String name) {
        return Arrays.stream(values())
            .filter(genre -> genre.value.equalsIgnoreCase(name))
            .findFirst()
            .orElse(ETC);
    }

    public static GenreType findByName(final String name) {
        return Arrays.stream(values())
            .filter(genre -> genre.value.equalsIgnoreCase(name))
            .findFirst()
            .orElseThrow(() -> new SongException.SongGenreNotFoundException(Map.of("genre", name)));
    }

    public String getValue() {
        return value;
    }
}
