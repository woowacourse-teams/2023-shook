import { useEffect, useLayoutEffect, useRef } from 'react';
import { useParams } from 'react-router-dom';
import { styled } from 'styled-components';
import swipeUpDown from '@/assets/icon/swipe-up-down.svg';
import SongDetailItem from '@/features/songs/components/SongDetailItem';
import {
  getExtraNextSongDetails,
  getExtraPrevSongDetails,
  getSongDetailEntries,
} from '@/features/songs/remotes/songs';
import useModal from '@/shared/components/Modal/hooks/useModal';
import Modal from '@/shared/components/Modal/Modal';
import Spacing from '@/shared/components/Spacing';
import useExtraFetch from '@/shared/hooks/useExtraFetch';
import useFetch from '@/shared/hooks/useFetch';
import createObserver from '@/shared/utils/createObserver';

const SongDetailListPage = () => {
  const { isOpen, closeModal } = useModal(true);
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

  const getFirstSongId = () => {
    const firstSongId = prevTargetRef.current?.nextElementSibling?.getAttribute(
      'data-song-id'
    ) as string;

    return Number(firstSongId);
  };

  const getLastSongId = () => {
    const lastSongId = nextTargetRef.current?.previousElementSibling?.getAttribute(
      'data-song-id'
    ) as string;

    return Number(lastSongId);
  };

  useEffect(() => {
    if (!prevTargetRef.current) return;

    const prevObserver = createObserver(() => fetchExtraPrevSongDetails(getFirstSongId()));
    prevObserver.observe(prevTargetRef.current);

    return () => prevObserver.disconnect();
  }, [fetchExtraPrevSongDetails, songDetailEntries]);

  useEffect(() => {
    if (!nextTargetRef.current) return;

    const nextObserver = createObserver(() => fetchExtraNextSongDetails(getLastSongId()));
    nextObserver.observe(nextTargetRef.current);

    return () => nextObserver.disconnect();
  }, [fetchExtraNextSongDetails, songDetailEntries]);

  useLayoutEffect(() => {
    itemRef.current?.scrollIntoView({ behavior: 'instant', block: 'start' });
  }, [songDetailEntries]);

  if (!songDetailEntries) return;

  const { prevSongs, currentSong, nextSongs } = songDetailEntries;

  return (
    <>
      <Modal isOpen={isOpen} closeModal={closeModal} css={{ backgroundColor: 'transparent' }}>
        <Spacing direction="vertical" size={170} />
        <img src={swipeUpDown} width="100px" />
        <Spacing direction="vertical" size={30} />
        <div style={{ fontSize: '18px' }}>위 아래로 스와이프하여 노래를 탐색해보세요!</div>
        <Spacing direction="vertical" size={40} />
        <Confirm type="button" onClick={closeModal}>
          확인
        </Confirm>
      </Modal>

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
    </>
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

const Confirm = styled.button`
  width: 200px;
  height: 40px;

  color: ${({ theme: { color } }) => color.black};

  background-color: ${({ theme: { color } }) => color.white};
  border-radius: 10px;
`;
