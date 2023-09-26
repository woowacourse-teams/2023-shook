import { useNavigate } from 'react-router-dom';
import { styled } from 'styled-components';
import emptyPlay from '@/assets/icon/empty-play.svg';
import { useAuthContext } from '@/features/auth/components/AuthProvider';
import LoginModal from '@/features/auth/components/LoginModal';
import useModal from '@/shared/components/Modal/hooks/useModal';
import Spacing from '@/shared/components/Spacing';
import ROUTE_PATH from '@/shared/constants/path';
import { toMinSecText } from '@/shared/utils/convertTime';
import type { VotingSong } from '../types/Song.type';

interface CarouselItemProps {
  votingSong: VotingSong;
}

const CarouselItem = ({ votingSong }: CarouselItemProps) => {
  const { id, singer, title, videoLength, albumCoverUrl } = votingSong;

  const { isOpen, openModal, closeModal } = useModal();

  const { user } = useAuthContext();
  const isLoggedIn = !!user;

  const navigate = useNavigate();
  const goToPartCollectingPage = () => navigate(`${ROUTE_PATH.COLLECT}/${id}`);

  return (
    <Wrapper>
      {!isLoggedIn && (
        <LoginModal
          message={
            '슉에서 당신만의 킬링파트를 등록해보세요!\n당신이 등록한 구간이 대표 킬링파트가 될 수 있어요!'
          }
          isOpen={isOpen}
          closeModal={closeModal}
        />
      )}

      <CollectingLink onClick={isLoggedIn ? goToPartCollectingPage : openModal}>
        <Album src={albumCoverUrl} />
        <Spacing direction={'horizontal'} size={24} />
        <Contents>
          <Title>{title}</Title>
          <Singer>{singer}</Singer>
          <PlayingTime>
            <img src={emptyPlay} />
            <PlayingTimeText>{toMinSecText(videoLength)}</PlayingTimeText>
          </PlayingTime>
        </Contents>
      </CollectingLink>
    </Wrapper>
  );
};

export default CarouselItem;

const Wrapper = styled.li`
  width: 100%;
  min-width: 350px;
`;

const CollectingLink = styled.a`
  display: flex;
  justify-content: center;
  padding: 10px;
`;

const Album = styled.img`
  max-width: 120px;
  background-color: white;
  border-radius: 4px;
`;

const Contents = styled.div`
  display: flex;
  flex-direction: column;
  align-items: start;
  justify-content: space-evenly;

  width: 150px;

  white-space: nowrap;
`;

const Title = styled.p`
  overflow: hidden;

  max-width: 150px;
  margin-left: 0;

  font-size: 18px;
  font-weight: 700;
  text-overflow: ellipsis;
`;

const Singer = styled.p`
  overflow: hidden;

  max-width: 150px;
  margin-left: 0;

  font-size: 14px;
  text-overflow: ellipsis;
`;

const PlayingTime = styled.div`
  display: flex;
  column-gap: 8px;
  border-radius: 20px;
`;

const PlayingTimeText = styled.p`
  padding-top: 2px;
`;
