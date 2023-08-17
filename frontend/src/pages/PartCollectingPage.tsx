import { useParams } from 'react-router-dom';
import { styled } from 'styled-components';
import Thumbnail from '@/features/songs/components/Thumbnail';
import VoteInterface from '@/features/songs/components/VoteInterface';
import { VoteInterfaceProvider } from '@/features/songs/components/VoteInterfaceProvider';
import { VideoPlayerProvider } from '@/features/youtube/components/VideoPlayerProvider';
import Youtube from '@/features/youtube/components/Youtube';
import useFetch from '@/shared/hooks/useFetch';
import fetcher from '@/shared/remotes';
import type { VotingSongList } from '@/shared/types/song';

const PartCollectingPage = () => {
  const { id: songId } = useParams();
  const { data: votingSongs } = useFetch<VotingSongList>(() =>
    fetcher(`/voting-songs/${songId}`, 'GET')
  );

  if (!votingSongs) return;
  const { id, title, singer, videoLength, songVideoId, albumCoverUrl } = votingSongs.currentSong;

  return (
    <Container>
      <BigTitle>킬링파트 수집</BigTitle>
      <SongInfoContainer>
        <Thumbnail src={albumCoverUrl} alt={`${title} 앨범 자켓`} />
        <Info>
          <SongTitle>{title}</SongTitle>
          <Singer>{singer}</Singer>
        </Info>
      </SongInfoContainer>
      <VideoPlayerProvider>
        <Youtube videoId={songVideoId} />
        <VoteInterfaceProvider videoLength={videoLength} songId={id}>
          <VoteInterface />
        </VoteInterfaceProvider>
      </VideoPlayerProvider>
    </Container>
  );
};

export default PartCollectingPage;

const Container = styled.section`
  display: flex;
  flex-direction: column;
  gap: 20px;
  width: 100%;
`;

const SongInfoContainer = styled.div`
  display: flex;
  gap: 12px;
  align-items: center;
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
