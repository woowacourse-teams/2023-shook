import fetcher from '@/shared/remotes';

export const deleteMember = (memberId: number | undefined) => () => {
  return fetcher(`/members/${memberId}`, 'DELETE');
};
