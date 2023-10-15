import { styled } from 'styled-components';
import Thumbnail from '@/features/songs/components/Thumbnail';
import Flex from '@/shared/components/Flex/Flex';

interface SongInformationProps {
  albumCoverUrl: string;
  title: string;
  singer: string;
}

const SongInformation = ({ albumCoverUrl, title, singer }: SongInformationProps) => {
  return (
    <Flex $gap={12} $align="center">
      <Thumbnail size="sm" src={albumCoverUrl} alt={`${title} 앨범 자켓`} />
      <SongTextContainer>
        <SongTitle>{title}</SongTitle>
        <Singer>{singer}</Singer>
      </SongTextContainer>
    </Flex>
  );
};

export default SongInformation;

const SongTextContainer = styled.div`
  max-width: calc(100% - 60px);
`;

const SongTitle = styled.p`
  overflow: hidden;
  font-size: 18px;
  font-weight: 700;
  color: ${({ theme: { color } }) => color.white};
  text-overflow: ellipsis;
  white-space: nowrap;

  @media (min-width: ${({ theme }) => theme.breakPoints.md}) {
    font-size: 22px;
  }
`;

const Singer = styled.p`
  overflow: hidden;
  font-size: 16px;
  font-weight: 700;
  color: ${({ theme: { color } }) => color.subText};
  text-overflow: ellipsis;
  white-space: nowrap;

  @media (max-width: ${({ theme }) => theme.breakPoints.md}) {
    font-size: 14px;
  }
`;
