import fetcher from '@/shared/remotes';

export const getLikeParts = () => fetcher('/my-page/like-parts', 'GET');

export const getMyParts = () => fetcher('/my-page/member-parts', 'GET');
