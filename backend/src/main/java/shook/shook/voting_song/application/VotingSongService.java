package shook.shook.voting_song.application;

import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shook.shook.voting_song.application.dto.VotingSongRegisterRequest;
import shook.shook.voting_song.application.dto.VotingSongResponse;
import shook.shook.voting_song.application.dto.VotingSongSwipeResponse;
import shook.shook.voting_song.domain.VotingSong;
import shook.shook.voting_song.domain.repository.VotingSongRepository;
import shook.shook.voting_song.exception.VotingSongException;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class VotingSongService {

    private static final int BEFORE_SONG_COUNT = 4;
    private static final int AFTER_SONG_COUNT = 4;

    private final VotingSongRepository votingSongRepository;

    @Transactional
    public void register(final VotingSongRegisterRequest request) {
        votingSongRepository.save(request.getVotingSong());
    }

    public VotingSongSwipeResponse findByIdForSwipe(final Long id) {
        final VotingSong votingSong = votingSongRepository.findById(id)
            .orElseThrow(VotingSongException.VotingSongNotExistException::new);

        final List<VotingSong> beforeVotingSongs = votingSongRepository.findByIdLessThanOrderByIdDesc(
            id, PageRequest.of(0, BEFORE_SONG_COUNT)
        );
        Collections.reverse(beforeVotingSongs);

        final List<VotingSong> afterVotingSongs = votingSongRepository.findByIdGreaterThanOrderByIdAsc(
            id, PageRequest.of(0, AFTER_SONG_COUNT)
        );

        return VotingSongSwipeResponse.from(votingSong, beforeVotingSongs, afterVotingSongs);
    }

    public List<VotingSongResponse> findAll() {
        final Sort ascendingSort = Sort.by("id").ascending();

        return votingSongRepository.findAll(ascendingSort).stream()
            .map(VotingSongResponse::from)
            .toList();
    }
}
