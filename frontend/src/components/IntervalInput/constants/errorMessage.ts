const ERROR_MESSAGE = {
  MIN_SEC: '구간(분 / 초)은 0~59 숫자만 입력 가능해요 ',
  SONG_RANGE(songMinute: number, songSecond: number) {
    return `${songMinute}분 ${songSecond}초 이하로 구간 시작점을 잡아주세요`;
  },
};

export default ERROR_MESSAGE;
