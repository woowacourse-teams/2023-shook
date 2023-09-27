import { Link } from 'react-router-dom';
import { styled } from 'styled-components';
import emptyPlay from '@/assets/icon/empty-play.svg';
import Thumbnail from '@/features/songs/components/Thumbnail';
import Spacing from '@/shared/components/Spacing';
import ROUTE_PATH from '@/shared/constants/path';
import { toMinSecText } from '@/shared/utils/convertTime';
import type { VotingSong } from '../types/Song.type';

interface CarouselItemProps {
  votingSong: VotingSong;
}

const CarouselItem = ({ votingSong }: CarouselItemProps) => {
  const { id, singer, title, videoLength, albumCoverUrl } = votingSong;

  return (
    <Wrapper>
      <CollectingLink to={`${ROUTE_PATH.COLLECT}/${id}`}>
        <Thumbnail src={albumCoverUrl} size="xl" borderRadius={4} />
        <Spacing direction={'horizontal'} size={24} />
        <Contents>
          <Title>{title}</Title>
          <Singer>{singer}</Singer>
          <PlayingTime>
            <PlayIcon src={emptyPlay} />
            <PlayingTimeText>{toMinSecText(videoLength)}</PlayingTimeText>
          </PlayingTime>
        </Contents>
      </CollectingLink>
      가
    </Wrapper>
  );
};

export default CarouselItem;

const Wrapper = styled.li`
  width: 100%;
  min-width: 350px;
`;

const CollectingLink = styled(Link)`
  display: flex;
  justify-content: center;
  padding: 10px;
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

const PlayIcon = styled.img`
  width: 16px;
  height: 16px;
  margin: auto;
`;
