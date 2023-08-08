import { styled } from 'styled-components';
import Thumbnail from './Thumbnail';

interface CardProps {
  rank: number;
  title: string;
  singer: string;
  albumCoverUrl: string;
  totalVoteCount: number;
}

const PopularSongItem = ({ rank, albumCoverUrl, title, singer, totalVoteCount }: CardProps) => {
  return (
    <Grid>
      <Rank>{rank}</Rank>
      <Thumbnail src={albumCoverUrl} alt={`${title}-${singer}`} />
      <SongTitle>{title}</SongTitle>
      <Singer>{singer}</Singer>
      <Info aria-label={`${totalVoteCount} 투표됨`}>
        {new Intl.NumberFormat('ko-KR').format(totalVoteCount)} votes
      </Info>
    </Grid>
  );
};

export default PopularSongItem;

const Grid = styled.div`
  display: grid;
  column-gap: 8px;
  padding: 6px 0;
  grid-template:
    'rank thumbnail title' 26px
    'rank thumbnail singer' 26px
    'rank thumbnail info' 18px
    / 14px 70px;

  color: ${({ theme: { color } }) => color.white};
`;

const Rank = styled.div`
  grid-area: rank;

  display: flex;
  justify-content: center;
  align-items: center;

  font-weight: 800;
`;

const SongTitle = styled.div`
  grid-area: title;

  font-size: 16px;
  font-weight: 800;

  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
`;

const Singer = styled.div`
  grid-area: singer;

  font-size: 12px;

  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
`;

const Info = styled.div`
  grid-area: info;

  color: #808191;
  font-size: 12px;
`;