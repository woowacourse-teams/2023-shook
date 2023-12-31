import styled from 'styled-components';
import shookshook from '@/assets/icon/shookshook.svg';
import Avatar from '@/shared/components/Avatar';
import Spacing from '@/shared/components/Spacing';
import convertRelativeTimeString from '@/shared/utils/convertRelativeTimeString';

interface CommentProps {
  content: string;
  createdAt: string;
  writerNickname: string;
}

const Comment = ({ content, createdAt, writerNickname }: CommentProps) => {
  return (
    <Wrapper>
      <Flex>
        <Avatar src={shookshook} alt="익명 프로필" />
        <Spacing direction="horizontal" size={14} />
        <Box tabIndex={0} role="comment">
          <Username>{writerNickname}</Username>
          <RelativeTime>{convertRelativeTimeString(createdAt)}</RelativeTime>
          <Content>{content}</Content>
        </Box>
      </Flex>
    </Wrapper>
  );
};

const DefaultComment = () => {
  return (
    <Wrapper>
      <Flex>
        <Avatar src={shookshook} alt="익명 프로필" />
        <Spacing direction="horizontal" size={14} />
        <Box tabIndex={0} role="comment">
          <Username>[슉슉이]</Username>
          <Content>아직 등록된 댓글이 없어요</Content>
        </Box>
      </Flex>
    </Wrapper>
  );
};

Comment.DefaultComment = DefaultComment;

export default Comment;

const Wrapper = styled.li`
  width: 100%;
  list-style: none;
`;

const Flex = styled.div`
  display: flex;
  align-items: flex-start;
  width: 100%;
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
  overflow: hidden;
  display: -webkit-box;

  margin-right: 16px;

  font-size: 14px;

  -webkit-box-orient: vertical;
  -webkit-line-clamp: 3;
`;
