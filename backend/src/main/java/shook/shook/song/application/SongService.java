package shook.shook.song.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shook.shook.song.application.dto.SongResponse;
import shook.shook.song.application.dto.SongWithKillingPartsRegisterRequest;
import shook.shook.song.application.killingpart.dto.HighLikedSongResponse;
import shook.shook.song.domain.Song;
import shook.shook.song.domain.killingpart.repository.KillingPartRepository;
import shook.shook.song.domain.repository.dto.SongTotalLikeCountDto;
import shook.shook.song.domain.repository.SongRepository;
import shook.shook.song.exception.SongException;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class SongService {

    private final SongRepository songRepository;
    private final KillingPartRepository killingPartRepository;

    @Transactional
    public long register(final SongWithKillingPartsRegisterRequest request) {
        final Song song = request.toSong();
        final Song savedSong = songRepository.save(song);

        killingPartRepository.saveAll(song.getKillingParts());

        return savedSong.getId();
    }
    
    public List<HighLikedSongResponse> showHighLikedSongs() {
        final List<SongTotalLikeCountDto> songsWithLikeCount = songRepository.findAllWithTotalLikeCount();

        return HighLikedSongResponse.ofSongTotalLikeCounts(sortByHighestLikeCountAndId(songsWithLikeCount));
    }
    
    private List<SongTotalLikeCountDto> sortByHighestLikeCountAndId(
        final List<SongTotalLikeCountDto> songWithLikeCounts
    ) {
        return songWithLikeCounts.stream()
            .sorted((firstSong, secondSong) -> {
                if (firstSong.getTotalLikeCount().equals(secondSong.getTotalLikeCount())) {
                    return secondSong.getSong().getId().compareTo(firstSong.getSong().getId());
                }
                return secondSong.getTotalLikeCount().compareTo(firstSong.getTotalLikeCount());
            })
            .toList();
    }

    public SongResponse findById(final Long songId) {
        final Song song = songRepository.findById(songId)
            .orElseThrow(SongException.SongNotExistException::new);

        return SongResponse.from(song);
    }

}
