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

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "member")
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Email email;

    @Embedded
    private NickName nickName;


    public Member(final String email, final String nickName) {
        this.id = null;
        this.email = new Email(email);
        this.nickName = new NickName(nickName);
    }

    public String getEmail() {
        return email.getValue();
    }

    public String getNickName() {
        return nickName.getValue();
    }
}
