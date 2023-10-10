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
    INVALID_AUTHORIZATION_CODE(1003, "올바르지 않은 authorization code 입니다."),
    INVALID_ACCESS_TOKEN(1004, "잘못된 구글 accessToken 입니다."),
    GOOGLE_SERVER_EXCEPTION(1005, "구글 서버에서 오류가 발생했습니다."),
    KAKAO_SERVER_EXCEPTION(1006, "카카오 서버에서 오류가 발생했습니다."),
    REFRESH_TOKEN_NOT_FOUND_EXCEPTION(1007, "accessToken 을 재발급하기 위해서는 refreshToken 이 필요합니다."),
    ACCESS_TOKEN_NOT_FOUND(1008, "accessToken이 필요합니다."),
    UNAUTHENTICATED_EXCEPTION(1009, "권한이 없는 요청입니다."),
    NOT_FOUND_OAUTH(1010, "현재 지원하지 않는 OAuth 요청입니다."),
    INVALID_REFRESH_TOKEN(1011, "존재하지 않는 refreshToken 입니다."),
    TOKEN_PAIR_NOT_MATCHING_EXCEPTION(1012, "올바르지 않은 TokenPair 입니다."),

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

    NOT_POSITIVE_SONG_LENGTH(3001, "노래 길이는 0보다 커야합니다."),
    SONG_NOT_EXIST(3002, "존재하지 않는 노래입니다."),
    EMPTY_SONG_TITLE(3003, "노래 제목은 비어있을 수 없습니다."),
    TOO_LONG_TITLE(3004, "노래 제목은 100자를 넘길 수 없습니다."),
    EMPTY_VIDEO_ID(3005, "비디오 ID는 비어있을 수 없습니다."),
    INCORRECT_VIDEO_ID_LENGTH(3006, "비디오 ID는 11자 입니다."),
    EMPTY_SONG_IMAGE_URL(3007, "노래 앨범 커버는 비어있을 수 없습니다."),
    TOO_LONG_SONG_IMAGE_URL(3008, "노래 앨범 커버 URL은 65,356자를 넘길 수 없습니다."),
    EMPTY_SINGER_NAME(3009, "가수 이름은 비어있을 수 없습니다."),
    TOO_LONG_SINGER_NAME(3010, "가수 이름은 50글자를 넘길 수 없습니다."),
    CAN_NOT_READ_SONG_DATA_FILE(3011, "노래 데이터 파일을 읽을 수 없습니다."),
    SONG_ALREADY_EXIST(3012, "등록하려는 노래가 이미 존재합니다."),
    WRONG_GENRE_TYPE(3013, "잘못된 장르 타입입니다."),
    EMPTY_ARTIST_PROFILE_URL(3014, "가수 프로필 이미지는 비어있을 수 없습니다."),
    TOO_LONG_ARTIST_PROFILE_URL(3015, "가수 프로필 이미지URL은 65,356자를 넘길 수 없습니다."),
    EMPTY_ARTIST_SYNONYM(3016, "가수 동의어는 비어있을 수 없습니다."),
    TOO_LONG_ARTIST_SYNONYM(3017, "가수 동의어는 255자를 넘길 수 없습니다.."),


    // 4000: 투표

    VOTING_PART_START_LESS_THAN_ZERO(4001, "파트의 시작 초는 0보다 작을 수 없습니다."),
    VOTING_PART_START_OVER_SONG_LENGTH(4002, "파트의 시작 초는 노래 길이를 초과할 수 없습니다."),
    VOTING_PART_END_OVER_SONG_LENGTH(4003, "파트의 끝 초는 노래 길이를 초과할 수 없습니다."),
    INVALID_VOTING_PART_LENGTH(4004, "파트의 길이는 5초 이상, 15초 이하여야 합니다."),
    VOTING_PART_DUPLICATE_START_AND_LENGTH_EXCEPTION(4005,
                                                     "한 노래에 동일한 파트를 두 개 이상 등록할 수 없습니다."),
    VOTING_SONG_PART_NOT_EXIST(4006, "투표 대상 파트가 존재하지 않습니다."),

    VOTING_SONG_PART_FOR_OTHER_SONG(4007, "해당 파트는 다른 노래의 파트입니다."),
    VOTING_SONG_NOT_EXIST(4008, "존재하지 않는 투표 노래입니다."),
    VOTE_FOR_OTHER_PART(4009, "해당 투표는 다른 파트에 대한 투표입니다."),
    DUPLICATE_VOTE_EXIST(4010, "중복된 투표입니다."),

    // 5000: 사용자

    EMPTY_EMAIL(5001, "이메일은 비어있을 수 없습니다."),
    TOO_LONG_EMAIL(5002, "이메일은 100자를 초과할 수 없습니다."),
    INVALID_EMAIL_FORM(5003, "이메일 형식에 맞지 않습니다."),
    EMPTY_NICKNAME(5004, "닉네임은 비어있을 수 없습니다."),
    TOO_LONG_NICKNAME(5005, "닉네임은 100자를 초과할 수 없습니다."),
    EXIST_MEMBER(5006, "이미 회원가입 된 멤버입니다."),
    MEMBER_NOT_EXIST(5007, "존재하지 않는 멤버입니다."),

    REQUEST_BODY_VALIDATION_FAIL(10001, ""),
    WRONG_REQUEST_URL(10002, "URL의 pathVariable 은 비어있을 수 없습니다."),

    INTERNAL_SERVER_ERROR(10003, "알 수 없는 서버 오류가 발생했습니다. 관리자에게 문의해주세요."),

    // 6000: 멤버 파트

    NEGATIVE_START_SECOND(6001, "멤버 파트의 시작 초는 0보다 작을 수 없습니다."),
    MEMBER_PART_END_OVER_SONG_LENGTH(6002, "파트의 끝 초는 노래 길이를 초과할 수 없습니다."),
    MEMBER_PART_ALREADY_EXIST(6003, "멤버 파트가 이미 존재합니다."),
    ;

    private final int code;
    private final String message;
}
