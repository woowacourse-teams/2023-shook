import { useMutation, useQueryClient } from '@tanstack/react-query';
import { useState } from 'react';
import { css, styled } from 'styled-components';
import defaultAvatar from '@/assets/icon/avatar-default.svg';
import shookshook from '@/assets/icon/shookshook.svg';
import { useAuthContext } from '@/features/auth/components/AuthProvider';
import LoginModal from '@/features/auth/components/LoginModal';
import Avatar from '@/shared/components/Avatar';
import useToastContext from '@/shared/components/Toast/hooks/useToastContext';
import { useOverlay } from '@/shared/hooks/useOverlay';
import { postComment } from '../remotes/comments';

interface CommentFormProps {
  songId: number;
  partId: number;
}

const CommentForm = ({ songId, partId }: CommentFormProps) => {
  const [newComment, setNewComment] = useState('');
  const [isLoginModalOpen, setIsLoginModalOpen] = useState(false);
  const overlay = useOverlay();
  const queryClient = useQueryClient();

  const openLoginModal = () => {
    setIsLoginModalOpen(true);

    overlay.open(({ isOpen, close }) => (
      <LoginModal
        isOpen={isOpen}
        closeModal={() => {
          setIsLoginModalOpen(false);
          close();
        }}
        message={'로그인하고 댓글을 작성해 보세요!'}
      />
    ));
  };

  const { user } = useAuthContext();

  const isLoggedIn = !!user;

  const { mutate: postNewComment, isPending: isPendingPostComment } = useMutation({
    mutationFn: postComment,
    onSuccess: (_, { songId, partId }) => {
      queryClient.invalidateQueries({ queryKey: ['comments', songId, partId] });
    },
  });

  const { showToast } = useToastContext();

  const resetNewComment = () => setNewComment('');

  const changeNewComment: React.ChangeEventHandler<HTMLInputElement> = ({
    currentTarget: { value },
  }) => setNewComment(value);

  const submitNewComment: React.FormEventHandler<HTMLFormElement> = (event) => {
    event.preventDefault();

    postNewComment(
      { songId, partId, content: newComment.trim() },
      {
        onSuccess: () => {
          showToast('댓글이 등록되었습니다.');
          resetNewComment();
        },
      }
    );
  };

  return (
    <Container onSubmit={submitNewComment}>
      <Flex>
        {isLoggedIn ? (
          <>
            <Avatar src={shookshook} alt="슉슉이" />
            <Input
              type="text"
              disabled={isPendingPostComment}
              value={newComment}
              onChange={changeNewComment}
              placeholder="댓글 추가..."
              maxLength={200}
            />
          </>
        ) : (
          <>
            <Avatar src={defaultAvatar} alt="익명 프로필" />
            <Input
              type="text"
              onFocus={openLoginModal}
              placeholder="댓글 추가..."
              disabled={isLoginModalOpen}
            />
          </>
        )}
      </Flex>
      {isLoggedIn && (
        <FlexEnd>
          <Cancel type="button" onClick={resetNewComment} aria-label="댓글 작성 취소">
            취소
          </Cancel>
          <Submit aria-label="댓글 작성 완료" disabled={newComment.trim() === ''}>
            댓글
          </Submit>
        </FlexEnd>
      )}
    </Container>
  );
};

export default CommentForm;

const Flex = styled.div`
  display: flex;
  gap: 14px;
  align-items: flex-start;
`;

const Container = styled.form`
  bottom: 0;

  width: 100%;
  padding: 16px;

  background-color: ${({ theme }) => theme.color.black};
  border-top: 1px solid ${({ theme }) => theme.color.white};
`;

const Input = styled.input`
  flex: 1;

  width: 100%;
  margin: 0;
  padding: 0;

  font-size: 14px;

  background-color: transparent;
  border: none;
  border-bottom: 1px solid white;
  outline: none;
  -webkit-box-shadow: none;
  box-shadow: none;

  &:disabled {
    color: ${({ theme: { color } }) => color.disabledBackground};
  }
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
  ${buttonBase};
  background-color: ${({ theme }) => theme.color.primary};

  &:hover,
  &:focus {
    background-color: #de5484;
  }

  &:disabled {
    background-color: ${({ theme }) => theme.color.secondary};
  }
`;
