import { Grid, Rank, Singer, SongTitle } from './SongRankCard.style';
import Thumbnail from './Thumbnail';

interface CardProps {
  rank: number;
  thumbnail: string;
  title: string;
  singer: string;
}

const SongRankCard = ({ rank, thumbnail, title, singer }: CardProps) => {
  return (
    <Grid>
      <Rank>{rank}</Rank>
      <Thumbnail src={thumbnail} alt={`${title}-${singer}`} />
      <SongTitle>{title}</SongTitle>
      <Singer>{singer}</Singer>
      {/*
        TODO: 백엔드 구현 되어야 함
        <Info>25,908 votes</Info>
       */}
    </Grid>
  );
};

export default SongRankCard;
