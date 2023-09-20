import { styled } from 'styled-components';
import Spacing from '@/shared/components/Spacing';
import Thumbnail from './Thumbnail';

interface SongItemProps {
  rank: number;
  title: string;
  singer: string;
  albumCoverUrl: string;
  totalLikeCount: number;
}

const SongItem = ({ albumCoverUrl, title, singer }: SongItemProps) => {
  return (
    <Flex>
      <Thumbnail size="xl" src={albumCoverUrl} alt={`${title}-${singer}`} />
      <Spacing direction="vertical" size={4} />
      <SongTitle>{title}</SongTitle>
      <Singer>{singer}</Singer>
    </Flex>
  );
};

export default SongItem;

const Flex = styled.div`
  display: flex;
  flex-direction: column;
  color: ${({ theme: { color } }) => color.white};
`;

const SongTitle = styled.div`
  overflow: hidden;
  grid-area: title;

  font-size: 14px;
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
