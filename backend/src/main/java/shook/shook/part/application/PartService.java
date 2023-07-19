package shook.shook.part.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shook.shook.part.application.dto.PartRegisterRequest;
import shook.shook.part.application.dto.PartRegisterResponse;
import shook.shook.part.domain.Part;
import shook.shook.part.domain.PartLength;
import shook.shook.part.domain.Vote;
import shook.shook.part.domain.repository.PartRepository;
import shook.shook.part.domain.repository.VoteRepository;
import shook.shook.part.exception.PartException;
import shook.shook.song.domain.Song;
import shook.shook.song.domain.repository.SongRepository;
import shook.shook.song.exception.SongException;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PartService {

    private final SongRepository songRepository;
    private final PartRepository partRepository;
    private final VoteRepository voteRepository;

    @Transactional
    public PartRegisterResponse register(final Long songId, final PartRegisterRequest request) {
        final Song song = songRepository.findById(songId)
            .orElseThrow(SongException.SongNotExistException::new);

        final int startSecond = request.getStartSecond();
        final PartLength partLength = PartLength.findBySecond(request.getLength());
        final Part part = Part.forSave(startSecond, partLength, song);

        if (song.isUniquePart(part)) {
            return addPartAndVote(song, part);
        }

        return voteToExistPart(song, part);
    }

    private PartRegisterResponse addPartAndVote(final Song song, final Part part) {
        song.addPart(part);
        partRepository.save(part);

        voteToPart(part);

        return PartRegisterResponse.of(song, part);
    }

    private PartRegisterResponse voteToExistPart(final Song song, final Part part) {
        final Part existPart = song.getSameLengthPartStartAt(part)
            .orElseThrow(PartException.PartNotExistException::new);

        voteToPart(existPart);

        return PartRegisterResponse.of(song, existPart);
    }

    private void voteToPart(final Part part) {
        final Vote newVote = Vote.forSave(part);
        part.vote(newVote);
        voteRepository.save(newVote);
    }
}
