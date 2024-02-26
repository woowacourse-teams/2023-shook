package shook.shook.improved.member.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.awt.Desktop;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shook.shook.legacy.member.domain.Nickname;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member")
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Identifier identifier;

    @Embedded
    private Nickname nickname;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now().truncatedTo(ChronoUnit.MICROS);

    @Column(nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now().truncatedTo(ChronoUnit.MICROS);

    private Member(final Long id, final String identifier, final String nickname) {
        this.id = id;
        this.identifier = new Identifier(identifier);
        this.nickname = new Nickname(nickname);
    }

    public Member(final String identifier, final String nickname) {
        this(null, identifier, nickname);
    }

    @PrePersist
    private void prePersist() {
        createdAt = LocalDateTime.now().truncatedTo(ChronoUnit.MICROS);
    }

    @PreUpdate
    private void preUpdate() {
        updatedAt = LocalDateTime.now().truncatedTo(ChronoUnit.MICROS);
    }

    public void updateNickname(final String newNickName) {
        this.nickname = new Nickname(newNickName);
    }

    public String getIdentifier() {
        return identifier.getValue();
    }

    public String getNickname() {
        return nickname.getValue();
    }
}
