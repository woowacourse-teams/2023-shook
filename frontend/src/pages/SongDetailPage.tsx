import { useParams } from 'react-router-dom';
import { styled } from 'styled-components';
import KillingPartInterface from '@/features/songs/components/KillingPartInterface';
import Thumbnail from '@/features/songs/components/Thumbnail';
import { useGetSongDetail } from '@/features/songs/remotes/useGetSongDetail';
import { VideoPlayerProvider } from '@/features/youtube/components/VideoPlayerProvider';
import Youtube from '@/features/youtube/components/Youtube';
import Flex from '@/shared/components/Flex';
import Spacing from '@/shared/components/Spacing';
import SRHeading from '@/shared/components/SRHeading';

const SongDetailPage = () => {
  const { id: songIdParams = '' } = useParams();
  const { songDetail } = useGetSongDetail(Number(songIdParams));

  if (!songDetail) return;
  const { id: songId, killingParts, singer, title, songVideoUrl, albumCoverUrl } = songDetail;

  const videoId = songVideoUrl.replace('https://youtu.be/', '');

  return (
    <Wrapper>
      <SRHeading>킬링파트 듣기 페이지</SRHeading>
      <BigTitle aria-label="킬링파트 듣기">킬링파트 듣기</BigTitle>
      <Spacing direction="vertical" size={20} />
      <SongInfoContainer>
        <Thumbnail src={albumCoverUrl} size="md" />
        <Info>
          <SongTitle aria-label={`노래 ${title}`}>{title}</SongTitle>
          <Singer aria-label={`가수 ${singer}`}>{singer}</Singer>
        </Info>
      </SongInfoContainer>
      <Spacing direction="vertical" size={20} />
      <VideoPlayerProvider>
        <Youtube videoId={videoId} />
        <Spacing direction="vertical" size={16} />
        <KillingPartInterface killingParts={killingParts} songId={songId} />
      </VideoPlayerProvider>
    </Wrapper>
  );
};

export default SongDetailPage;

const BigTitle = styled.h2`
  font-size: 28px;
  font-weight: 700;
`;

const Wrapper = styled(Flex)`
  flex-direction: column;
`;

const SongInfoContainer = styled.div`
  overflow: hidden;
  display: flex;
  gap: 12px;
  align-items: center;

  width: 100%;

  text-overflow: ellipsis;
  white-space: nowrap;
`;

const Info = styled.div``;

const SongTitle = styled.div`
  height: 30px;
  font-size: 20px;
  font-weight: 700;
  color: ${({ theme: { color } }) => color.white};

  @media (max-width: ${({ theme }) => theme.breakPoints.md}) {
    font-size: 20px;
  }
`;

const Singer = styled.div`
  font-size: 18px;
  font-weight: 700;
  color: ${({ theme: { color } }) => color.subText};

  @media (max-width: ${({ theme }) => theme.breakPoints.md}) {
    font-size: 16px;
  }
`;
