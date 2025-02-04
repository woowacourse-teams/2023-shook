import { queryOptions } from '@tanstack/react-query';
import { getComments } from '../remotes/comments';

export const commentsQueries = {
  all: ['comments'],
  list: ({ songId, partId }: { songId: number; partId: number }) =>
    queryOptions({
      queryKey: [...commentsQueries.all, songId, partId],
      queryFn: () => getComments(songId, partId),
    }),
};
