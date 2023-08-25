import { useEffect, useRef } from 'react';
import { useParams } from 'react-router-dom';
import { styled } from 'styled-components';
import SongDetailItem from '@/features/songs/components/SongDetailItem';
import {
  getExtraNextSongDetails,
  getExtraPrevSongDetails,
  getSongDetailEntries,
} from '@/features/songs/remotes/songs';
import useExtraFetch from '@/shared/hooks/useExtraFetch';
import useFetch from '@/shared/hooks/useFetch';

const SongDetailListPage = () => {
  const { id: songIdParams } = useParams();
  const { data: songDetailEntries } = useFetch(() => getSongDetailEntries(Number(songIdParams)));

  const { data: extraPrevSongDetails, fetchData: fetchExtraPrevSongDetails } = useExtraFetch(
    getExtraPrevSongDetails,
    'prev'
  );

  const { data: extraNextSongDetails, fetchData: fetchExtraNextSongDetails } = useExtraFetch(
    getExtraNextSongDetails,
    'next'
  );

  const itemRef = useRef<HTMLDivElement>(null);

  const prevTargetRef = useRef<HTMLDivElement | null>(null);
  const nextTargetRef = useRef<HTMLDivElement | null>(null);

  useEffect(() => {
    if (!prevTargetRef.current) return;

    const prevObserver = new IntersectionObserver(([entry]) => {
      if (!entry.isIntersecting) return;

      const firstSongId = entry.target.nextElementSibling?.getAttribute('data-song-id') as string;

      fetchExtraPrevSongDetails(Number(firstSongId));
    });

    prevObserver.observe(prevTargetRef.current);

    return () => prevObserver.disconnect();
  }, [fetchExtraPrevSongDetails, songDetailEntries]);

  useEffect(() => {
    if (!nextTargetRef.current) return;

    const nextObserver = new IntersectionObserver(([entry]) => {
      if (!entry.isIntersecting) return;

      const lastSongId = entry.target.previousElementSibling?.getAttribute(
        'data-song-id'
      ) as string;

      fetchExtraNextSongDetails(Number(lastSongId));
    });

    nextObserver.observe(nextTargetRef.current);

    return () => nextObserver.disconnect();
  }, [fetchExtraNextSongDetails, songDetailEntries]);

  useEffect(() => {
    itemRef.current?.scrollIntoView({ behavior: 'instant', block: 'start' });
  }, [songDetailEntries]);

  if (!songDetailEntries) return;

  const { prevSongs, currentSong, nextSongs } = songDetailEntries;

  return (
    <ItemContainer>
      <ObservingTrigger ref={prevTargetRef} aria-hidden="true" />

      {extraPrevSongDetails?.map((extraPrevSongDetail) => (
        <SongDetailItem key={extraPrevSongDetail.id} {...extraPrevSongDetail} />
      ))}
      {prevSongs.map((prevSongDetail) => (
        <SongDetailItem key={prevSongDetail.id} {...prevSongDetail} />
      ))}

      <SongDetailItem ref={itemRef} key={currentSong.id} {...currentSong} />

      {nextSongs.map((nextSongDetail) => (
        <SongDetailItem key={nextSongDetail.id} {...nextSongDetail} />
      ))}
      {extraNextSongDetails?.map((extraNextSongDetail) => (
        <SongDetailItem key={extraNextSongDetail.id} {...extraNextSongDetail} />
      ))}

      <ObservingTrigger ref={nextTargetRef} aria-hidden="true" />
    </ItemContainer>
  );
};

export default SongDetailListPage;

export const ObservingTrigger = styled.div`
  width: 1px;
`;

export const ItemContainer = styled.div`
  width: 100%;

  @media (max-width: ${({ theme }) => theme.breakPoints.md}) {
    scroll-snap-type: y mandatory;
    overflow-y: scroll;
    height: calc(
      ${({ theme: { mainTopBottomPadding } }) => {
        return `100vh - ${mainTopBottomPadding.tablet} * 2`;
      }}
    );

    & > div[role='article'] {
      scroll-snap-align: start;
      scroll-snap-stop: always;
    }
  }

  @media (max-width: ${({ theme }) => theme.breakPoints.xxs}) {
    height: calc(
      ${({ theme: { mainTopBottomPadding } }) => {
        return `100vh -  ${mainTopBottomPadding.xxs} * 2`;
      }}
    );

    & > div[role='article'] {
      scroll-snap-align: start;
      scroll-snap-stop: always;
    }
  }
`;
