import { useCallback, useLayoutEffect, useRef } from 'react';
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
import useLocalStorage from '@/shared/hooks/useLocalStorage';
import useValidParams from '@/shared/hooks/useValidParams';
import createObserver from '@/shared/utils/createObserver';
import type { Genre } from '@/features/songs/types/Song.type';

const SongDetailListPage = () => {
  const { isOpen, closeModal } = useModal(true);
  const [onboarding, setOnboarding] = useLocalStorage<boolean>('onboarding', true);

  const { id: songIdParams, genre: genreParams } = useValidParams();
  const { data: songDetailEntries } = useFetch(() =>
    getSongDetailEntries(Number(songIdParams), genreParams as Genre)
  );

  const { data: extraPrevSongDetails, fetchData: fetchExtraPrevSongDetails } = useExtraFetch(
    getExtraPrevSongDetails,
    'prev'
  );

  const { data: extraNextSongDetails, fetchData: fetchExtraNextSongDetails } = useExtraFetch(
    getExtraNextSongDetails,
    'next'
  );

  const prevObserverRef = useRef<IntersectionObserver | null>(null);
  const nextObserverRef = useRef<IntersectionObserver | null>(null);

  const getExtraPrevSongDetailsOnObserve: React.RefCallback<HTMLDivElement> = useCallback((dom) => {
    if (dom === null) {
      prevObserverRef.current?.disconnect();
      return;
    }

    prevObserverRef.current = createObserver(() =>
      fetchExtraPrevSongDetails(getFirstSongId(dom), genreParams as Genre)
    );

    prevObserverRef.current.observe(dom);
  }, []);

  const getExtraNextSongDetailsOnObserve: React.RefCallback<HTMLDivElement> = useCallback((dom) => {
    if (dom === null) {
      nextObserverRef.current?.disconnect();
      return;
    }

    nextObserverRef.current = createObserver(() =>
      fetchExtraNextSongDetails(getLastSongId(dom), genreParams as Genre)
    );

    nextObserverRef.current.observe(dom);
  }, []);

  const itemRef = useRef<HTMLDivElement>(null);

  const getFirstSongId = (dom: HTMLDivElement) => {
    const firstSongId = dom.nextElementSibling?.getAttribute('data-song-id') as string;

    return Number(firstSongId);
  };

  const getLastSongId = (dom: HTMLDivElement) => {
    const lastSongId = dom.previousElementSibling?.getAttribute('data-song-id') as string;

    return Number(lastSongId);
  };

  const closeCoachMark = () => {
    setOnboarding(false);
    closeModal();
  };

  useLayoutEffect(() => {
    itemRef.current?.scrollIntoView({ behavior: 'instant', block: 'start' });
  }, [songDetailEntries]);

  if (!songDetailEntries) return;

  const { prevSongs, currentSong, nextSongs } = songDetailEntries;

  return (
    <>
      {onboarding && (
        <Modal
          isOpen={isOpen}
          closeModal={closeCoachMark}
          css={{ backgroundColor: 'transparent' }}
          canCloseByBackDrop={false}
        >
          <Spacing direction="vertical" size={170} />
          <img src={swipeUpDown} width="100px" />
          <Spacing direction="vertical" size={30} />
          <div style={{ fontSize: '18px' }}>위 아래로 스와이프하여 노래를 탐색해보세요!</div>
          <Spacing direction="vertical" size={40} />
          <Confirm type="button" onClick={closeCoachMark}>
            확인
          </Confirm>
        </Modal>
      )}

      <ItemContainer>
        <ObservingTrigger ref={getExtraPrevSongDetailsOnObserve} aria-hidden="true" />

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

        <ObservingTrigger ref={getExtraNextSongDetailsOnObserve} aria-hidden="true" />
      </ItemContainer>
    </>
  );
};

export default SongDetailListPage;

export const ObservingTrigger = styled.div`
  width: 1px;
`;

export const ItemContainer = styled.div`
  scroll-snap-type: y mandatory;
  overflow-y: scroll;
  width: 100%;
  height: 100vh;

  & > div[role='article'] {
    scroll-snap-align: start;
    scroll-snap-stop: always;
  }
`;

const Confirm = styled.button`
  width: 200px;
  height: 40px;

  color: ${({ theme: { color } }) => color.black};

  background-color: ${({ theme: { color } }) => color.white};
  border-radius: 10px;
`;
