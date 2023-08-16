package shook.shook.song.application;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shook.shook.member.domain.Member;
import shook.shook.member.domain.repository.MemberRepository;
import shook.shook.song.application.dto.SongResponse;
import shook.shook.song.application.dto.SongWithKillingPartsRegisterRequest;
import shook.shook.song.application.killingpart.dto.HighLikedSongResponse;
import shook.shook.song.domain.Song;
import shook.shook.song.domain.killingpart.repository.KillingPartRepository;
import shook.shook.song.domain.repository.SongRepository;
import shook.shook.song.domain.repository.dto.SongTotalLikeCountDto;
import shook.shook.song.exception.SongException;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class SongService {

    private final SongRepository songRepository;
    private final KillingPartRepository killingPartRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long register(final SongWithKillingPartsRegisterRequest request) {
        final Song song = request.convertToSong();
        final Song savedSong = songRepository.save(song);

        killingPartRepository.saveAll(song.getKillingParts());

        return savedSong.getId();
    }

    public List<HighLikedSongResponse> showHighLikedSongs() {
        final List<SongTotalLikeCountDto> songsWithLikeCount = songRepository.findAllWithTotalLikeCount();

        return HighLikedSongResponse.ofSongTotalLikeCounts(
            sortByHighestLikeCountAndId(songsWithLikeCount)
        );
    }

    private List<SongTotalLikeCountDto> sortByHighestLikeCountAndId(
        final List<SongTotalLikeCountDto> songWithLikeCounts
    ) {
        return songWithLikeCounts.stream()
            .sorted(
                Comparator.comparing(SongTotalLikeCountDto::getTotalLikeCount,
                        Comparator.reverseOrder())
                    .thenComparing(dto -> dto.getSong().getId(), Comparator.reverseOrder())
            ).toList();
    }

    public SongResponse findByIdAndMemberId(final Long songId, final Long memberId) {
        final Song song = songRepository.findById(songId)
            .orElseThrow(SongException.SongNotExistException::new);

        if (Objects.isNull(memberId)) {
            return SongResponse.fromUnauthorizedUser(song);
        }

        final Optional<Member> foundMember = memberRepository.findById(memberId);

        return foundMember.map(member -> SongResponse.of(song, member))
            .orElseGet(() -> SongResponse.fromUnauthorizedUser(song));
    }
}
