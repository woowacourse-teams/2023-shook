import { Grid, Info, Rank, Singer, SongTitle } from './PopularSongItem.style';
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
