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
  const { data: extraNextSongDetails, fetchData: fetchExtraNextSongDetails } = useExtraFetch(
    getExtraNextSongDetails,
    'down'
  );

  const { data: extraPrevSongDetails, fetchData: fetchExtraPrevSongDetails } = useExtraFetch(
    getExtraPrevSongDetails,
    'top'
  );

  const itemRef = useRef<HTMLDivElement>(null);

  const songItemContainerRef = useRef<HTMLDivElement>(null);
  const topObservingTargetRef = useRef<HTMLDivElement>(null);
  const bottomObservingTargetRef = useRef<HTMLDivElement>(null);

  const getFirstSongId = () => {
    const firstSongId = topObservingTargetRef.current?.nextElementSibling?.getAttribute(
      'data-song-id'
    ) as string;

    return Number(firstSongId);
  };

  const getLastSongId = () => {
    const lastSongId = bottomObservingTargetRef.current?.previousElementSibling?.getAttribute(
      'data-song-id'
    ) as string;

    return Number(lastSongId);
  };

  useEffect(() => {
    console.log('탑 터짐');
    console.log(extraPrevSongDetails);

    const onObserveTopTarget: IntersectionObserverCallback = async ([entry], observer) => {
      if (entry.isIntersecting) {
        const firstSongId = getFirstSongId();

        observer.unobserve(entry.target);
        await fetchExtraPrevSongDetails(firstSongId);
        observer.observe(entry.target);
      }
    };

    const observer = new IntersectionObserver(onObserveTopTarget, {
      threshold: [0],
      rootMargin: '1500px 0px',
      root: songItemContainerRef.current,
    });

    if (!bottomObservingTargetRef.current) return;
    observer.observe(bottomObservingTargetRef.current);

    return () => observer.disconnect();
  }, [extraPrevSongDetails, fetchExtraPrevSongDetails]);

  useEffect(() => {
    console.log('바텀 터짐');
    console.log(extraNextSongDetails);

    const onObserveBottomTarget: IntersectionObserverCallback = async ([entry], observer) => {
      if (entry.isIntersecting) {
        const lastSongId = getLastSongId();

        observer.unobserve(entry.target);
        await fetchExtraNextSongDetails(lastSongId);
        observer.observe(entry.target);
      }
    };

    const observer = new IntersectionObserver(onObserveBottomTarget, {
      threshold: [0],
      rootMargin: '1500px 0px',
      root: songItemContainerRef.current,
    });

    if (!bottomObservingTargetRef.current) return;
    observer.observe(bottomObservingTargetRef.current);

    return () => observer.disconnect();
  }, [extraNextSongDetails, fetchExtraNextSongDetails]);

  useEffect(() => {
    itemRef.current?.scrollIntoView({ behavior: 'instant', block: 'start' });
  }, [songDetailEntries]);

  if (!songDetailEntries) return;

  const { prevSongs, currentSong, nextSongs } = songDetailEntries;

  return (
    <ItemContainer ref={songItemContainerRef}>
      <ObservingTrigger ref={topObservingTargetRef} aria-hidden="true" />
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
      <ObservingTrigger ref={bottomObservingTargetRef} aria-hidden="true" />
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
      ${({ theme: { headerHeight, mainTopBottomPadding } }) => {
        return `100vh - ${headerHeight.mobile} - ${mainTopBottomPadding.tablet} * 2`;
      }}
    );

    & > div[role='article'] {
      scroll-snap-align: start;
      scroll-snap-stop: always;
    }
  }

  @media (max-width: ${({ theme }) => theme.breakPoints.xxs}) {
    height: calc(
      ${({ theme: { headerHeight, mainTopBottomPadding } }) => {
        return `100vh - ${headerHeight.xxs} - ${mainTopBottomPadding.xxs} * 2`;
      }}
    );

    & > div[role='article'] {
      scroll-snap-align: start;
      scroll-snap-stop: always;
    }
  }
`;
