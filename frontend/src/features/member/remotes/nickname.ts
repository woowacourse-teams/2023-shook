import fetcher from '@/shared/remotes';

export const updateNickname =
  (memberId: number | undefined, nickname: string | undefined) => () => {
    return fetcher(`/members/${memberId}/nickname`, 'PATCH', {
      nickname,
    });
  };
