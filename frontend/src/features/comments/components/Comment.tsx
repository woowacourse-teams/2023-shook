import { styled } from 'styled-components';
import shookshook from '@/assets/icon/shookshook.svg';
import Spacing from '@/shared/components/Spacing';

interface CommentProps {
  content: string;
  createdAt: string;
}

// FIXME: 분리 및 포맷 정리, ~일 전 말고도 세분화 필요
const rtf = new Intl.RelativeTimeFormat('ko', {
  numeric: 'always',
});

const Comment = ({ content, createdAt }: CommentProps) => {
  const time = Math.ceil(
    (new Date(createdAt).getTime() - new Date().getTime()) / (1000 * 60 * 60 * 24)
  );

  return (
    <Wrapper>
      <Flex>
        <Profile>
          <img src={shookshook} alt="익명 프로필" />
        </Profile>
        <Spacing direction="horizontal" size={14} />
        <Box tabIndex={0} role="comment">
          <Username>익명</Username>
          <RelativeTime>{rtf.format(time, 'day')}</RelativeTime>
          <Content>{content}</Content>
        </Box>
      </Flex>
    </Wrapper>
  );
};

export default Comment;

const Wrapper = styled.li`
  width: 100%;
  margin-bottom: 16px;
`;

const Flex = styled.div`
  display: flex;
  width: 100%;
  align-items: flex-start;
`;

const Profile = styled.div`
  width: 40px;
  height: 40px;

  border-radius: 100%;
  background-color: white;

  overflow: hidden;
`;

const Box = styled.div`
  flex: 1;
`;

const Username = styled.span`
  font-size: 14px;
`;

const RelativeTime = styled.span`
  margin-left: 5px;
  font-size: 12px;
  color: #aaaaaa;
`;

const Content = styled.div`
  font-size: 14px;

  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 3;
  overflow: hidden;
`;
