import link from '@/assets/icon/link.svg';
import shook from '@/assets/icon/shook.svg';
import people from '@/assets/icon/user-group.svg';
import useCopyClipBoard from '@/hooks/useCopyClipBoard';
import { secondsToMinSec } from '@/utils/convertTime';
import {
  Container,
  Img,
  RestWrapper,
  ShareBox,
  TimeWrapper,
  VoteBox,
  VoteCount,
  Wrapper,
} from './KillingPartInfo.style';
import type { KillingPart } from '@/types/song';

interface KillingPartInfoProps {
  killingPart: KillingPart | null;
}

const KillingPartInfo = ({ killingPart }: KillingPartInfoProps) => {
  const { copyClipboard } = useCopyClipBoard();

  if (!killingPart) return;

  const { voteCount, start, end, partVideoUrl } = killingPart;

  const shareUrl = () => {
    copyClipboard(partVideoUrl, '클립보드에 영상링크가 복사되었습니다.');
  };

  const getPlayingTime = (startSec: number, endSec: number) => {
    const timeList = [secondsToMinSec(startSec), secondsToMinSec(endSec)].flat().map((timeSec) => {
      const timeString = timeSec.toString();
      return timeString.length === 1 ? `0${timeString}` : timeString;
    });
    return `${timeList[0]}:${timeList[1]} ~ ${timeList[2]}:${timeList[3]}`;
  };

  return (
    <Wrapper>
      <Container>
        <TimeWrapper>
          <Img src={shook} alt="logo" />
          <p>{getPlayingTime(start, end)}</p>
        </TimeWrapper>

        <RestWrapper>
          <VoteBox>
            <Img src={people} alt="vote" />
            <VoteCount>{voteCount}votes</VoteCount>
          </VoteBox>

          <ShareBox onClick={shareUrl}>
            <Img src={link} alt="?" />
          </ShareBox>
        </RestWrapper>
      </Container>
    </Wrapper>
  );
};

export default KillingPartInfo;
