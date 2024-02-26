package shook.shook.part.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "part")
@Entity
public class Part {

    private static final String EMBED_LINK_PATH_PARAMETER_FORMAT = "?start=%d&end=%d";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private StartSecond startSecond;

    @Embedded
    private PartLength length;

    @Column(name = "song_id", nullable = false, updatable = false)
    private Long songId;

    @Column(name = "count", nullable = false)
    private int count = 0;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now().truncatedTo(ChronoUnit.MICROS);

    private Part(final Long id, final int startSecond, final int length, final Long songId) {
        this.id = id;
        this.startSecond = new StartSecond(startSecond);
        this.length = new PartLength(length);
        this.songId = songId;
    }

    public Part(final int startSecond, final int length, final Long songId) {
        this(null, startSecond, length, songId);
    }

    @PrePersist
    private void prePersist() {
        createdAt = LocalDateTime.now().truncatedTo(ChronoUnit.MICROS);
    }

    public int getStartSecond() {
        return startSecond.getValue();
    }

    public int getLength() {
        return length.getValue();
    }
}
