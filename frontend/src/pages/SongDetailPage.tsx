import { useParams } from 'react-router-dom';
import dummyJacket from '@/assets/image/album-jacket.png';
import VoteInterface from '@/components/VoteInterface/VoteInterface';
import Youtube from '@/components/Youtube/Youtube';
import { VideoPlayerProvider } from '@/context/VideoPlayerProvider';
import { VoteInterfaceProvider } from '@/context/VoteInterfaceProvider';
import { useGetSongDetail } from '@/hooks/song';
import {
  Container,
  Singer,
  Jacket,
  SongTitle,
  SongInfoContainer,
  Info,
} from './SongDetailPage.style';

const SongDetailPage = () => {
  const { id: songIdParam } = useParams();
  const { songDetail } = useGetSongDetail(Number(songIdParam));

  if (!songDetail) return;
  const { id, title, singer, videoLength, songVideoUrl } = songDetail;
  // TODO: videoId 자체가 응답값으로 오도록 API 협의
  const videoId = songVideoUrl.replace('https://youtu.be/', '');

  return (
    <Container>
      <SongInfoContainer>
        <Jacket src={dummyJacket} alt={`${title} 앨범 자켓`} />
        <Info>
          <SongTitle>{title}</SongTitle>
          <Singer>{singer}</Singer>
        </Info>
      </SongInfoContainer>
      <VideoPlayerProvider>
        <VoteInterfaceProvider>
          <Youtube videoId={videoId} />
          <VoteInterface videoLength={videoLength} songId={id} />
        </VoteInterfaceProvider>
      </VideoPlayerProvider>
    </Container>
  );
};

export default SongDetailPage;
