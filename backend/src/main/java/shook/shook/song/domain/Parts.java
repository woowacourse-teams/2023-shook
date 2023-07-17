package shook.shook.song.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shook.shook.part.domain.Part;
import shook.shook.part.exception.PartException;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Embeddable
public class Parts {

    private static final int KILLING_PART_COUNT = 3;

    @OneToMany(mappedBy = "song")
    private Set<Part> parts = new HashSet<>();

    public void addPart(final Part part) {
        validatePart(part);
        this.parts.add(part);
    }

    public void addPart(final Part... newParts) {
        for (final Part part : newParts) {
            addPart(part);
        }
    }

    private void validatePart(final Part newPart) {
        if (parts.contains(newPart) || !isUniquePart(newPart)) {
            throw new PartException.DuplicateStartAndLengthException();
        }
    }

    public boolean isUniquePart(final Part newPart) {
        return parts.stream().noneMatch((part) -> part.hasEqualStartAndLength(newPart));
    }

    public Optional<Part> getTopKillingPart() {
        if (parts.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(sortedByVoteCountDescending().get(0));
    }

    public List<Part> getKillingParts() {
        final List<Part> sortedParts = sortedByVoteCountDescending();

        if (sortedParts.size() <= KILLING_PART_COUNT) {
            return sortedParts;
        }
        return sortedParts.subList(0, KILLING_PART_COUNT);
    }

    private List<Part> sortedByVoteCountDescending() {
        return parts.stream()
            .sorted(Comparator.comparing(Part::getVoteCount).reversed())
            .toList();
    }

    public Optional<Part> getSameLengthPartStartAt(final Part other) {
        return parts.stream()
            .filter(other::hasEqualStartAndLength)
            .findFirst();
    }

    public List<Part> getParts() {
        return new ArrayList<>(parts);
    }
}
