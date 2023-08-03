import { Grid, Info, Rank, Singer, SongTitle } from './PopularSongItem.style';
import Thumbnail from './Thumbnail';

interface CardProps {
  rank: number;
  title: string;
  singer: string;
  imageUrl: string;
  totalVoteCount: number;
}

const PopularSongItem = ({ rank, imageUrl, title, singer, totalVoteCount }: CardProps) => {
  return (
    <Grid>
      <Rank>{rank}</Rank>
      <Thumbnail src={imageUrl} alt={`${title}-${singer}`} />
      <SongTitle>{title}</SongTitle>
      <Singer>{singer}</Singer>
      <Info>{new Intl.NumberFormat('ko-KR').format(totalVoteCount)} votes</Info>
    </Grid>
  );
};

export default PopularSongItem;
