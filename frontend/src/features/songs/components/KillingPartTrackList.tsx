import { useState } from 'react';
import { styled } from 'styled-components';
import KILLING_PART_RANK from '../constants/killingPartRank';
import KillingPartTrack from './KillingPartTrack';
import type { KillingPartRank } from '../types/KillingPartRank.type';
import type { SongDetail } from '@/shared/types/song';

interface KillingPartTrackListProps {
  killingParts: SongDetail['killingParts'];
}

const KillingPartTrackList = ({ killingParts }: KillingPartTrackListProps) => {
  const [nowPlayingTrack, setNowPlayingTrack] = useState<KillingPartRank>(
    KILLING_PART_RANK.DEFAULT
  );

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
