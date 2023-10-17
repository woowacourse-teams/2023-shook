package shook.shook.my_part.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import shook.shook.member.domain.Member;
import shook.shook.member.domain.repository.MemberRepository;
import shook.shook.my_part.domain.MemberPart;
import shook.shook.my_part.domain.repository.dto.SongMemberPartCreatedAtDto;
import shook.shook.song.domain.Genre;
import shook.shook.song.domain.KillingParts;
import shook.shook.song.domain.Song;
import shook.shook.song.domain.killingpart.KillingPart;
import shook.shook.song.domain.repository.SongRepository;
import shook.shook.support.UsingJpaTest;

@Sql(scripts = "classpath:killingpart/initialize_killing_part_song.sql")
class MemberPartRepositoryTest extends UsingJpaTest {

    @Autowired
    private MemberPartRepository memberPartRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private SongRepository songRepository;

    @DisplayName("멤버 아이디와 멤버 파트 아이디로 멤버 파트를 조회한다.")
    @Test
    void findByMemberIdAndId() {
        // given
        final Member member = createAndSaveMember("email", "name");
        final Song song = createNewSongWithKillingPartsAndSaveSong();
        final MemberPart memberPart = createAndSaveMemberPart(MemberPart.forSave(10, 5, song, member));
        saveAndClearEntityManager();

        // when
        final Optional<MemberPart> optionalMember = memberPartRepository.findByMemberIdAndId(member.getId(),
                                                                                             memberPart.getId());
        final MemberPart savedMemberPart = optionalMember.get();
        // then
        assertThat(optionalMember).isPresent();
        assertThat(savedMemberPart.getMember().getId()).isEqualTo(member.getId());
        assertThat(savedMemberPart.getSong().getId()).isEqualTo(song.getId());
    }

    private Song createNewSongWithKillingPartsAndSaveSong() {
        final KillingPart firstKillingPart = KillingPart.forSave(10, 5);
        final KillingPart secondKillingPart = KillingPart.forSave(15, 5);
        final KillingPart thirdKillingPart = KillingPart.forSave(20, 5);

        final Song song = new Song(
            "제목", "비디오ID는 11글자", "이미지URL", "가수", 180, Genre.from("댄스"),
            new KillingParts(List.of(firstKillingPart, secondKillingPart, thirdKillingPart)));

        return songRepository.save(song);
    }

    private Member createAndSaveMember(final String email, final String name) {
        final Member member = new Member(email, name);
        return memberRepository.save(member);
    }

    private MemberPart createAndSaveMemberPart(final MemberPart memberPart) {
        return memberPartRepository.save(memberPart);
    }

    @DisplayName("해당하는 song Id 리스트를 입력 받아 멤버 파트 리스트를 조회한다.")
    @Test
    void findBySongIdIn() {
        // given
        final Song firstSong = createNewSongWithKillingPartsAndSaveSong();
        final Song secondSong = createNewSongWithKillingPartsAndSaveSong();
        final Song thirdSong = createNewSongWithKillingPartsAndSaveSong();
        final Song fourthSong = createNewSongWithKillingPartsAndSaveSong();
        final Member member = createAndSaveMember("email", "name");

        createAndSaveMemberPart(MemberPart.forSave(10, 5, firstSong, member));
        createAndSaveMemberPart(MemberPart.forSave(10, 5, secondSong, member));
        createAndSaveMemberPart(MemberPart.forSave(10, 5, thirdSong, member));

        saveAndClearEntityManager();

        // when
        final List<MemberPart> memberParts = memberPartRepository.findByMemberAndSongIdIn(member,
                                                                                          List.of(firstSong.getId(),
                                                                                                  secondSong.getId(),
                                                                                                  thirdSong.getId()));

        // then
        assertThat(memberParts).hasSize(3);
        assertThat(memberParts.stream()
                       .map(MemberPart::getSong)
                       .map(Song::getId)
                       .toList()).contains(firstSong.getId(), secondSong.getId(), thirdSong.getId());
    }

    @DisplayName("나의 파트를 조회한다.")
    @Test
    void find_myPart() {
        // given
        final Song savedSong = songRepository.findById(1L).get();

        final Member member = new Member("shook@s.com", "shook1");
        final Member savedMember = memberRepository.save(member);

        final MemberPart memberPart = MemberPart.forSave(10, 15, savedSong, savedMember);
        final MemberPart savedMemberPart = memberPartRepository.save(memberPart);

        // when
        final List<SongMemberPartCreatedAtDto> result = memberPartRepository.findByMemberId(savedMember.getId());

        // then
        assertThat(result.size()).isOne();
        assertThat(result.get(0).getSong()).isEqualTo(savedSong);
        assertThat(result.get(0).getMemberPart()).isEqualTo(savedMemberPart);
    }
}
