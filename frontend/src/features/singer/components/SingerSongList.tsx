import styled from 'styled-components';
import Flex from '@/shared/components/Flex/Flex';
import SingerSongItem from './SingerSongItem';
import type { SingersSong } from '../types/singer.type';

interface SingerSongListProps {
  songs: SingersSong[];
  title: string;
}

const SingerSongList = ({ songs, title }: SingerSongListProps) => {
  return (
    <Container>
      <Title>{title}</Title>
      <SongsItemList as="ol" $direction="column" $gap={12} $align="center">
        {songs.map((song) => (
          <SingerSongItem key={song.id} {...song} />
        ))}
      </SongsItemList>
    </Container>
  );
};

export default SingerSongList;

const Container = styled.div`
  flex: 1;
`;

const SongsItemList = styled(Flex)`
  overflow-y: scroll;
  width: 100%;
`;

const Title = styled.h2`
  margin-bottom: 18px;
  font-size: 28px;
  font-weight: 700;
  color: ${({ theme: { color } }) => color.white};

  @media (max-width: ${({ theme }) => theme.breakPoints.xs}) {
    font-size: 24px;
  }
`;
