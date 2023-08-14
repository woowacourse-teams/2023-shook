import { styled } from 'styled-components';
import TimerProvider from '@/shared/components/Timer/TimerProvider';
import KillingPartTrack from './KillingPartTrack';
import type { KillingPart, SongDetail } from '@/shared/types/song';

interface KillingPartTrackListProps {
  killingParts: SongDetail['killingParts'];
  songId: number;
  nowPlayingTrack: KillingPart['id'];
  setNowPlayingTrack: React.Dispatch<React.SetStateAction<KillingPart['id']>>;
}

const KillingPartTrackList = ({
  killingParts,
  songId,
  nowPlayingTrack,
  setNowPlayingTrack,
}: KillingPartTrackListProps) => {
  return (
    <TrackList role="radiogroup">
      {killingParts.map((killingPart) => {
        const { id, start, end } = killingPart;
        const isNowPlayingTrack = id === nowPlayingTrack;

        return (
          <TimerProvider time={end - start} key={id}>
            <KillingPartTrack
              killingPart={killingPart}
              songId={songId}
              isNowPlayingTrack={isNowPlayingTrack}
              setNowPlayingTrack={setNowPlayingTrack}
            />
          </TimerProvider>
        );
      })}
    </TrackList>
  );
};

export default KillingPartTrackList;

export const TrackList = styled.div`
  display: flex;
  flex-direction: column;
  gap: 10px;
`;
