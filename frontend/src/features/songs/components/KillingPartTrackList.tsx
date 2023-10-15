import { useNavigate } from 'react-router-dom';
import { styled } from 'styled-components';
import { useAuthContext } from '@/features/auth/components/AuthProvider';
import LoginModal from '@/features/auth/components/LoginModal';
import useModal from '@/shared/components/Modal/hooks/useModal';
import ROUTE_PATH from '@/shared/constants/path';
import KillingPartTrack from './KillingPartTrack';
import type { KillingPart, SongDetail } from '@/shared/types/song';

interface KillingPartTrackListProps {
  killingParts: SongDetail['killingParts'];
  myPart: SongDetail['myPart'];
  songId: number;
  nowPlayingTrack: KillingPart['id'];
  setNowPlayingTrack: React.Dispatch<React.SetStateAction<KillingPart['id']>>;
  setCommentsPartId: React.Dispatch<React.SetStateAction<KillingPart['id']>>;
}

const KillingPartTrackList = ({
  killingParts,
  myPart,
  songId,
  nowPlayingTrack,
  setNowPlayingTrack,
  setCommentsPartId,
}: KillingPartTrackListProps) => {
  const hasMyPart = myPart !== undefined;

  const { user } = useAuthContext();
  const navigate = useNavigate();

  const isLoggedIn = !!user;
  const goToPartCollectingPage = () => navigate(`/${ROUTE_PATH.COLLECT}/${songId}`);

  const { isOpen, openModal, closeModal } = useModal();

  return (
    <TrackList role="radiogroup">
      {killingParts.map((killingPart) => (
        <KillingPartTrack
          key={killingPart.id}
          killingPart={killingPart}
          songId={songId}
          isNowPlayingTrack={killingPart.id === nowPlayingTrack}
          setNowPlayingTrack={setNowPlayingTrack}
          setCommentsPartId={setCommentsPartId}
        />
      ))}

      {hasMyPart ? (
        <KillingPartTrack
          killingPart={myPart}
          songId={songId}
          isNowPlayingTrack={myPart.id === nowPlayingTrack}
          setNowPlayingTrack={setNowPlayingTrack}
          setCommentsPartId={setCommentsPartId}
          isMyKillingPart
        />
      ) : (
        <PartRegisterButton type="button" onClick={isLoggedIn ? goToPartCollectingPage : openModal}>
          + My Part
        </PartRegisterButton>
      )}

      <LoginModal
        isOpen={isOpen}
        closeModal={closeModal}
        message={'로그인하여 나의 킬링파트를 등록하세요!\n등록한 노래는 마이페이지에 저장됩니다!'}
      />
    </TrackList>
  );
};

export default KillingPartTrackList;

export const TrackList = styled.div`
  display: flex;
  flex-direction: column;
  gap: 10px;
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
