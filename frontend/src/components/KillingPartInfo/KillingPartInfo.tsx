import link from '@/assets/icon/link.svg';
import shook from '@/assets/icon/shook.svg';
import people from '@/assets/icon/user-group.svg';
import { getPlayingTimeText } from '@/utils/convertTime';
import copyClipboard from '@/utils/copyClipBoard';
import useToastContext from '../@common/Toast/hooks/useToastContext';
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
  killingPart: KillingPart | undefined;
}

const KillingPartInfo = ({ killingPart }: KillingPartInfoProps) => {
  const { showToast } = useToastContext();

  if (!killingPart) return;

  const { voteCount, start, end, partVideoUrl } = killingPart;

  const shareUrl = () => {
    copyClipboard(partVideoUrl);
    showToast('클립보드에 영상링크가 복사되었습니다.');
  };

  const playingTimeText = getPlayingTimeText(start, end);

  return (
    <Wrapper>
      <Container>
        <TimeWrapper>
          <Img src={shook} alt="" />
          <p>{playingTimeText}</p>
        </TimeWrapper>

        <RestWrapper>
          <VoteBox>
            <Img src={people} alt="" />
            <VoteCount>{voteCount}votes</VoteCount>
          </VoteBox>

          <ShareBox onClick={shareUrl}>
            <Img src={link} alt="영상 링크 공유하기" />
          </ShareBox>
        </RestWrapper>
      </Container>
    </Wrapper>
  );
};

export default KillingPartInfo;
