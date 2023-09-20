import { Link } from 'react-router-dom';
import styled from 'styled-components';
import SongItem from '@/features/songs/components/SongItem';
import Spacing from '@/shared/components/Spacing';
import ROUTE_PATH from '@/shared/constants/path';
import useFetch from '@/shared/hooks/useFetch';
import fetcher from '@/shared/remotes';
import type { Song } from '../types/Song.type';

const SongItemList = () => {
  const { data: popularSongs } = useFetch<Song[]>(() => fetcher('/songs/high-liked', 'GET'));

  return (
    <>
      <Title>킬링파트 좋아요 많은순</Title>
      <Spacing direction="vertical" size={16} />
      <SongList>
        {popularSongs?.map(({ id, albumCoverUrl, title, singer, totalLikeCount }, i) => (
          <Li key={id}>
            <StyledLink
              to={`${ROUTE_PATH.SONG_DETAILS}/${id}`}
              aria-label={`킬링파트 투표순 ${i + 1}등 ${singer} ${title}`}
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
    </>
  );
};

export default SongItemList;

const Li = styled.li`
  max-width: 250px;
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
  font-size: 20px;
  font-weight: 700;
  color: white;
`;

const SongList = styled.ol`
  overflow-x: scroll;
  display: flex;
  flex-direction: row;
  gap: 8px;
  align-items: flex-start;

  width: 100%;
`;
