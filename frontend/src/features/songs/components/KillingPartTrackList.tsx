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

  const changePlayingTrack: React.ChangeEventHandler<HTMLInputElement> = ({ currentTarget }) => {
    const newTrack = Number(currentTarget.value) as KillingPartRank;

    setNowPlayingTrack(newTrack);
  };

  return (
    <TrackList>
      {killingParts.map((killingPart) => (
        <KillingPartTrack
          key={killingPart.id}
          killingPart={killingPart}
          nowPlayingTrack={nowPlayingTrack}
          changePlayingTrack={changePlayingTrack}
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
