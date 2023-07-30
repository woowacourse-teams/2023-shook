import { useState } from 'react';
import { useParams } from 'react-router-dom';
import { styled } from 'styled-components';
import Youtube from '@/components/Youtube/Youtube';
import { useGetSongDetail } from '@/hooks/song';

const SongPage = () => {
  const { id } = useParams();
  const [player, setPlayer] = useState<YT.Player | undefined>();

  const { songDetail } = useGetSongDetail(Number(id));
  if (!songDetail) return;
  const { killingParts, singer, title, videoLength, videoUrl } = songDetail;

  // TODO: videoId 구하는 util함수 분리
  const videoId = videoUrl.replace('https://youtu.be/', '');

  const setPlayerAfterReady = ({ target }: YT.PlayerEvent) => {
    setPlayer(target);
  };

  return (
    <Container>
      SongPage
      <Youtube videoId={videoId} start={0} onReady={setPlayerAfterReady} />
    </Container>
  );
};

export default SongPage;

const Container = styled.section`
  display: flex;
  width: 100%;
  flex-direction: column;
  color: white;
`;
