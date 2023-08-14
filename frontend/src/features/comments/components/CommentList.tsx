import { useEffect, useState } from 'react';
import { css, styled } from 'styled-components';
import shookshook from '@/assets/icon/shookshook.svg';
import Spacing from '@/shared/components/Spacing';
import SRHeading from '@/shared/components/SRHeading';
import useToastContext from '@/shared/components/Toast/hooks/useToastContext';
import useFetch from '@/shared/hooks/useFetch';
import { useMutation } from '@/shared/hooks/useMutation';
import fetcher from '@/shared/remotes';
import Comment from './Comment';

interface Comment {
  id: number;
  content: string;
  createdAt: string;
}

interface CommentListProps {
  songId: string;
  partId: number;
}

const CommentList = ({ songId, partId }: CommentListProps) => {
  const [newComment, setNewComment] = useState('');

  const { data: comments, fetchData: getComment } = useFetch<Comment[]>(() =>
    fetcher(`/songs/${songId}/parts/${partId}/comments`, 'GET')
  );

  const { mutateData } = useMutation(() =>
    fetcher(`/songs/${songId}/parts/${partId}/comments`, 'POST', { content: newComment.trim() })
  );
  const { showToast } = useToastContext();

  const resetNewComment = () => setNewComment('');

  const changeNewComment: React.ChangeEventHandler<HTMLInputElement> = ({
    currentTarget: { value },
  }) => setNewComment(value);

  const submitNewComment: React.FormEventHandler<HTMLFormElement> = async (event) => {
    event.preventDefault();

    await mutateData();

    showToast('댓글이 등록되었습니다.');
    resetNewComment();
    getComment();
  };

  useEffect(() => {
    getComment();
  }, [partId]);

  if (!comments) {
    return null;
  }

  return (
    <>
      <Spacing direction="vertical" size={24} />
      <SRHeading as="h3">댓글 목록</SRHeading>
      <p>댓글 {comments.length}개</p>
      <Spacing direction="vertical" size={24} />
      <form onSubmit={submitNewComment}>
        <Flex>
          <Profile>
            <img src={shookshook} alt="익명 프로필" />
          </Profile>
          <Input
            type="text"
            value={newComment}
            onChange={changeNewComment}
            placeholder="댓글 추가..."
            maxLength={200}
          />
        </Flex>
        <FlexEnd>
          <Cancel type="button" onClick={resetNewComment} aria-label="댓글 작성 취소">
            취소
          </Cancel>
          <Submit aria-label="댓글 작성 완료" disabled={newComment.trim() === ''}>
            댓글
          </Submit>
        </FlexEnd>
      </form>
      <Spacing direction="vertical" size={20} />
      <Comments>
        {comments.map(({ id, content, createdAt }) => (
          <Comment key={id} content={content} createdAt={createdAt} />
        ))}
      </Comments>
    </>
  );
};

export default CommentList;

const Flex = styled.div`
  display: flex;
  gap: 14px;
  align-items: flex-start;
`;

const Profile = styled.div`
  overflow: hidden;

  width: 40px;
  height: 40px;

  background-color: white;
  border-radius: 100%;
`;

const Input = styled.input`
  flex: 1;

  margin: 0 8px;
  margin: 0;
  padding: 0;

  font-size: 14px;

  background-color: transparent;
  border: none;
  border-bottom: 1px solid white;
  outline: none;
  -webkit-box-shadow: none;
  box-shadow: none;
`;

const FlexEnd = styled.div`
  display: flex;
  gap: 10px;
  justify-content: flex-end;
`;

const buttonBase = css`
  width: 50px;
  height: 36px;
  font-size: 14px;
  border-radius: 10px;
`;

const Cancel = styled.button`
  ${buttonBase}

  &:hover,
  &:focus {
    background-color: ${({ theme }) => theme.color.secondary};
  }
`;

const Submit = styled.button`
  ${buttonBase}
  background-color: ${({ theme }) => theme.color.primary};

  &:hover,
  &:focus {
    background-color: #de5484;
  }

  &:disabled {
    background-color: ${({ theme }) => theme.color.secondary};
  }
`;

const Comments = styled.ol`
  gap: 10px;
`;
