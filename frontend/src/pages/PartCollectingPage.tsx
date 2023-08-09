import { useParams } from 'react-router-dom';
import { styled } from 'styled-components';
import Thumbnail from '@/features/songs/components/Thumbnail';
import VoteInterface from '@/features/songs/components/VoteInterface';
import { VoteInterfaceProvider } from '@/features/songs/components/VoteInterfaceProvider';
import { useGetSongDetail } from '@/features/songs/remotes/useGetSongDetail';
import { VideoPlayerProvider } from '@/features/youtube/components/VideoPlayerProvider';
import Youtube from '@/features/youtube/components/Youtube';

const PartCollectingPage = () => {
  const { id: songIdParam } = useParams();
  const { songDetail } = useGetSongDetail(Number(songIdParam));

  if (!songDetail) return;
  const { id, title, singer, videoLength, songVideoUrl, albumCoverUrl } = songDetail;
  // TODO: videoId 자체가 응답값으로 오도록 API 협의
  // TODO: Jacket img src API 추가 협의
  const videoId = songVideoUrl.replace('https://youtu.be/', '');

  return (
    <Container>
      <BigTitle>킬링파트 투표 🔖</BigTitle>
      <SongInfoContainer>
        <Thumbnail src={albumCoverUrl} alt={`${title} 앨범 자켓`} />
        <Info>
          <SongTitle>{title}</SongTitle>
          <Singer>{singer}</Singer>
        </Info>
      </SongInfoContainer>
      <VideoPlayerProvider>
        <Youtube videoId={videoId} />
        <VoteInterfaceProvider>
          <VoteInterface videoLength={videoLength} songId={id} />
        </VoteInterfaceProvider>
      </VideoPlayerProvider>
    </Container>
  );
};

export default PartCollectingPage;

const Container = styled.section`
  display: flex;
  width: 100%;
  flex-direction: column;
  gap: 20px;
`;

const SongInfoContainer = styled.div`
  display: flex;
  align-items: center;
  gap: 12px;
`;

const Info = styled.div``;

const SongTitle = styled.p`
  font-size: 24px;
  font-weight: 700;
  color: ${({ theme: { color } }) => color.white};

  @media (max-width: ${({ theme }) => theme.breakPoints.md}) {
    font-size: 20px;
  }
`;

const Singer = styled.p`
  font-size: 18px;
  font-weight: 700;
  color: ${({ theme: { color } }) => color.subText};

  @media (max-width: ${({ theme }) => theme.breakPoints.md}) {
    font-size: 16px;
  }
`;

const BigTitle = styled.h2`
  font-size: 28px;
  font-weight: 700;
`;
