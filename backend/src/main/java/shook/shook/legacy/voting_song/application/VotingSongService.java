package shook.shook.legacy.voting_song.application;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shook.shook.legacy.song.domain.repository.ArtistRepository;
import shook.shook.legacy.voting_song.application.dto.VotingSongRegisterRequest;
import shook.shook.legacy.voting_song.application.dto.VotingSongResponse;
import shook.shook.legacy.voting_song.application.dto.VotingSongSwipeResponse;
import shook.shook.legacy.voting_song.domain.VotingSong;
import shook.shook.legacy.voting_song.domain.repository.VotingSongRepository;
import shook.shook.legacy.voting_song.exception.VotingSongException;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class VotingSongService {

    private static final int BEFORE_SONG_COUNT = 4;
    private static final int AFTER_SONG_COUNT = 4;

    private final VotingSongRepository votingSongRepository;
    private final ArtistRepository artistRepository;

    @Transactional
    public void register(final VotingSongRegisterRequest request) {
        final VotingSong votingSong = request.getVotingSong();
        artistRepository.save(votingSong.getArtist());
        votingSongRepository.save(votingSong);
    }

    public VotingSongSwipeResponse findAllForSwipeById(final Long id) {
        final VotingSong votingSong = votingSongRepository.findById(id)
            .orElseThrow(() -> new VotingSongException.VotingSongNotExistException(
                Map.of("VotingSongId", String.valueOf(id))
            ));

        final long startId = Math.max(0, id - BEFORE_SONG_COUNT);
        final long endId = id + AFTER_SONG_COUNT;

        final List<VotingSong> songsForSwipe =
            votingSongRepository.findByIdGreaterThanEqualAndIdLessThanEqual(startId, endId)
                .stream()
                .sorted(Comparator.comparing(VotingSong::getId))
                .toList();

        return VotingSongSwipeResponse.of(songsForSwipe, votingSong);
    }

    public List<VotingSongResponse> findAll() {
        return votingSongRepository.findAll().stream()
            .sorted(Comparator.comparing(VotingSong::getId))
            .map(VotingSongResponse::from)
            .toList();
    }
}
