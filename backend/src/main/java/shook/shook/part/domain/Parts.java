package shook.shook.part.domain;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import shook.shook.part.exception.PartsException;

public class Parts {

    private final List<Part> parts;
    private boolean sorted = false;

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
        sort();

        if (parts.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(parts.get(0));
    }

    private void sort() {
        if (sorted) {
            return;
        }
        parts.sort(Comparator.comparing(Part::getVoteCount).reversed());
        sorted = true;
    }

    public List<Part> getKillingParts() {
        sort();

        if (parts.size() <= 3) {
            return new ArrayList<>(parts);
        }
        return parts.subList(0, 3);
    }
}
