import link from '@/assets/icon/link.svg';
import shook from '@/assets/icon/shook.svg';
import people from '@/assets/icon/user-group.svg';
import useCopyClipBoard from '@/hooks/useCopyClipBoard';
import { getPlayingTimeText } from '@/utils/convertTime';
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

  const playingTimeText = getPlayingTimeText(start, end);

  return (
    <Wrapper>
      <Container>
        <TimeWrapper>
          <Img src={shook} alt="logo" />
          <p>{playingTimeText}</p>
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
