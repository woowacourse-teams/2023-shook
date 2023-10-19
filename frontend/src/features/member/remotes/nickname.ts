import fetcher from '@/shared/remotes';

export const updateNickname = (memberId: number, nickname: string) => {
  return fetcher(`/members/${memberId}/nickname`, 'PATCH', {
    nickname,
  });
};
