package shook.shook.globalexception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum ErrorCode {

    // 1000: 로그인, 인증, 인가
    NOT_ISSUED_TOKEN(1000, "파싱에 실패한 토큰입니다."),
    EXPIRED_TOKEN(1001, "유효기간이 만료된 토큰입니다."),
    INVALID_EMAIL(1002, "유효하지 않은 이메일입니다."),
    INVALID_AUTHORIZATION_CODE(1003, "올바르지 않은 authorization code 입니다."),
    INVALID_ACCESS_TOKEN(1004, "잘못된 구글 accessToken 입니다."),
    GOOGLE_SERVER_EXCEPTION(1005, "구글 서버에서 오류가 발생했습니다."),

    // 2000: 킬링파트 - 좋아요, 댓글
    EMPTY_KILLING_PART_COMMENT(2001, "킬링파트 댓글은 비어있을 수 없습니다."),
    TOO_LONG_KILLING_PART_COMMENT(2002, "킬링파트 댓글 길이는 최대 200 글자여야 합니다."),
    KILLING_PART_COMMENT_FOR_OTHER_PART(2003, "해당 댓글은 다른 킬링파트의 댓글입니다."),
    DUPLICATE_COMMENT_EXIST(2004, "동일한 킬링파트 댓글이 존재합니다."),

    KILLING_PART_NOT_EXIST(2005, "킬링파트가 존재하지 않습니다."),
    KILLING_PART_SONG_NOT_UPDATABLE(2006, "이미 다른 노래의 킬링파트에 속해있습니다."),
    SONG_MAX_KILLING_PART_EXCEEDED(2007, "노래의 킬링파트는 3개를 초과할 수 없습니다."),

    LIKE_FOR_OTHER_KILLING_PART(2008, "다른 킬링파트에 대한 좋아요입니다."),
    EMPTY_LIKE_EXCEPTION(2009, "비어있는 좋아요를 킬링파트에 등록할 수 없습니다."),

    KILLING_PARTS_OUT_OF_SIZE(2010, "킬링파트는 3개여야 합니다."),
    EMPTY_KILLING_PARTS(2011, "노래의 킬링파트는 비어있을 수 없습니다."),

    // 3000: 노래
    NEGATIVE_SONG_LENGTH(3001, "노래 길이는 0보다 커야합니다."),
    SONG_NOT_EXIST(3002, "존재하지 않는 노래입니다."),
    EMPTY_SONG_TITLE(3003, "노래 제목은 비어있을 수 없습니다."),
    TOO_LONG_TITLE(3004, "노래 제목은 100자를 넘길 수 없습니다."),
    EMPTY_VIDEO_URL(3005, "비디오 URL은 비어있을 수 없습니다."),
    TOO_LONG_VIDEO_URL(3006, "비디오 URL은 65,356자를 넘길 수 없습니다."),
    EMPTY_SONG_IMAGE_URL(3007, "노래 앨범 커버는 비어있을 수 없습니다."),
    TOO_LONG_SONG_IMAGE_URL(3008, "노래 앨범 커버 URL은 65,356자를 넘길 수 없습니다."),
    NULL_OR_EMPTY_SINGER_NAME(3009, "가수 이름은 비어있을 수 없습니다."),
    TOO_LONG_SINGER_NAME(3010, "가수 이름은 50글자를 넘길 수 없습니다."),

    // 4000: 투표
    VOTING_PART_START_LESS_THAN_ZERO(4001, "파트의 시작 초는 0보다 작을 수 없습니다."),
    VOTING_PART_START_OVER_SONG_LENGTH(4002, "파트의 시작 초는 노래 길이를 초과할 수 없습니다."),
    VOTING_PART_END_OVER_SONG_LENGTH(4003, "파트의 끝 초는 노래 길이를 초과할 수 없습니다."),
    INVALID_VOTING_PART_LENGTH(4004, "파트의 길이는 5, 10, 15초 중 하나여야합니다."),
    VOTING_PART_DUPLICATE_START_AND_LENGTH_EXCEPTION(4005,
        "한 노래에 동일한 파트를 두 개 이상 등록할 수 없습니다."), //500

    VOTING_SONG_PART_NOT_EXIST(4006, "투표 대상 파트가 존재하지 않습니다."),
    VOTING_SONG_PART_FOR_OTHER_SONG(4007, "해당 파트는 다른 노래의 파트입니다."),
    VOTING_SONG_NOT_EXIST(4008, "존재하지 않는 투표 노래입니다."),
    VOTE_FOR_OTHER_PART(4009, "해당 투표는 다른 파트에 대한 투표입니다."),
    DUPLICATE_VOTE_EXIST(4010, "중복된 투표입니다."),

    REQUEST_BODY_VALIDATION_FAIL(10001, "필드명: %s, 오류 메세지: %s"),
    WRONG_REQUEST_URL(10002, "URL의 pathVariable 은 비어있을 수 없습니다.");

    private final int code;
    private final String message;
}
