package shook.shook.part.application;

import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import shook.shook.part.application.dto.KillingPartResponse;
import shook.shook.part.application.dto.KillingPartsResponse;
import shook.shook.part.application.dto.PartRegisterRequest;
import shook.shook.part.domain.Part;
import shook.shook.part.domain.PartLength;
import shook.shook.part.domain.Vote;
import shook.shook.part.domain.repository.PartRepository;
import shook.shook.part.domain.repository.VoteRepository;
import shook.shook.part.exception.PartException;
import shook.shook.song.domain.Song;
import shook.shook.song.domain.repository.SongRepository;
import shook.shook.song.exception.SongException.SongNotExistException;

@AllArgsConstructor
@Transactional(readOnly = true, propagation = Propagation.MANDATORY)
@Service
public class PartService {

    private final SongRepository songRepository;
    private final PartRepository partRepository;
    private final VoteRepository voteRepository;

    @Transactional
    public void register(final PartRegisterRequest request) {
        final Song song = songRepository.findById(request.getSongId())
            .orElseThrow(SongNotExistException::new);

        final Integer startSecond = request.getStartSecond();
        final PartLength partLength = PartLength.findBySecond(request.getLength());
        final Part part = Part.notPersisted(startSecond, partLength, song);

        if (song.isUniquePart(part)) {
            addPartAndVote(song, part);
            return;
        }
        voteToExistPart(song, startSecond, partLength);
    }

    private void addPartAndVote(final Song song, final Part part) {
        song.addPart(part);
        partRepository.save(part);

        final Vote newVote = Vote.notPersisted(part);
        part.vote(newVote);
        voteRepository.save(newVote);
    }

    private void voteToExistPart(final Song song, final int start, final PartLength length) {
        final Part part = song.getSameLengthPartStartAt(start, length)
            .orElseThrow(PartException.PartNotExistException::new);

        final Vote newVote = Vote.notPersisted(part);
        part.vote(newVote);
        voteRepository.save(newVote);
    }

    public KillingPartResponse showTopKillingPart(final Long songId) {
        final Song song = songRepository.findById(songId)
            .orElseThrow(SongNotExistException::new);

        final Optional<Part> topKillingPart = song.getTopKillingPart();

        return topKillingPart.map(KillingPartResponse::from)
            .orElseGet(KillingPartResponse::empty);
    }

    public KillingPartsResponse showKillingParts(final Long songId) {
        final Song song = songRepository.findById(songId)
            .orElseThrow(SongNotExistException::new);

        final List<Part> killingParts = song.getKillingParts();

        return KillingPartsResponse.of(killingParts);
    }
}
