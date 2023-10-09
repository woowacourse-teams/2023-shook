package shook.shook.song.domain.killingpart;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import shook.shook.member.domain.Member;
import shook.shook.part.domain.PartLength;
import shook.shook.song.domain.Artist;
import shook.shook.song.domain.ArtistName;
import shook.shook.song.domain.Genre;
import shook.shook.song.domain.KillingParts;
import shook.shook.song.domain.ProfileImageUrl;
import shook.shook.song.domain.Song;
import shook.shook.song.exception.SongException;
import shook.shook.song.exception.killingpart.KillingPartCommentException;
import shook.shook.song.exception.killingpart.KillingPartException;
import shook.shook.song.exception.killingpart.KillingPartLikeException;

class KillingPartTest {

    private static final Song EMPTY_SONG = null;
    private static final Member MEMBER = new Member("email@naver.com", "nickname");

    @DisplayName("Id가 같은 킬링파트는 동등성 비교에 참을 반환한다.")
    @Test
    void equals_true() {
        //given
        final KillingPart firstPart = KillingPart.saved(1L, 4, PartLength.SHORT, EMPTY_SONG);
        final KillingPart secondPart = KillingPart.saved(1L, 14, PartLength.SHORT, EMPTY_SONG);

        //when
        final boolean equals = firstPart.equals(secondPart);

        //then
        assertThat(equals).isTrue();
    }

    @DisplayName("Id가 없는 경우 동등성 비교에 항상 거짓을 반환한다.")
    @Nested
    class Equals {

        @DisplayName("비교하는 객체 중 하나만 없을 때")
        @Test
        void equals_false_nullId() {
            //given
            final KillingPart firstPart = KillingPart.saved(null, 4, PartLength.SHORT, EMPTY_SONG);
            final KillingPart secondPart = KillingPart.saved(1L, 14, PartLength.SHORT, EMPTY_SONG);

            //when
            final boolean equals = firstPart.equals(secondPart);

            //then
            assertThat(equals).isFalse();
        }

        @DisplayName("비교하는 객체 둘다 없을 때")
        @Test
        void equals_false_bothNullId() {
            //given
            final KillingPart firstPart = KillingPart.saved(null, 4, PartLength.SHORT, EMPTY_SONG);
            final KillingPart secondPart = KillingPart.saved(null, 14, PartLength.SHORT,
                EMPTY_SONG);

            //when
            final boolean equals = firstPart.equals(secondPart);

            //then
            assertThat(equals).isFalse();
        }
    }

    @DisplayName("킬링파트의 시작과 끝을 담은 URL Path parameter를 반환한다.")
    @Test
    void getStartAndEndUrlPathParameterOfKillingPart() {
        //given
        final KillingPart killingPart = KillingPart.saved(1L, 5, PartLength.SHORT, EMPTY_SONG);

        //when
        final String startAndEndUrlPathParameter = killingPart.getStartAndEndUrlPathParameter();

        //then
        final int startSecond = killingPart.getStartSecond();
        final int endSecond = killingPart.getEndSecond();
        final String playDuration = String.format("?start=%d&end=%d", startSecond, endSecond);
        assertThat(startAndEndUrlPathParameter).isEqualTo(playDuration);
    }

    @DisplayName("킬링파트에 댓글을 추가한다.")
    @Nested
    class AddComment {

        @DisplayName("성공적으로 추가된다.")
        @Test
        void success() {
            //given
            final KillingPart killingPart = KillingPart.saved(1L, 5, PartLength.SHORT, EMPTY_SONG);

            //when
            killingPart.addComment(KillingPartComment.saved(1L, killingPart, "댓글 내용", MEMBER));

            //then
            assertThat(killingPart.getComments()).hasSize(1);
        }

        @DisplayName("다른 킬링파트의 댓글을 추가한 경우 예외가 발생한다.")
        @Test
        void belongToOtherPart() {
            //given
            final KillingPart firstPart = KillingPart.saved(1L, 5, PartLength.SHORT, EMPTY_SONG);
            final KillingPart secondPart = KillingPart.saved(2L, 5, PartLength.SHORT, EMPTY_SONG);

            //when
            //then
            assertThatThrownBy(
                () -> firstPart.addComment(
                    KillingPartComment.saved(2L, secondPart, "댓글 내용", MEMBER)))
                .isInstanceOf(KillingPartCommentException.CommentForOtherPartException.class);
        }
    }

    @DisplayName("킬링파트에 노래를 설정한다.")
    @Nested
    class SetSong {

        @DisplayName("설정할 노래가 비어있다면 예외가 발생한다.")
        @Test
        void setSong_emptySong_fail() {
            // given
            final KillingPart killingPart = KillingPart.forSave(0, PartLength.STANDARD);

            // when, then
            assertThatThrownBy(() -> killingPart.setSong(EMPTY_SONG))
                .isInstanceOf(SongException.SongNotExistException.class);
        }

        @DisplayName("킬링파트에 설정하려는 노래가 이미 3개의 킬링파트를 가지고 있다면 예외가 발생한다.")
        @Test
        void setSong_alreadyRegisteredToSong_fail() {
            // given
            final KillingPart dummyKillingPart1 = KillingPart.forSave(0, PartLength.STANDARD);
            final KillingPart dummyKillingPart2 = KillingPart.forSave(0, PartLength.SHORT);
            final KillingPart dummyKillingPart3 = KillingPart.forSave(0, PartLength.LONG);
            final Artist artist = new Artist(new ProfileImageUrl("image"), new ArtistName("name"));
            final Song song = new Song(
                "title",
                "3rUPND6FG8A",
                "image_url",
                artist,
                230,
                Genre.from("댄스"),
                new KillingParts(List.of(dummyKillingPart1, dummyKillingPart2, dummyKillingPart3))
            );

            final KillingPart killingPart = KillingPart.forSave(0, PartLength.STANDARD);

            // when, then
            assertThatThrownBy(() -> killingPart.setSong(song))
                .isInstanceOf(KillingPartException.SongMaxKillingPartExceededException.class);
        }
    }

