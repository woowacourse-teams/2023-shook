import { postKillingPart } from '@/features/songs/remotes/killingPart';
import { useMutation } from '@/shared/hooks/useMutation';

export const usePostKillingPart = () => {
  const { isLoading, error, mutateData: createKillingPart } = useMutation(postKillingPart);

  return { isLoading, error, createKillingPart };
};
