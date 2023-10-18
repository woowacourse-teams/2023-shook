import styled from 'styled-components';
import Flex from '@/shared/components/Flex/Flex';
import Spacing from '@/shared/components/Spacing';
import type { SingerDetail } from '@/features/singer/types/singer.type';

interface SingerBannerProps
  extends Pick<SingerDetail, 'profileImageUrl' | 'singer' | 'totalSongCount'> {}

const SingerBanner = ({ profileImageUrl, singer, totalSongCount }: SingerBannerProps) => {
  return (
    <SingerInfoContainer $align=" center" $gap={24}>
      <ProfileImage src={profileImageUrl} alt="" />
      <Flex $direction="column" $gap={16}>
        <Name>{singer}</Name>
        <SongCount>{`등록된 노래 ${totalSongCount}개`}</SongCount>
      </Flex>
    </SingerInfoContainer>
  );
};

export default SingerBanner;

const Title = styled.h2`
  font-size: 28px;
  font-weight: 700;
`;

const SingerInfoContainer = styled(Flex)`
  width: 370px;
  height: 240px;
  padding: 20px;

  background-color: ${({ theme: { color } }) => color.black500};
  border-radius: 8px;

  @media (hover: hover) {
    &:hover {
      background-color: ${({ theme: { color } }) => color.secondary};
    }
  }

  @media (max-width: ${({ theme }) => theme.breakPoints.md}) {
    width: 100%;
    height: 180px;
  }
`;

const ProfileImage = styled.img`
  width: 100px;
  height: 100px;
  border-radius: 50%;
`;

const Name = styled.div`
  font-size: 36px;
  font-weight: 700;

  @media (max-width: ${({ theme }) => theme.breakPoints.md}) {
    font-size: 20px;
  }
`;

const SongCount = styled.p`
  font-size: 18px;

  @media (max-width: ${({ theme }) => theme.breakPoints.md}) {
    font-size: 14px;
  }
`;
