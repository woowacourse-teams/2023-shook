package shook.shook.member.domain;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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

    private Member(final Long id, final String identifier, final String nickname) {
        this.id = id;
        this.identifier = new Identifier(identifier);
        this.nickname = new Nickname(nickname);
    }

    public Member(final String identifier, final String nickname) {
        this(null, identifier, nickname);
    }

    public String getIdentifier() {
        return identifier.getValue();
    }

    public String getNickname() {
        return nickname.getValue();
    }
}
