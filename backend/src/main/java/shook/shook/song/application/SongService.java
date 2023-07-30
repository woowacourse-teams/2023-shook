package shook.shook.song.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shook.shook.part.domain.Part;
import shook.shook.song.application.dto.HighVotedSongResponse;
import shook.shook.song.application.dto.KillingPartResponse;
import shook.shook.song.application.dto.KillingPartsResponse;
import shook.shook.song.application.dto.SongRegisterRequest;
import shook.shook.song.application.dto.SongResponse;
import shook.shook.song.domain.Song;
import shook.shook.song.domain.SongTitle;
import shook.shook.song.domain.SongTotalVoteCountDto;
import shook.shook.song.domain.repository.SongRepository;
import shook.shook.song.exception.SongException;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class SongService {

    private static final int HIGH_VOTED_SONG_SIZE = 40;
    private final SongRepository songRepository;

    @Transactional
    public void register(final SongRegisterRequest songRegisterRequest) {
        songRepository.save(songRegisterRequest.getSong());
    }

    public SongResponse findById(final Long songId) {
        final Song song = songRepository.findById(songId)
            .orElseThrow(SongException.SongNotExistException::new);

        return SongResponse.from(song);
    }

    public SongResponse findByTitle(final String name) {
        final Song song = songRepository.findByTitle(new SongTitle(name))
            .orElseThrow(SongException.SongNotExistException::new);

        return SongResponse.from(song);
    }

    public KillingPartResponse showTopKillingPart(final Long songId) {
        final Song song = songRepository.findById(songId)
            .orElseThrow(SongException.SongNotExistException::new);

        return song.getTopKillingPart()
            .map((killingPart) -> KillingPartResponse.of(song, killingPart))
            .orElseGet(KillingPartResponse::empty);
    }

    public KillingPartsResponse showKillingParts(final Long songId) {
        final Song song = songRepository.findById(songId)
            .orElseThrow(SongException.SongNotExistException::new);

        final List<Part> killingParts = song.getKillingParts();

        return KillingPartsResponse.of(song, killingParts);
    }

    public List<HighVotedSongResponse> findHighVotedSongs() {
        final List<SongTotalVoteCountDto> songTotalVoteCountDto = songRepository.findSongWithTotalVoteCount();

        final List<Song> highVotedSongs = songTotalVoteCountDto.stream()
            .sorted((o1, o2) -> {
                if (o1.getTotalVoteCount().equals(o2.getTotalVoteCount())) {
                    return o2.getSong().getCreatedAt().compareTo(o1.getSong().getCreatedAt());
                }
                return o2.getTotalVoteCount().compareTo(o1.getTotalVoteCount());
            })
            .map(SongTotalVoteCountDto::getSong)
            .toList();

        if (highVotedSongs.size() <= HIGH_VOTED_SONG_SIZE) {
            return HighVotedSongResponse.getList(highVotedSongs);
        }
        return HighVotedSongResponse.getList(highVotedSongs.subList(0, HIGH_VOTED_SONG_SIZE));
    }
}
