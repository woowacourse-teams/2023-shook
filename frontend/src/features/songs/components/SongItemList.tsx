import { Link } from 'react-router-dom';
import styled from 'styled-components';
import SongItem from '@/features/songs/components/SongItem';
import Spacing from '@/shared/components/Spacing';
import ROUTE_PATH from '@/shared/constants/path';
import useFetch from '@/shared/hooks/useFetch';
import GENRES from '../constants/genres';
import { getHighLikedSongs } from '../remotes/song';
import type { Genre, Song } from '../types/Song.type';

interface SongItemListProps {
  genre: Genre;
}

const SongItemList = ({ genre }: SongItemListProps) => {
  const { data: songs } = useFetch<Song[]>(() => getHighLikedSongs(genre));

  if (songs === null || songs?.length === 0) return;

  return (
    <>
      <Title>{`${GENRES[genre]} Top 10`}</Title>
      <Spacing direction="vertical" size={16} />
      <SongList>
        {songs?.map(({ id, albumCoverUrl, title, singer, totalLikeCount }, i) => (
          <Li key={id}>
            <StyledLink
              to={`${ROUTE_PATH.SONG_DETAILS}/${id}/${genre}`}
              aria-label={`${GENRES[genre]} 장르 ${i + 1}등 ${singer} ${title}`}
            >
              <SongItem
                rank={i + 1}
                albumCoverUrl={albumCoverUrl}
                title={title}
                singer={singer}
                totalLikeCount={totalLikeCount}
              />
            </StyledLink>
          </Li>
        ))}
      </SongList>
      <Spacing direction="vertical" size={30} />
    </>
  );
};

export default SongItemList;

const SongList = styled.ol`
  scroll-snap-type: x mandatory;

  overflow-x: scroll;
  display: flex;
  flex-direction: row;
  gap: 12px;
  align-items: flex-start;

  width: 100%;
`;

const Li = styled.li`
  scroll-snap-align: center;
  scroll-snap-stop: normal;
  max-width: 130px;
`;

const StyledLink = styled(Link)`
  width: 100%;

  &:hover,
  &:focus {
    background-color: ${({ theme }) => theme.color.secondary};
  }
`;

const Title = styled.h2`
  align-self: flex-start;
  font-size: 18px;
  font-weight: 700;
  color: white;
`;
