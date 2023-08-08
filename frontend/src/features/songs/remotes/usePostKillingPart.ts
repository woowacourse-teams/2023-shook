import { postKillingPart } from '@/features/songs/remotes/killingPart';
import { useMutation } from '@/shared/hooks/useMutation';

export const usePostKillingPart = () => {
  const {
    data: killingPartPostResponse,
    isLoading,
    error,
    mutateData: createKillingPart,
  } = useMutation(postKillingPart);

  return { killingPartPostResponse, isLoading, error, createKillingPart };
};
