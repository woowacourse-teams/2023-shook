import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import dummyJacket from '@/assets/image/album-jacket.png';
import VoteInterface from '@/components/VoteInterface/VoteInterface';
import Youtube from '@/components/Youtube/Youtube';
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

  const [videoPlayer, setVideoPlayer] = useState<YT.Player | undefined>();
  const updatePlayer = ({ target: player }: YT.PlayerEvent) => setVideoPlayer(player);

  // useEffect(() => {
  //   if (videoPlayer?.getPlayerState() === 2) videoPlayer.playVideo();

  //   const timer = window.setInterval(() => {
  //     const startSecond = minSecToSeconds([partStart.minute, partStart.second]);

  //     videoPlayer?.seekTo(startSecond, true);
  //   }, interval * 1000);

  //   return () => window.clearInterval(timer);
  // }, [player, partStart, interval]);

  if (!songDetail) return;
  const { id, title, singer, videoLength, songVideoUrl } = songDetail;
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
      <Youtube videoId={videoId} start={0} videoPlayer={videoPlayer} updatePlayer={updatePlayer} />
      <VoteInterface videoLength={videoLength} videoPlayer={videoPlayer} />
    </Container>
  );
};

export default SongDetailPage;
