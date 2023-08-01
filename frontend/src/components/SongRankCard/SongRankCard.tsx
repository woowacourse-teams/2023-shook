import { Grid, Info, Rank, Singer, SongTitle } from './SongRankCard.style';
import Thumbnail from './Thumbnail';

interface CardProps {
  rank: number;
  src: string;
  alt: string;
  title: string;
  singer: string;
}

const SongRankCard = ({ rank, src, alt, title, singer }: CardProps) => {
  return (
    <Grid>
      <Rank>{rank}</Rank>
      <Thumbnail src={src} alt={alt} />
      <SongTitle>{title}</SongTitle>
      <Singer>{singer}</Singer>
      <Info>25,908 votes</Info>
    </Grid>
  );
};

export default SongRankCard;
