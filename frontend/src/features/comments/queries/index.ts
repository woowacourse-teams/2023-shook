import { queryOptions } from '@tanstack/react-query';
import { getComments } from '../remotes/comments';

export const commentsQueryOptions = (songId: number, partId: number) =>
  queryOptions({
    queryKey: ['comments', songId, partId],
    queryFn: () => getComments(songId, partId),
  });
