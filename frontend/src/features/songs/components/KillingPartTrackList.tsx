import { styled } from 'styled-components';
import KillingPartTrack from './KillingPartTrack';
import type { KillingPart, SongDetail } from '@/shared/types/song';

interface KillingPartTrackListProps {
  killingParts: SongDetail['killingParts'];
  nowPlayingTrack: KillingPart['id'];
  setNowPlayingTrack: React.Dispatch<React.SetStateAction<KillingPart['id']>>;
}

const KillingPartTrackList = ({
  killingParts,
  nowPlayingTrack,
  setNowPlayingTrack,
}: KillingPartTrackListProps) => {
  return (
    <TrackList>
      {killingParts.map((killingPart) => (
        <KillingPartTrack
          key={killingPart.id}
          killingPart={killingPart}
          isPlaying={killingPart.rank === nowPlayingTrack}
          setNowPlayingTrack={setNowPlayingTrack}
        />
      ))}
    </TrackList>
  );
};

export default KillingPartTrackList;

export const TrackList = styled.div`
  display: flex;
  flex-direction: column;
  gap: 8px;
`;
