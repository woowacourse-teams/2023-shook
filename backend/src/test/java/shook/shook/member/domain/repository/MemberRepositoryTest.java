package shook.shook.member.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import shook.shook.member.domain.Member;
import shook.shook.support.UsingJpaTest;

class MemberRepositoryTest extends UsingJpaTest {

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("Member 를 저장할 때의 시간 정보로 createAt이 자동 생성된다.")
    @Test
    void createdAt_prePersist() {
        //given
        final Member member = new Member("email@email.com", "nickname");

        //when
        final LocalDateTime prev = LocalDateTime.now().truncatedTo(ChronoUnit.MICROS);
        final Member saved = memberRepository.save(member);
        final LocalDateTime after = LocalDateTime.now().truncatedTo(ChronoUnit.MICROS);

        //then
        assertThat(member).isSameAs(saved);
        assertThat(member.getCreatedAt()).isBetween(prev, after);
    }
}
