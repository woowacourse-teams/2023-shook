import styled from 'styled-components';
import Flex from '@/shared/components/Flex/Flex';
import { toMinSecText } from '@/shared/utils/convertTime';
import type { SingersSong } from '@/features/singer/types/singer.type';

interface SingerSongItemProps extends SingersSong {}

const SingerSongItem = ({ id, singer, albumCoverUrl, title, videoLength }: SingerSongItemProps) => {
  return (
    <SongListItem as="li" key={id} $gap={16} $justify="center" $xs={{ $align: 'center' }}>
      <AlbumCoverWrapper>
        <AlbumCover src={albumCoverUrl} />
      </AlbumCoverWrapper>
      <FlexInfo $direction="column" $gap={8} $xs={{ $gap: 4 }}>
        <SongTitle>{title}</SongTitle>
        <Singer>{singer}</Singer>
      </FlexInfo>
      <VideoLength $align="center" $justify="flex-end">
        {toMinSecText(videoLength)}
      </VideoLength>
    </SongListItem>
  );
};

export default SingerSongItem;

const SongListItem = styled(Flex)`
  width: 100%;
  padding: 8px;
  color: ${({ theme: { color } }) => color.white};
  border-radius: 4px;

  @media (hover: hover) {
    &:hover {
      background-color: ${({ theme: { color } }) => color.secondary};
    }
  }
`;

const AlbumCoverWrapper = styled.div`
  aspect-ratio: 1 / 1;
  width: 100px;

  @media (max-width: ${({ theme }) => theme.breakPoints.md}) {
    width: 70px;
  }
`;

const AlbumCover = styled.img`
  width: 100%;
`;

const FlexInfo = styled(Flex)`
  overflow: hidden;
  flex: 3 1 0;

  padding: 8px 0;

  text-overflow: ellipsis;
  white-space: nowrap;

  @media (max-width: ${({ theme }) => theme.breakPoints.md}) {
    padding: 6px 0;
  }
`;

const SongTitle = styled.div`
  overflow: hidden;

  font-size: 16px;
  font-weight: 700;
  text-overflow: ellipsis;
  white-space: nowrap;
`;

const Singer = styled.div`
  overflow: hidden;

  font-size: 14px;
  color: ${({ theme: { color } }) => color.subText};
  text-overflow: ellipsis;
  white-space: nowrap;
`;

const VideoLength = styled(Flex)`
  flex: 1 0 0;
  font-size: 16px;

  @media (max-width: ${({ theme }) => theme.breakPoints.md}) {
    font-size: 14px;
  }
`;
