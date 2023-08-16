import { useParams } from 'react-router-dom';
import SongDetailItem from '@/features/songs/components/SongDetailItem';
import { getSongDetailEntries } from '@/features/songs/remotes/songs';
import useFetch from '@/shared/hooks/useFetch';

const SongDetailListPage = () => {
  const { id: songIdParams } = useParams();
  const { data: songDetailEntries } = useFetch(() => getSongDetailEntries(Number(songIdParams)));
  if (!songDetailEntries) return;

  const { prevSongs, currentSong, nextSongs } = songDetailEntries;

  return (
    <div>
      {prevSongs.map((prevSongDetail) => (
        <SongDetailItem key={prevSongDetail.id} {...prevSongDetail} />
      ))}
      <SongDetailItem {...currentSong} />
      {nextSongs.map((nextSongDetail) => (
        <SongDetailItem key={nextSongDetail.id} {...nextSongDetail} />
      ))}
    </div>
  );
};

export default SongDetailListPage;
