import { useEffect } from 'react';
import { styled } from 'styled-components';
import cancelIcon from '@/assets/icon/cancel.svg';
import BottomSheet from '@/shared/components/BottomSheet/BottomSheet';
import useModal from '@/shared/components/Modal/hooks/useModal';
import Spacing from '@/shared/components/Spacing';
import SRHeading from '@/shared/components/SRHeading';
import useFetch from '@/shared/hooks/useFetch';
import { getComments } from '../remotes/comments';
import Comment from './Comment';
import CommentForm from './CommentForm';

interface CommentListProps {
  songId: number;
  partId: number;
}

const CommentList = ({ songId, partId }: CommentListProps) => {
  const { isOpen, openModal, closeModal } = useModal(false);
  const { data: comments, fetchData: refetchComments } = useFetch(() =>
    getComments(songId, partId)
  );

  useEffect(() => {
    refetchComments();
  }, [partId]);

  if (!comments) {
    return null;
  }

  const isEmptyComment = comments.length === 0;

  return (
    <>
      <Spacing direction="vertical" size={16} />
      <SRHeading as="h3">댓글 목록</SRHeading>
      <CommentTitle>댓글 {comments.length}개</CommentTitle>
      <Spacing direction="vertical" size={12} />
      <CommentWrapper onClick={openModal}>
        {isEmptyComment ? (
          <Comment.DefaultComment />
        ) : (
          <Comment
            content={comments[0].content}
            createdAt={comments[0].createdAt}
            writerNickname={comments[0].writerNickname}
          />
        )}
      </CommentWrapper>
      <BottomSheet isOpen={isOpen} closeModal={closeModal}>
        <Spacing direction="vertical" size={16} />
        <div style={{ display: 'flex', justifyContent: 'space-between' }}>
          <CommentsTitle>댓글 {comments.length}개</CommentsTitle>
          <CloseImg src={cancelIcon} onClick={closeModal} />
        </div>
        <Spacing direction="vertical" size={20} />
        <Comments>
          {comments.map(({ id, content, createdAt, writerNickname }) => (
            <Comment
              key={id}
              content={content}
              createdAt={createdAt}
              writerNickname={writerNickname}
            />
          ))}
        </Comments>
        <Spacing direction="vertical" size={8} />
        <CommentForm getComments={refetchComments} songId={songId} partId={partId} />
      </BottomSheet>
    </>
  );
};

export default CommentList;

const Comments = styled.ol`
  overflow-y: scroll;
  display: flex;
  flex-direction: column;
  flex-shrink: unset;
  row-gap: 10px;

  height: 100%;
  padding: 0 16px;
`;

const CommentWrapper = styled.button`
  display: flex;
  align-items: center;
  justify-content: center;

  padding: 16px;

  text-align: start;

  background-color: ${({ theme }) => theme.color.secondary};
  border-radius: 4px;

  @media (max-width: ${({ theme }) => theme.breakPoints.xxs}) {
    padding: 8px;
  }
`;

const CommentTitle = styled.p`
  font-size: 18px;
  font-weight: 700;

  @media (max-width: ${({ theme }) => theme.breakPoints.xxs}) {
    font-size: 16px;
    font-weight: 700;
  }
`;

const CommentsTitle = styled.p`
  padding-left: 16px;
`;

const CloseImg = styled.img`
  position: fixed;
  right: 16px;
  width: 24px;
  height: 24px;
`;
