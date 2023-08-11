package shook.shook.voting_song.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shook.shook.voting_song.application.dto.VotingSongRegisterRequest;
import shook.shook.voting_song.domain.repository.VotingSongRepository;

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
