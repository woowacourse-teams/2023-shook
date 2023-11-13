import client from '@/shared/remotes/axios';

export const deleteMemberParts = async (partId: number) => {
  await client.delete(`/member-parts/${partId}`);
};
