import { styled } from 'styled-components';
import link from '@/assets/icon/link.svg';
import shook from '@/assets/icon/shook.svg';
import people from '@/assets/icon/user-group.svg';
import Flex from '@/shared/components/Flex';
import useToastContext from '@/shared/components/Toast/hooks/useToastContext';
import { toPlayingTimeText, secondsToMinSec } from '@/shared/utils/convertTime';
import copyClipboard from '@/shared/utils/copyClipBoard';
import type { KillingPart } from '@/shared/types/song';

interface KillingPartInfoProps {
  killingPart: KillingPart | undefined;
}

const KillingPartInfo = ({ killingPart }: KillingPartInfoProps) => {
  const { showToast } = useToastContext();

  if (!killingPart) return;

  const { likeCount, start, end, partVideoUrl } = killingPart;

  const shareUrl = () => {
    copyClipboard(partVideoUrl);
    showToast('클립보드에 영상링크가 복사되었습니다.');
  };

  const playingTimeText = toPlayingTimeText(start, end);
  const { minute: startMin, second: startSec } = secondsToMinSec(start);
  const { minute: endMin, second: endSec } = secondsToMinSec(end);

  return (
    <Wrapper>
      <Container>
        <TimeWrapper>
          <Img src={shook} alt="" />
          <p
            tabIndex={0}
            aria-label={`킬링파트 구간 ${startMin}분 ${startSec}초부터 ${endMin}분 ${endSec}초`}
          >
            {playingTimeText}
          </p>
        </TimeWrapper>

        <RestWrapper>
          <VoteBox tabIndex={0} aria-label={`${likeCount}명 좋아요~`}>
            <Img src={people} alt="" />
            <VoteCount>{likeCount}votes</VoteCount>
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

const VoteCount = styled.p`
  font-size: 14px;
  color: ${({ theme: { color } }) => color.subText};
  letter-spacing: 1px;
`;

const RestWrapper = styled.div`
  display: flex;
  column-gap: 8px;
  align-items: center;
`;

const TimeWrapper = styled.div`
  display: flex;
  justify-content: center;

  font-weight: 700;
  color: ${({ theme: { color } }) => color.primary};
  letter-spacing: 1px;
`;

const Wrapper = styled(Flex)`
  flex-direction: column;
  color: white;
`;

const Container = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;

  margin: 0 8px;
  padding: 16px;

  border: 1px solid ${({ theme }) => theme.color.secondary};
  border-radius: 8px;
`;

const VoteBox = styled.div`
  display: flex;
  justify-content: center;
`;

const ShareBox = styled.button`
  cursor: pointer;

  display: flex;
  align-items: center;
  justify-content: center;

  width: 30px;
  height: 30px;

  background-color: ${({ theme }) => theme.color.white};
  border-radius: 50%;
`;

const Img = styled.img`
  width: 16px;
  height: 16px;
`;
