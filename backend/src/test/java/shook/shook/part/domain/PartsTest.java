package shook.shook.part.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import shook.shook.part.exception.PartsException;
import shook.shook.song.domain.Song;

class PartsTest {

    @DisplayName("생성할 때 중복된 파트가 존재시 예외를 던진다.")
    @Test
    void create_fail_duplicatePartExist() {
        //given
        final Song song = new Song("제목", "비디오URL", "가수", 30);
        final Part firstPart = Part.persisted(1L, 5, PartLength.SHORT, song);
        final Part secondPart = Part.persisted(1L, 6, PartLength.SHORT, song);

        //when
        //then
        assertThatThrownBy(() -> new Parts(List.of(firstPart, secondPart)))
            .isInstanceOf(PartsException.DuplicatePartExistException.class);
    }

    @DisplayName("가장 인기있는 킬링파트 하나를 반환한다.")
    @Test
    void getBestKillingPart() {
        //given
        final Song song = new Song("제목", "비디오URL", "가수", 30);
        final Part firstPart = Part.persisted(1L, 5, PartLength.SHORT, song);
        final Part secondPart = Part.persisted(2L, 14, PartLength.SHORT, song);
        firstPart.vote(Vote.persisted(1L, firstPart));
        firstPart.vote(Vote.persisted(2L, firstPart));
        secondPart.vote(Vote.persisted(3L, secondPart));
        final Parts parts = new Parts(List.of(firstPart, secondPart));

        //when
        final Optional<Part> bestKillingPart = parts.getTopKillingPart();

        //then
        assertThat(bestKillingPart).isPresent();
        assertThat(bestKillingPart.get()).usingRecursiveComparison().isEqualTo(firstPart);
    }

    @DisplayName("킬링파트들을 인기있는 순으로 3개 반환한다.")
    @Nested
    class GetKillingParts {

        @DisplayName("3개가 존재할 때.")
        @Test
        void enough() {
            //given
            final Song song = new Song("제목", "비디오URL", "가수", 30);
            final Part firstPart = Part.persisted(1L, 5, PartLength.SHORT, song);
            final Part secondPart = Part.persisted(2L, 6, PartLength.SHORT, song);
            final Part thirdPart = Part.persisted(3L, 7, PartLength.SHORT, song);
            final Part fourthPart = Part.persisted(4L, 8, PartLength.SHORT, song);
            votePart(fourthPart, List.of(
                Vote.persisted(1L, fourthPart),
                Vote.persisted(2L, fourthPart),
                Vote.persisted(3L, fourthPart)
            ));
            votePart(firstPart,
                List.of(Vote.persisted(4L, firstPart), Vote.persisted(5L, firstPart)));
            votePart(thirdPart, List.of(Vote.persisted(6L, thirdPart)));

            final Parts parts = new Parts(List.of(firstPart, secondPart, thirdPart, fourthPart));

            //when
            final List<Part> killingParts = parts.getKillingParts();

            //then
            assertThat(killingParts).usingRecursiveComparison()
                .isEqualTo(List.of(fourthPart, firstPart, thirdPart));
        }

        @DisplayName("킬링파트들을 인기있는 순으로 3개 반환한다.(3개보다 작을 때)")
        @Test
        void onlyTwo() {
            //given
            final Song song = new Song("제목", "비디오URL", "가수", 30);

            final Part firstPart = Part.persisted(1L, 5, PartLength.SHORT, song);
            final Part secondPart = Part.persisted(2L, 6, PartLength.SHORT, song);
            votePart(firstPart,
                List.of(Vote.persisted(4L, firstPart), Vote.persisted(5L, firstPart)));
            votePart(secondPart, List.of(Vote.persisted(6L, secondPart)));

            final Parts parts = new Parts(List.of(firstPart, secondPart));

            //when
            final List<Part> killingParts = parts.getKillingParts();

            //then
            assertThat(killingParts).usingRecursiveComparison()
                .isEqualTo(List.of(firstPart, secondPart));
        }

        @DisplayName("킬링파트들을 인기있는 순으로 3개 반환한다.(킬링파트가 없을 때)")
        @Test
        void notExist() {
            //given
            final Parts parts = new Parts(Collections.emptyList());

            //when
            final List<Part> killingParts = parts.getKillingParts();

            //then
            assertThat(killingParts).isEmpty();
        }
    }

    void votePart(final Part part, final List<Vote> votes) {
        for (final Vote vote : votes) {
            part.vote(vote);
        }
    }
}
