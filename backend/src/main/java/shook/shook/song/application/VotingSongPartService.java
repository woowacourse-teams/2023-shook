package shook.shook.song.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shook.shook.part.domain.PartLength;
import shook.shook.song.application.dto.voting_song.VotingSongPartRegisterRequest;
import shook.shook.song.domain.repository.RegisterRepository;
import shook.shook.song.domain.repository.VotingSongPartRepository;
import shook.shook.song.domain.repository.VotingSongRepository;
import shook.shook.song.domain.voting_song.Register;
import shook.shook.song.domain.voting_song.VotingSong;
import shook.shook.song.domain.voting_song.VotingSongPart;
import shook.shook.song.exception.voting_song.VotingSongException.VotingSongNotExistException;
import shook.shook.song.exception.voting_song.VotingSongPartException;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class VotingSongPartService {

    private final VotingSongRepository votingSongRepository;
    private final VotingSongPartRepository votingSongPartRepository;
    private final RegisterRepository registerRepository;

    @Transactional
    public void register(
        final Long votingSongId,
        final VotingSongPartRegisterRequest request
    ) {
        final VotingSong votingSong = votingSongRepository.findById(votingSongId)
            .orElseThrow(VotingSongNotExistException::new);

        final int startSecond = request.getStartSecond();
        final PartLength partLength = PartLength.findBySecond(request.getLength());
        final VotingSongPart votingSongPart =
            VotingSongPart.forSave(startSecond, partLength, votingSong);

        if (votingSong.isUniquePart(votingSongPart)) {
            addPartAndVote(votingSong, votingSongPart);
            return;
        }

        voteToExistPart(votingSong, votingSongPart);
    }

    private void addPartAndVote(
        final VotingSong votingSong,
        final VotingSongPart votingSongPart
    ) {
        votingSong.addPart(votingSongPart);
        votingSongPartRepository.save(votingSongPart);

        voteToPart(votingSongPart);
    }

    private void voteToExistPart(
        final VotingSong votingSong,
        final VotingSongPart votingSongPart
    ) {
        final VotingSongPart existPart = votingSong.getSameLengthPartStartAt(votingSongPart)
            .orElseThrow(VotingSongPartException.PartNotExistException::new);

        voteToPart(existPart);
    }

    private void voteToPart(final VotingSongPart votingSongPart) {
        final Register newVote = Register.forSave(votingSongPart);
        votingSongPart.vote(newVote);
        registerRepository.save(newVote);
    }
}
