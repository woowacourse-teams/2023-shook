package shook.shook.part.domain;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import shook.shook.part.exception.PartsException;

public class Parts {

    private static final int KILLINGPART_COUNT = 3;
    private final List<Part> parts;

    public Parts(final List<Part> parts) {
        validateDistinct(parts);
        this.parts = new ArrayList<>(parts);
    }

    private void validateDistinct(final List<Part> parts) {
        final long uniqueCount = parts.stream()
            .distinct()
            .count();
        if (parts.size() != uniqueCount) {
            throw new PartsException.DuplicatePartExistException();
        }
    }

    public Optional<Part> getTopKillingPart() {
        if (parts.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(sortedByVoteCountDescending().get(0));
    }

    private List<Part> sortedByVoteCountDescending() {
        return parts.stream()
            .sorted(Comparator.comparing(Part::getVoteCount).reversed())
            .toList();
    }

    public List<Part> getKillingParts() {
        final List<Part> sortedParts = sortedByVoteCountDescending();

        if (sortedParts.size() <= KILLINGPART_COUNT) {
            return sortedParts;
        }
        return sortedParts.subList(0, KILLINGPART_COUNT);
    }
}
