import { queryOptions, useMutation, useQueryClient } from '@tanstack/react-query';
import { getComments, postComment } from '../remotes/comments';

export const commentsQueryOptions = (songId: number, partId: number) =>
  queryOptions({
    queryKey: ['comments', songId, partId],
    queryFn: () => getComments(songId, partId),
  });

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
