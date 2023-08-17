import { styled } from 'styled-components';
import KillingPartTrack from './KillingPartTrack';
import type { KillingPart, SongDetail } from '@/shared/types/song';

interface KillingPartTrackListProps {
  killingParts: SongDetail['killingParts'];
  nowPlayingTrack: KillingPart['id'];
  setNowPlayingTrack: React.Dispatch<React.SetStateAction<KillingPart['id']>>;
  setCommentsPartId: React.Dispatch<React.SetStateAction<KillingPart['id']>>;
}

const KillingPartTrackList = ({
  killingParts,
  nowPlayingTrack,
  setNowPlayingTrack,
  setCommentsPartId,
}: KillingPartTrackListProps) => {
  return (
    <TrackList role="radiogroup">
      {killingParts.map((killingPart) => {
        const { id } = killingPart;
        const isNowPlayingTrack = id === nowPlayingTrack;

        return (
          <KillingPartTrack
            key={id}
            killingPart={killingPart}
            isNowPlayingTrack={isNowPlayingTrack}
            setNowPlayingTrack={setNowPlayingTrack}
            setCommentsPartId={setCommentsPartId}
          />
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
