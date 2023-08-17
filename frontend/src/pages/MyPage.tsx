import { useEffect, useState } from 'react';
import { styled } from 'styled-components';
import link from '@/assets/icon/link.svg';
import shook from '@/assets/icon/shook.svg';
import shookshook from '@/assets/icon/shookshook.svg';
import Thumbnail from '@/features/songs/components/Thumbnail';
import Flex from '@/shared/components/Flex';
import Spacing from '@/shared/components/Spacing';
import SRHeading from '@/shared/components/SRHeading';
import { secondsToMinSec, toPlayingTimeText } from '@/shared/utils/convertTime';
import type { KillingPart, SongDetail } from '@/shared/types/song';

const { BASE_URL } = process.env;

type LikeKillingPart = Pick<SongDetail, 'title' | 'singer' | 'albumCoverUrl'> &
  Pick<KillingPart, 'start' | 'end'> & {
    songId: number;
    partId: number;
  };

const MyPage = () => {
  const [likes, setLikes] = useState<LikeKillingPart[]>([]);

  useEffect(() => {
    const getLikeSongs = async () => {
      fetch(`${BASE_URL}/my-page`)
        .then((res) => res.json())
        .then((data) => setLikes(data as LikeKillingPart[]))
        .catch((error) => alert(error));
    };

    getLikeSongs();
  }, []);

  return (
    <>
      <SRHeading>마이 페이지</SRHeading>

      <SpaceBetween>
        <Box>
          <Title>아이고난</Title>
          <Spacing direction="vertical" size={6} />
          <Box>아무 노래나 일단 틀어</Box>
        </Box>
        <Avatar src={shookshook} alt="" />
      </SpaceBetween>

      <Spacing direction="vertical" size={24} />

      <SpaceBetween>
        <Button onClick={() => alert('기능이 준비중입니다!')}>프로필 편집</Button>
        <Button
          onClick={() =>
            alert(
              `당신의 로그아웃 점수는?\n100점 만점에 ${Math.floor(Math.random() * 101)}점 이에요!`
            )
          }
        >
          로그아웃
        </Button>
      </SpaceBetween>

      <Spacing direction="vertical" size={24} />

      <Subtitle>좋아요한 킬링파트 {likes.length}개</Subtitle>

      <Spacing direction="vertical" size={24} />

      <PopularSongList>
        {likes.map(({ title, singer, albumCoverUrl, partId, start, end }, i) => {
          return (
            <Li key={partId}>
              <LikePartItem
                rank={i + 1}
                albumCoverUrl={albumCoverUrl}
                title={title}
                singer={singer}
                start={start}
                end={end}
              />
            </Li>
          );
        })}
      </PopularSongList>
    </>
  );
};

export default MyPage;

const Box = styled.div`
  flex: 1;
  width: 100%;
`;

const Li = styled.li`
  width: 100%;
`;

const Title = styled.h2`
  align-self: flex-start;
  font-size: 20px;
  font-weight: 700;
  color: white;
`;

const PopularSongList = styled.ol`
  display: flex;
  flex-direction: column;
  gap: 12px;
  align-items: flex-start;

  width: 100%;
`;

const SpaceBetween = styled(Flex)`
  justify-content: space-between;
`;

const Shook = styled.img`
  width: 16px;
  height: 18px;
  border-radius: 50%;
`;

const Avatar = styled.img`
  width: 60px;
  height: 60px;
  border-radius: 50%;
`;

const ShareLink = styled.img`
  cursor: pointer;

  grid-area: share;

  width: 26px;
  height: 26px;
  padding: 2px;

  background-color: white;
  border-radius: 50%;
`;

const Button = styled.button`
  width: 48%;
  height: 40px;

  font-weight: 700;

  border: 1.6px solid ${({ theme }) => theme.color.secondary};
  border-radius: 12px;
`;

const Subtitle = styled.div`
  width: 100%;
  height: 36px;
  font-size: 18px;
  border-bottom: 1px solid ${({ theme }) => theme.color.white};
`;

type LikePartItemProps = Pick<
  LikeKillingPart,
  'albumCoverUrl' | 'title' | 'singer' | 'start' | 'end'
> & {
  rank: number;
};

const LikePartItem = ({ albumCoverUrl, title, singer, start, end }: LikePartItemProps) => {
  const { minute: startMin, second: startSec } = secondsToMinSec(start);
  const { minute: endMin, second: endSec } = secondsToMinSec(end);

  return (
    <Grid>
      <Thumbnail src={albumCoverUrl} alt={`${title}-${singer}`} />
      <SongTitle>{title}</SongTitle>
      <Singer>{singer}</Singer>
      <TimeWrapper>
        <Shook src={shook} alt="" />
        <Spacing direction="horizontal" size={4} />
        <p
          tabIndex={0}
          aria-label={`킬링파트 구간 ${startMin}분 ${startSec}초부터 ${endMin}분 ${endSec}초`}
        >
          {toPlayingTimeText(start, end)}
        </p>
        <Spacing direction="horizontal" size={10} />
      </TimeWrapper>
      <ShareLink src={link} alt="영상 링크 공유하기" />
    </Grid>
  );
};

const Grid = styled.div`
  display: grid;
  grid-template:
    'thumbnail title _' 26px
    'thumbnail singer share' 26px
    'thumbnail info share' 18px
    / 70px auto 28px;
  column-gap: 8px;

  padding: 6px 0;

  color: ${({ theme: { color } }) => color.white};
`;

const SongTitle = styled.div`
  overflow: hidden;
  grid-area: title;

  font-size: 16px;
  font-weight: 800;
  text-overflow: ellipsis;
  white-space: nowrap;
`;

const Singer = styled.div`
  overflow: hidden;
  grid-area: singer;

  font-size: 12px;
  text-overflow: ellipsis;
  white-space: nowrap;
`;

const TimeWrapper = styled.div`
  display: flex;
  grid-area: info;

  font-size: 14px;
  font-weight: 700;
  color: ${({ theme: { color } }) => color.primary};
  letter-spacing: 1px;
`;
