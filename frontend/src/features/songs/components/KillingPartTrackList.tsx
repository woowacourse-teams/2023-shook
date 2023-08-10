import { useState } from 'react';
import { styled } from 'styled-components';
import KillingPartTrack from './KillingPartTrack';
import type { SongDetail } from '@/shared/types/song';

interface KillingPartTrackListProps {
  killingParts: SongDetail['killingParts'];
}

const KillingPartTrackList = ({ killingParts }: KillingPartTrackListProps) => {
  const [nowPlayingTrack, setNowPlayingTrack] = useState(-1);

  const changePlayingTrack: React.ChangeEventHandler<HTMLInputElement> = ({ currentTarget }) => {
    const newTrack = Number(currentTarget.value);

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
  align-items: center;
  gap: 8px;
`;
