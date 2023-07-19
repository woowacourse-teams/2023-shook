package shook.shook.song.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import shook.shook.part.domain.Part;
import shook.shook.part.domain.PartLength;
import shook.shook.part.domain.Vote;
import shook.shook.part.exception.PartException;

class PartsTest {

    void votePart(final Part part, final List<Vote> votes) {
        for (final Vote vote : votes) {
            part.vote(vote);
        }
    }

    @DisplayName("생성할 때 중복된 파트가 존재시 예외를 던진다.")
    @Test
    void create_fail_duplicatePartExist() {
        //given
        final Song song = new Song("제목", "비디오URL", "가수", 30);
        final Part firstPart = Part.saved(1L, 5, PartLength.SHORT, song);
        final Part secondPart = Part.forSave(5, PartLength.SHORT, song);

        final Parts parts = new Parts();
        parts.addPart(firstPart);

        //when
        //then
        assertThatThrownBy(() -> parts.addPart(secondPart))
            .isInstanceOf(PartException.DuplicateStartAndLengthException.class);
    }

    @DisplayName("가장 인기있는 킬링파트 하나를 반환한다.")
    @Test
    void getTopKillingPart() {
        //given
        final Song song = new Song("제목", "비디오URL", "가수", 30);
        final Part firstPart = Part.saved(1L, 5, PartLength.SHORT, song);
        final Part secondPart = Part.saved(2L, 14, PartLength.SHORT, song);
        firstPart.vote(Vote.saved(1L, firstPart));
        firstPart.vote(Vote.saved(2L, firstPart));
        secondPart.vote(Vote.saved(3L, secondPart));

        final Parts parts = new Parts();
        parts.addPart(firstPart, secondPart);

        //when
        final Optional<Part> topKillingPart = parts.getTopKillingPart();

        //then
        assertThat(topKillingPart).isPresent();
        assertThat(topKillingPart.get()).usingRecursiveComparison().isEqualTo(firstPart);
    }

    @DisplayName("킬링파트들을 인기있는 순으로 3개 반환한다.")
    @Nested
    class GetKillingParts {

        @DisplayName("3개가 존재할 때.")
        @Test
        void enough() {
            //given
            final Song song = new Song("제목", "비디오URL", "가수", 30);
            final Part firstPart = Part.saved(1L, 5, PartLength.SHORT, song);
            final Part secondPart = Part.saved(2L, 6, PartLength.SHORT, song);
            final Part thirdPart = Part.saved(3L, 7, PartLength.SHORT, song);
            final Part fourthPart = Part.saved(4L, 8, PartLength.SHORT, song);
            votePart(fourthPart, List.of(
                Vote.saved(1L, fourthPart),
                Vote.saved(2L, fourthPart),
                Vote.saved(3L, fourthPart)
            ));
            votePart(firstPart,
                List.of(Vote.saved(4L, firstPart), Vote.saved(5L, firstPart)));
            votePart(thirdPart, List.of(Vote.saved(6L, thirdPart)));

            final Parts parts = new Parts();

            parts.addPart(firstPart, secondPart, thirdPart, fourthPart);

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

            final Part firstPart = Part.saved(1L, 5, PartLength.SHORT, song);
            final Part secondPart = Part.saved(2L, 6, PartLength.SHORT, song);
            votePart(firstPart,
                List.of(Vote.saved(4L, firstPart), Vote.saved(5L, firstPart)));
            votePart(secondPart, List.of(Vote.saved(6L, secondPart)));

            final Parts parts = new Parts();

            parts.addPart(firstPart, secondPart);

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
            final Parts parts = new Parts();

            //when
            final List<Part> killingParts = parts.getKillingParts();

            //then
            assertThat(killingParts).isEmpty();
        }
    }

    @DisplayName("파트의 순위를 반환한다.")
    @Nested
    class GetRank {

        @DisplayName("파트가 존재할 때")
        @Test
        void exist() {
            //given
            final Song song = new Song("제목", "비디오URL", "가수", 30);
            final Part firstPart = Part.saved(1L, 5, PartLength.SHORT, song);
            final Part secondPart = Part.saved(2L, 6, PartLength.SHORT, song);
            final Part thirdPart = Part.saved(3L, 7, PartLength.SHORT, song);
            final Part fourthPart = Part.saved(4L, 8, PartLength.SHORT, song);
            votePart(fourthPart, List.of(
                Vote.saved(1L, fourthPart),
                Vote.saved(2L, fourthPart),
                Vote.saved(3L, fourthPart)
            ));
            votePart(firstPart, List.of(
                Vote.saved(4L, firstPart),
                Vote.saved(5L, firstPart))
            );
            votePart(thirdPart, List.of(Vote.saved(6L, thirdPart)));

            final Parts parts = new Parts();

            parts.addPart(firstPart, secondPart, thirdPart, fourthPart);

            //when
            final int firstPartRank = parts.getRank(firstPart);
            final int secondPartRank = parts.getRank(secondPart);

            //then
            assertThat(firstPartRank).isEqualTo(2);
            assertThat(secondPartRank).isEqualTo(4);
        }

        @DisplayName("파트가 존재하지 않으면 예외가 발생한다.")
        @Test
        void notExist() {
            //given
            final Song song = new Song("제목", "비디오URL", "가수", 30);
            final Part part = Part.saved(1L, 5, PartLength.SHORT, song);

            //when
            //then
            assertThatThrownBy(() -> song.getRank(part))
                .isInstanceOf(PartException.PartNotExistException.class);
        }
    }
}
