import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { styled } from 'styled-components';
import { useAuthContext } from '@/features/auth/components/AuthProvider';
import LoginModal from '@/features/auth/components/LoginModal';
import ROUTE_PATH from '@/shared/constants/path';
import { useOverlay } from '@/shared/hooks/useOverlay';
import KillingPartTrack from './KillingPartTrack';
import type { KillingPart, SongDetail } from '@/shared/types/song';

interface KillingPartTrackListProps {
  killingParts: SongDetail['killingParts'];
  memberPart: SongDetail['memberPart'];
  songId: number;
  nowPlayingTrack: KillingPart['id'];
  setNowPlayingTrack: React.Dispatch<React.SetStateAction<number>>;
  setCommentsPartId: React.Dispatch<React.SetStateAction<KillingPart['id']>>;
}

const KillingPartTrackList = ({
  killingParts,
  memberPart,
  songId,
  nowPlayingTrack,
  setNowPlayingTrack,
  setCommentsPartId,
}: KillingPartTrackListProps) => {
  const [myPartDetail, setMyPartDetail] = useState<SongDetail['memberPart'] | null>(memberPart);
  const { user } = useAuthContext();
  const navigate = useNavigate();
  const overlay = useOverlay();

  const openLoginModal = () => {
    overlay.open(({ isOpen, close }) => (
      <LoginModal
        isOpen={isOpen}
        closeModal={close}
        message={'로그인하여 나의 킬링파트를 등록하세요!\n등록한 노래는 마이페이지에 저장됩니다!'}
      />
    ));
  };

  const isLoggedIn = !!user;
  const goToPartCollectingPage = () => navigate(`/${ROUTE_PATH.COLLECT}/${songId}`);

  const hideMyPart = () => setMyPartDetail(null);

  return (
    <TrackList role="radiogroup">
      {killingParts.map((killingPart, i) => (
        <KillingPartTrack
          order={i + 1}
          key={killingPart.id}
          killingPart={killingPart}
          songId={songId}
          isNowPlayingTrack={i + 1 === nowPlayingTrack}
          setNowPlayingTrack={setNowPlayingTrack}
          setCommentsPartId={setCommentsPartId}
        />
      ))}

      {myPartDetail ? (
        <KillingPartTrack
          order={4}
          killingPart={memberPart}
          songId={songId}
          isNowPlayingTrack={4 === nowPlayingTrack}
          setNowPlayingTrack={setNowPlayingTrack}
          setCommentsPartId={setCommentsPartId}
          isMyKillingPart
          hideMyPart={hideMyPart}
        />
      ) : (
        <PartRegisterButton
          type="button"
          onClick={isLoggedIn ? goToPartCollectingPage : openLoginModal}
        >
          + My Part
        </PartRegisterButton>
      )}
    </TrackList>
  );
};

export default KillingPartTrackList;

export const TrackList = styled.div`
  display: flex;
  flex-direction: column;
  gap: 10px;
  width: 314px;

  @media (max-width: ${({ theme }) => theme.breakPoints.md}) {
    width: 100%;
  }
`;

const PartRegisterButton = styled.button`
  display: flex;
  align-items: center;
  justify-content: center;

  height: 60px;
  padding: 0 12px;

  background-color: ${({ theme: { color } }) => color.secondary};
  border-radius: 4px;

  @media (max-width: ${({ theme }) => theme.breakPoints.xxs}) {
    height: 46px;
  }
`;
