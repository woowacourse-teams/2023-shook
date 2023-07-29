package shook.shook.member.domain;

import jakarta.persistence.Column;
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

    @Column(nullable = false, length = 100, updatable = false)
    private String email;

    //TODO: 닉네임은 중복이 되면 안될지 -> uinque key를 거는 것 여부판단하기
    @Column(nullable = false, length = 100)
    private String nickName;


    public Member(final String email, final String nickName) {
        this.id = null;
        this.email = email;
        this.nickName = nickName;
    }

}
