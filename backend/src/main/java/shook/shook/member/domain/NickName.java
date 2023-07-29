package shook.shook.member.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shook.shook.member.exception.MemberException;
import shook.shook.song.exception.SongException;
import shook.shook.util.StringChecker;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode
@Embeddable
public class NickName {

    private static final int NICK_NAME_MAXIMUM_LENGTH = 100;

    @Column(name = "nick_name", length = NICK_NAME_MAXIMUM_LENGTH, nullable = false)
    private String nickName;

    public NickName(final String nickName) {
        validateNickName(nickName);
        this.nickName = nickName;
    }

    private void validateNickName(final String nickName) {
        if (StringChecker.isNullOrBlank(nickName)) {
            throw new MemberException.NullOrEmptyNickNameException();
        }
        if (nickName.length() > NICK_NAME_MAXIMUM_LENGTH) {
            throw new MemberException.TooLongNickNameException();
        }
    }
}
