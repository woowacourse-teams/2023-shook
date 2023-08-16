import { useState } from 'react';
import { Link } from 'react-router-dom';
import { css, styled } from 'styled-components';
import defaultAvatar from '@/assets/icon/avatar-default.svg';
import shookshook from '@/assets/icon/shookshook.svg';
import { useAuthContext } from '@/features/auth/components/AuthProvider';
import Avatar from '@/shared/components/Avatar';
import useToastContext from '@/shared/components/Toast/hooks/useToastContext';
import ROUTE_PATH from '@/shared/constants/path';
import { useMutation } from '@/shared/hooks/useMutation';
import fetcher from '@/shared/remotes';

interface CommentFormProps {
  getComment: () => Promise<void>;
  songId: string;
  partId: number;
}

const CommentForm = ({ getComment, songId, partId }: CommentFormProps) => {
  const [newComment, setNewComment] = useState('');
  const { user } = useAuthContext();

  const isLoggedIn = !!user;

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

  return (
    <Container onSubmit={submitNewComment}>
      <Flex>
        {isLoggedIn ? (
          <Avatar src={shookshook} alt="슉슉이" />
        ) : (
          <Avatar src={defaultAvatar} alt="익명 프로필" />
        )}
        {isLoggedIn ? (
          <Input
            type="text"
            value={newComment}
            onChange={changeNewComment}
            placeholder="댓글 추가..."
            maxLength={200}
          />
        ) : (
          <LinkBox
            to={`https://accounts.google.com/o/oauth2/v2/auth?scope=email&response_type=code&redirect_uri=${process.env.BASE_URL}${ROUTE_PATH.LOGIN_REDIRECT}&client_id=1008951336382-8o2n6n9u8jbj3sb6fe5jdeha9b6alnqa.apps.googleusercontent.com`}
          >
            <Input
              type="text"
              value={newComment}
              onChange={changeNewComment}
              placeholder="댓글 추가..."
              maxLength={200}
            />
          </LinkBox>
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

const LinkBox = styled(Link)`
  flex: 1;
`;

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

const Profile = styled.div`
  overflow: hidden;

  width: 40px;
  height: 40px;

  background-color: white;
  border-radius: 100%;
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
