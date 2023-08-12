import { useState } from 'react';
import { styled } from 'styled-components';
import CommentList from '@/features/comments/components/CommentList';
import Spacing from '@/shared/components/Spacing';
import ToggleSwitch from '@/shared/components/ToggleSwitch/ToggleSwitch';
import useKillingPartInterfaceContext from '../hooks/KillingPartInterfaceContext';
import KillingPartTrackList from './KillingPartTrackList';
import type { SongDetail } from '@/shared/types/song';

interface KillingPartInterfaceProps {
  killingParts: SongDetail['killingParts'];
}

const KillingPartInterface = ({ killingParts }: KillingPartInterfaceProps) => {
  const { nowPlayingTrack, songId } = useKillingPartInterfaceContext();
  const [isRepeat, setIsRepeat] = useState(false);

  const toggleRepetition = () => {
    setIsRepeat(!isRepeat);
  };

  const killingPart = killingParts.find((part) => part.rank === nowPlayingTrack);

  return (
    <>
      <FlexContainer>
        <TitleWrapper aria-label="Top 3 킬링파트 듣기">
          <ItalicTitle aria-hidden="true">Top 3</ItalicTitle>
          <NormalTitle aria-hidden="true"> Killing part</NormalTitle>
        </TitleWrapper>
        <SwitchWrapper>
          <SwitchLabel htmlFor="repetition">구간 반복</SwitchLabel>
          <ToggleSwitch
            id="repetition"
            on={toggleRepetition}
            off={toggleRepetition}
            defaultToggle={isRepeat}
          />
        </SwitchWrapper>
      </FlexContainer>
      <Spacing direction="vertical" size={16} />
      <KillingPartTrackList killingParts={killingParts} />
      {killingPart && <CommentList songId={songId} partId={killingPart?.id} />}
    </>
  );
};

export default KillingPartInterface;

const SwitchWrapper = styled.div`
  display: flex;
  column-gap: 8px;
`;

const SwitchLabel = styled.label`
  font-size: 12px;
  color: ${({ theme: { color } }) => color.white};
`;

const TitleWrapper = styled.div`
  font-size: 22px;
  font-weight: 700;
  color: ${({ theme: { color } }) => color.white};

  @media (max-width: ${({ theme }) => theme.breakPoints.md}) {
    font-size: 18px;
  }
`;

const ItalicTitle = styled.span`
  font-style: italic;
  color: ${({ theme: { color } }) => color.primary};
`;

const NormalTitle = styled.span`
  color: ${({ theme: { color } }) => color.white};
`;

const FlexContainer = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
`;