    @DisplayName("킬링파트에서 좋아요 갯수를 관리한다.")
    @Nested
    class LikeCountKillingPart {

        @DisplayName("킬링파트를 처음 생성할 때, likeCount 는 0이고 좋아요 목록이 없다.")
        @Test
        void createKillingPart_likeCount_0() {
            // given
            // when
            final KillingPart killingPart = KillingPart.saved(1L, 10, PartLength.SHORT, EMPTY_SONG);

            // then
            assertThat(killingPart.getLikeCount()).isZero();
            assertThat(killingPart.getKillingPartLikes()).isEmpty();
        }

        @DisplayName("킬링파트에 좋아요를 누를 때, 새롭게 추가되면 likeCount 갯수가 증가한다.")
        @Test
        void likeCount_updateSuccess() {
            // given
            final Member member = new Member("email@naver.com", "nickname");
            final KillingPart killingPart = KillingPart.saved(1L, 10, PartLength.SHORT, EMPTY_SONG);

            // when
            final KillingPartLike likeToAdd = new KillingPartLike(killingPart, member);
            killingPart.like(likeToAdd);

            // then
            assertThat(killingPart.getKillingPartLikes()).containsOnly(likeToAdd);
            assertThat(killingPart.getLikeCount()).isEqualTo(1);
        }

        @DisplayName("킬링파트에 좋아요를 누를 때, 동일 좋아요가 이미 존재했다면 likeCount 가 증가하지 않는다.")
        @Test
        void likeCount_updateFail() {
            // given
            final Member member = new Member("email@naver.com", "nickname");
            final KillingPart killingPart = KillingPart.saved(1L, 10, PartLength.SHORT, EMPTY_SONG);
            final KillingPartLike like = new KillingPartLike(killingPart, member);
            killingPart.like(like);

            // when
            killingPart.like(like);

            // then
            assertThat(killingPart.getKillingPartLikes()).containsOnly(like);
            assertThat(killingPart.getLikeCount()).isEqualTo(1);
        }

        @DisplayName("킬링파트에 좋아요를 취소할 때, 존재하던 것을 삭제하면 likeCount 가 감소한다.")
        @Test
        void likeCount_deleteSuccess() {
            // given
            final Member member = new Member("email@naver.com", "nickname");
            final KillingPart killingPart = KillingPart.saved(1L, 10, PartLength.SHORT, EMPTY_SONG);
            final KillingPartLike like = new KillingPartLike(killingPart, member);
            killingPart.like(like);

            // when
            killingPart.unlike(like);

            // then
            assertThat(killingPart.getKillingPartLikes()).isEmpty();
            assertThat(killingPart.getLikeCount()).isZero();
        }

        @DisplayName("킬링파트에 좋아요를 취소할 때, 존재하지 않았다면 likeCount 가 감소하지 않는다.")
        @Test
        void likeCount_deleteFail() {
            // given
            final Member member = new Member("email@naver.com", "nickname");
            final KillingPart killingPart = KillingPart.saved(1L, 10, PartLength.SHORT, EMPTY_SONG);
            final KillingPartLike like = new KillingPartLike(killingPart, member);
            killingPart.like(like);

            // when
            final Member newMember = new Member("new@naver.com", "new");
            final KillingPartLike newLike = new KillingPartLike(killingPart, newMember);

            killingPart.unlike(newLike);

            // then
            assertThat(killingPart.getKillingPartLikes()).containsExactly(like);
            assertThat(killingPart.getLikeCount()).isEqualTo(1);
        }
    }

    @DisplayName("좋아요를 등록/취소 할 때, KillingPartLike 가 null 이면 예외가 발생한다.")
    @Test
    void like_empty_fail() {
        // given
        final KillingPart killingPart = KillingPart.saved(1L, 10, PartLength.SHORT, EMPTY_SONG);

        // when, then
        assertThatThrownBy(() -> killingPart.like(null))
            .isInstanceOf(KillingPartLikeException.EmptyLikeException.class);
    }

    @DisplayName("좋아요를 등록/취소 할 때, KillingPartLike 가 다른 킬링파트에 속하면 예외가 발생한다.")
    @Test
    void like_belongsToOtherKillingPart_fail() {
        // given
        final Member member = new Member("email@naver.com", "name");
        final KillingPart killingPart = KillingPart.saved(1L, 10, PartLength.SHORT, EMPTY_SONG);
        final KillingPartLike like = new KillingPartLike(killingPart, member);

        final KillingPart other = KillingPart.saved(2L, 10, PartLength.SHORT, EMPTY_SONG);

        // when, then
        assertThatThrownBy(() -> other.like(like))
            .isInstanceOf(KillingPartLikeException.LikeForOtherKillingPartException.class);
    }

    @DisplayName("KillingPart 에 대한 사용자의 좋아요 여부를 확인한다.")
    @Test
    void likeByMember() {
        // given
        final Member member = new Member("email@naver.com", "name");
        final KillingPart killingPart = KillingPart.saved(1L, 10, PartLength.SHORT, EMPTY_SONG);
        final KillingPartLike like = new KillingPartLike(killingPart, member);
        killingPart.like(like);

        // when
        final boolean isLikedByMember = killingPart.isLikedByMember(member);

        // then
        assertThat(isLikedByMember).isTrue();
    }
}
