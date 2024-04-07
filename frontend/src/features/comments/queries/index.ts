import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import { getComments, postComment } from '../remotes/comments';

export const useCommentsQuery = (songId: number, partId: number) => {
  const { data: comments, ...queries } = useQuery({
    queryKey: ['comments', songId, partId],
    queryFn: () => getComments(songId, partId),
  });

  return { comments, queries };
};

export const usePostCommentMutation = () => {
  const client = useQueryClient();

  const { mutate: postNewComment, ...mutations } = useMutation({
    mutationFn: postComment,
    onSuccess: (_, { songId, partId }) => {
      client.invalidateQueries({ queryKey: ['comments', songId, partId] });
    },
  });

  return { postNewComment, mutations };
};
