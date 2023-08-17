import { useEffect, useRef } from 'react';
import { useParams } from 'react-router-dom';
import { styled } from 'styled-components';
import SongDetailItem from '@/features/songs/components/SongDetailItem';
import { getSongDetailEntries } from '@/features/songs/remotes/songs';
import useFetch from '@/shared/hooks/useFetch';

const SongDetailListPage = () => {
  const { id: songIdParams } = useParams();
  const { data: songDetailEntries } = useFetch(() => getSongDetailEntries(Number(songIdParams)));

  const itemRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    itemRef.current?.scrollIntoView({ behavior: 'instant', block: 'start' });
  }, [songDetailEntries]);

  if (!songDetailEntries) return;

  const { prevSongs, currentSong, nextSongs } = songDetailEntries;

  return (
    <ItemContainer>
      {prevSongs.map((prevSongDetail) => (
        <SongDetailItem key={prevSongDetail.id} {...prevSongDetail} />
      ))}
      <SongDetailItem ref={itemRef} key={currentSong.id} {...currentSong} />
      {nextSongs.map((nextSongDetail) => (
        <SongDetailItem key={nextSongDetail.id} {...nextSongDetail} />
      ))}
    </ItemContainer>
  );
};

export default SongDetailListPage;

export const ItemContainer = styled.div`
  width: 100%;

  @media (max-width: ${({ theme }) => theme.breakPoints.xs}) {
    scroll-snap-type: y mandatory;
    overflow-y: scroll;
    height: calc(
      ${({ theme: { headerHeight, mainTopBottomPadding } }) => {
        return `100vh - ${headerHeight.mobile} - ${mainTopBottomPadding.mobile} * 2`;
      }}
    );

    & > div {
      scroll-snap-align: start;
      scroll-snap-stop: always;
    }
  }
`;
