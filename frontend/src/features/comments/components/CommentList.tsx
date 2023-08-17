import { useEffect } from 'react';
import { styled } from 'styled-components';
import cancelIcon from '@/assets/icon/cancel.svg';
import BottomSheet from '@/shared/components/BottomSheet/BottomSheet';
import useModal from '@/shared/components/Modal/hooks/useModal';
import Spacing from '@/shared/components/Spacing';
import SRHeading from '@/shared/components/SRHeading';
import useFetch from '@/shared/hooks/useFetch';
import fetcher from '@/shared/remotes';
import Comment from './Comment';
import CommentForm from './CommentForm';

interface Comment {
  id: number;
  content: string;
  createdAt: string;
}

interface CommentListProps {
  songId: number;
  partId: number;
}

const CommentList = ({ songId, partId }: CommentListProps) => {

  const { showToast } = useToastContext();
  const [newComment, setNewComment] = useState('');


  const { isOpen, openModal, closeModal } = useModal(false);

  const { data: comments, fetchData: getComment } = useFetch<Comment[]>(() =>
    fetcher(`/songs/${songId}/parts/${partId}/comments`, 'GET')
  );


  const { mutateData } = useMutation(() =>
    fetcher(`/songs/${songId}/parts/${partId}/comments`, 'POST', { content: newComment.trim() })
  );

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

  return (
    <>
      <Spacing direction="vertical" size={24} />
      <SRHeading as="h3">댓글 목록</SRHeading>

      <p>댓글 {comments?.length ?? 0}개</p>
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
        {comments?.map(({ id, content, createdAt }) => (
          <Comment key={id} content={content} createdAt={createdAt} />
        ))}
      </Comments>

      <CommentTitle>댓글 {comments.length}개</CommentTitle>
      <Spacing direction="vertical" size={12} />
      <CommentWrapper onClick={openModal}>
        <Comment
          key={comments[0].id}
          content={comments[0].content}
          createdAt={comments[0].createdAt}
        />
      </CommentWrapper>
      <BottomSheet isOpen={isOpen} closeModal={closeModal}>
        <Spacing direction="vertical" size={16} />
        <div style={{ display: 'flex', justifyContent: 'space-between' }}>
          <CommentsTitle>댓글 {comments.length}개</CommentsTitle>
          <CloseImg src={cancelIcon} onClick={closeModal} />
        </div>
        <Spacing direction="vertical" size={20} />
        <Comments>
          {comments.map(({ id, content, createdAt }) => (
            <Comment key={id} content={content} createdAt={createdAt} />
          ))}
        </Comments>
        <Spacing direction="vertical" size={8} />
        <CommentForm getComment={getComment} songId={songId} partId={partId} />
      </BottomSheet>

    </>
  );
};

export default CommentList;

const Comments = styled.ol`
  overflow-y: scroll;
  display: flex;
  flex-shrink: unset;
  flex-direction: column;
  row-gap: 10px;

  padding: 0 16px;
`;

const CommentWrapper = styled.button`
  display: flex;
  align-items: center;
  justify-content: center;
  text-align: start;
  padding: 16px;

  background-color: ${({ theme }) => theme.color.secondary};
  border-radius: 4px;
`;

const CommentTitle = styled.p`
  font-size: 20px;
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
