import { client } from '@/shared/remotes/axios';

export const deleteMember = async (memberId: number) => {
  await client.delete(`/members/${memberId}`);
};
