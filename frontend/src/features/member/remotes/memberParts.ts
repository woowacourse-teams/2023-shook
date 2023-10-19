import fetcher from '@/shared/remotes';

export const deleteMemberParts = (partId: number) => fetcher(`/member-parts/${partId}`, 'DELETE');
