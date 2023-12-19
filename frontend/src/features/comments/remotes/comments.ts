import { client } from '@/shared/remotes/axios';
import type { Comment } from '../types/comment.type';

export const postComment = async (songId: number, partId: number, content: string) => {
  await client.post(`/songs/${songId}/parts/${partId}/comments`, { content });
};

export const getComments = async (songId: number, partId: number) => {
  const { data } = await client.get<Comment[]>(`/songs/${songId}/parts/${partId}/comments`);

  return data;
};
