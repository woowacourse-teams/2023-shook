package shook.shook.song.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shook.shook.song.application.dto.voting_song.VotingSongRegisterRequest;
import shook.shook.song.domain.repository.VotingSongRepository;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class VotingSongService {

    private final VotingSongRepository votingSongRepository;

    @Transactional
    public void register(final VotingSongRegisterRequest request) {
        votingSongRepository.save(request.getVotingSong());
    }
}
