import { styled } from 'styled-components';
import swipeUpDown from '@/assets/icon/swipe-up-down.svg';
import SongDetailItem from '@/features/songs/components/SongDetailItem';
import useExtraSongDetail from '@/features/songs/hooks/useExtraSongDetail';
import useSongDetailEntries from '@/features/songs/hooks/useSongDetailEntries';
import useModal from '@/shared/components/Modal/hooks/useModal';
import Modal from '@/shared/components/Modal/Modal';
import Spacing from '@/shared/components/Spacing';
import useLocalStorage from '@/shared/hooks/useLocalStorage';

const SongDetailListPage = () => {
  const { isOpen, closeModal } = useModal(true);
  const [onboarding, setOnboarding] = useLocalStorage<boolean>('onboarding', true);

  const { songDetailEntries, scrollIntoCurrentSong } = useSongDetailEntries();

  const {
    extraPrevSongDetails,
    extraNextSongDetails,
    isLoadingNextSongDetails,
    isLoadingPrevSongDetails,
    hasPreviousPage,
    hasNextPage,
    getExtraPrevSongDetailsOnObserve,
    getExtraNextSongDetailsOnObserve,
  } = useExtraSongDetail();

  // Suspense 적용시 워터폴 문제 해결 후 Suspense 적용
  // 적용 시 아래 분기문 사라짐.
  if (!songDetailEntries || isLoadingNextSongDetails || isLoadingPrevSongDetails) return null;

  // 응답값의 prev, next 사용하지 않게 되었음.
  const { currentSong } = songDetailEntries;

  const closeCoachMark = () => {
    setOnboarding(false);
    closeModal();
  };

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
        {hasPreviousPage && (
          <ObservingTrigger ref={getExtraPrevSongDetailsOnObserve} aria-hidden="true" />
        )}
        {extraPrevSongDetails?.pages.map((details) =>
          details.map((extraPrevSongDetail) => (
            <SongDetailItem key={extraPrevSongDetail.id} {...extraPrevSongDetail} />
          ))
        )}

        {/* 응답값의 prev, next 사용하지 않게 되었음. */}
        {/* {prevSongs.map((prevSongDetail) => (
          <SongDetailItem key={prevSongDetail.id} {...prevSongDetail} />
        ))} */}

        <SongDetailItem ref={scrollIntoCurrentSong} key={currentSong.id} {...currentSong} />

        {/* 응답값의 prev, next 사용하지 않게 되었음. */}
        {/* {nextSongs.map((nextSongDetail) => (
          <SongDetailItem key={nextSongDetail.id} {...nextSongDetail} />
        ))} */}

        {extraNextSongDetails?.pages.map((details) =>
          details.map((extraNextSongDetail) => (
            <SongDetailItem key={extraNextSongDetail.id} {...extraNextSongDetail} />
          ))
        )}
        {hasNextPage && (
          <ObservingTrigger ref={getExtraNextSongDetailsOnObserve} aria-hidden="true" />
        )}
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
