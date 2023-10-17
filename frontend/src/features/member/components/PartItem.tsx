import { useNavigate } from 'react-router-dom';
import styled from 'styled-components';
import link from '@/assets/icon/link.svg';
import shook from '@/assets/icon/shook.svg';
import { useAuthContext } from '@/features/auth/components/AuthProvider';
import Thumbnail from '@/features/songs/components/Thumbnail';
import GENRES from '@/features/songs/constants/genres';
import Spacing from '@/shared/components/Spacing';
import useToastContext from '@/shared/components/Toast/hooks/useToastContext';
import { GA_ACTIONS, GA_CATEGORIES } from '@/shared/constants/GAEventName';
import ROUTE_PATH from '@/shared/constants/path';
import sendGAEvent from '@/shared/googleAnalytics/sendGAEvent';
import { secondsToMinSec, toPlayingTimeText } from '@/shared/utils/convertTime';
import copyClipboard from '@/shared/utils/copyClipBoard';
import type { LikeKillingPart } from './MyPartList';

const { BASE_URL } = process.env;

type PartItemProps = LikeKillingPart;

const PartItem = ({ songId, albumCoverUrl, title, singer, start, end }: PartItemProps) => {
  const { showToast } = useToastContext();
  const { user } = useAuthContext();
  const navigate = useNavigate();

  const shareUrl: React.MouseEventHandler = (e) => {
    e.stopPropagation();
    sendGAEvent({
      action: GA_ACTIONS.COPY_URL,
      category: GA_CATEGORIES.MY_PAGE,
      memberId: user?.memberId,
    });

    copyClipboard(
      `${BASE_URL?.replace('/api', '')}/${ROUTE_PATH.SONG_DETAILS}${songId}/${GENRES.ALL}`
    );
    showToast('클립보드에 영상링크가 복사되었습니다.');
  };

  const goToSongDetailListPage = () => {
    navigate(`/${ROUTE_PATH.SONG_DETAILS}${songId}/${GENRES.ALL}`);
  };

  const { minute: startMin, second: startSec } = secondsToMinSec(start);
  const { minute: endMin, second: endSec } = secondsToMinSec(end);

  return (
    <PartItemGrid onClick={goToSongDetailListPage}>
      <Thumbnail src={albumCoverUrl} alt={`${title}-${singer}`} />
      <SongTitle>{title}</SongTitle>
      <Singer>{singer}</Singer>
      <TimeWrapper>
        <Shook src={shook} alt="" />
        <Spacing direction="horizontal" size={4} />
        <p
          tabIndex={0}
          aria-label={`킬링파트 구간 ${startMin}분 ${startSec}초부터 ${endMin}분 ${endSec}초`}
        >
          {toPlayingTimeText(start, end)}
        </p>
        <Spacing direction="horizontal" size={10} />
      </TimeWrapper>
      <ShareButton onClick={shareUrl}>
        <Share src={link} alt="영상 링크 공유하기" />
      </ShareButton>
    </PartItemGrid>
  );
};

const PartItemGrid = styled.li`
  cursor: pointer;

  display: grid;
  grid-template:
    'thumbnail title _' 26px
    'thumbnail singer share' 26px
    'thumbnail info share' 18px
    / 70px auto 26px;
  column-gap: 8px;

  width: 100%;
  padding: 6px 10px;

  color: ${({ theme: { color } }) => color.white};
  text-align: start;

  &:hover,
  &:focus {
    background-color: ${({ theme }) => theme.color.secondary};
  }
`;

const SongTitle = styled.div`
  overflow: hidden;
  grid-area: title;

  font-size: 16px;
  font-weight: 700;
  text-overflow: ellipsis;
  white-space: nowrap;
`;

const Singer = styled.div`
  overflow: hidden;
  grid-area: singer;

  font-size: 12px;
  text-overflow: ellipsis;
  white-space: nowrap;
`;

const TimeWrapper = styled.div`
  display: flex;
  grid-area: info;

  font-size: 14px;
  font-weight: 700;
  color: ${({ theme: { color } }) => color.primary};
  letter-spacing: 1px;
`;

const ShareButton = styled.button`
  grid-area: share;
  width: 26px;
  height: 26px;
`;

const Share = styled.img`
  padding: 2px;
  background-color: white;
  border-radius: 50%;
`;

const Shook = styled.img`
  width: 16px;
  height: 18px;
  border-radius: 50%;
`;

export default PartItem;
