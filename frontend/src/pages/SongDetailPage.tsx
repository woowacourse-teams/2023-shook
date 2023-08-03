import { useParams } from 'react-router-dom';
import dummyJacket from '@/assets/image/album-jacket.png';
import { VoteInterface, VoteInterfaceProvider } from '@/components/VoteInterface';
import { VideoPlayerProvider, Youtube } from '@/components/Youtube';
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
  // TODO: Jacket img src API 추가 협의
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
        <Youtube videoId={videoId} />
        <VoteInterfaceProvider>
          <VoteInterface videoLength={videoLength} songId={id} />
        </VoteInterfaceProvider>
      </VideoPlayerProvider>
    </Container>
  );
};

export default SongDetailPage;
