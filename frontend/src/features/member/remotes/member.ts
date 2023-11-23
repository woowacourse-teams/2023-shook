import fetcher from '@/shared/remotes';

export const deleteMember = (memberId: number) => {
  return fetcher(`/members/${memberId}`, 'DELETE');
};
