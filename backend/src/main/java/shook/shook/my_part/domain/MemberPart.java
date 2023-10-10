package shook.shook.my_part.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shook.shook.member.domain.Member;
import shook.shook.my_part.exception.MemberPartException;
import shook.shook.part.domain.PartLength;
import shook.shook.song.domain.Song;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "member_part")
@Entity
public class MemberPart {

    private static final int MINIMUM_START = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false)
    private int startSecond;

    @Embedded
    private PartLength length;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "song_id", foreignKey = @ForeignKey(name = "none"), updatable = false, nullable = false)
    private Song song;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", foreignKey = @ForeignKey(name = "none"), updatable = false, nullable = false)
    private Member member;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now().truncatedTo(ChronoUnit.MICROS);

    @PrePersist
    private void prePersist() {
        createdAt = LocalDateTime.now().truncatedTo(ChronoUnit.MICROS);
    }

    private MemberPart(
        final Long id,
        final int startSecond,
        final PartLength length,
        final Song song,
        final Member member
    ) {
        this.id = id;
        this.startSecond = startSecond;
        this.length = length;
        this.song = song;
        this.member = member;
    }

    public static MemberPart saved(
        final Long id,
        final int startSecond,
        final PartLength length,
        final Song song,
        final Member member
    ) {
        return new MemberPart(id, startSecond, length, song, member);
    }

    public static MemberPart forSave(final int startSecond, final int length, final Song song, final Member member) {
        final PartLength partLength = new PartLength(length);
        validateStartSecond(startSecond, partLength, song.getLength());
        return new MemberPart(null, startSecond, partLength, song, member);
    }

    private static void validateStartSecond(final int startSecond, final PartLength length, final int songLength) {
        if (startSecond < MINIMUM_START) {
            throw new MemberPartException.MemberPartStartSecondNegativeException(
                Map.of("startSecond", String.valueOf(startSecond))
            );
        }
        if (length.getEndSecond(startSecond) > songLength) {
            throw new MemberPartException.MemberPartEndOverSongLengthException(
                Map.of("startSecond", String.valueOf(startSecond),
                       "EndSecond", String.valueOf(length.getEndSecond(startSecond))
                )
            );
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MemberPart part = (MemberPart) o;
        if (Objects.isNull(part.id) || Objects.isNull(this.id)) {
            return false;
        }
        return Objects.equals(id, part.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
