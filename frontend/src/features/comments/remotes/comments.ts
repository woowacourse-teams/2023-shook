import fetcher from '@/shared/remotes';
import type { Comment } from '../types/comment.type';

export const postComment = async (songId: number, partId: number, content: string) => {
  await fetcher(`/songs/${songId}/parts/${partId}/comments`, 'POST', { content });
};

export const getComments = async (songId: number, partId: number): Promise<Comment[]> => {
  return await fetcher(`/songs/${songId}/parts/${partId}/comments`, 'GET');
};
