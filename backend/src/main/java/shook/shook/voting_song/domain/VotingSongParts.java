package shook.shook.voting_song.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shook.shook.part.exception.PartException;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Embeddable
public class VotingSongParts {

    @OneToMany(mappedBy = "votingSong")
    private final List<VotingSongPart> votingSongParts = new ArrayList<>();

    public void addPart(final VotingSongPart votingSongPart) {
        validatePart(votingSongPart);
        this.votingSongParts.add(votingSongPart);
    }

    private void validatePart(final VotingSongPart newVotingSongPart) {
        if (votingSongParts.contains(newVotingSongPart) || !isUniquePart(newVotingSongPart)) {
            throw new PartException.DuplicateStartAndLengthException(
                Map.of(
                    "StartSecond", String.valueOf(newVotingSongPart.getStartSecond()),
                    "PartLength", String.valueOf(newVotingSongPart.getLength())
                )
            );
        }
    }

    public boolean isUniquePart(final VotingSongPart newVotingSongPart) {
        return votingSongParts.stream()
            .noneMatch(votingSongPart -> votingSongPart.hasEqualStartAndLength(newVotingSongPart));
    }

    public Optional<VotingSongPart> getSameLengthPartStartAt(final VotingSongPart other) {
        return votingSongParts.stream()
            .filter(other::hasEqualStartAndLength)
            .findFirst();
    }

    public List<VotingSongPart> getVotingSongParts() {
        return new ArrayList<>(votingSongParts);
    }
}
