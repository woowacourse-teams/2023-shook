import { postKillingPart } from '@/apis/killingPart';
import { useMutation } from './@common/useMutation';

export const usePostKillingPart = () => {
  const {
    data: killingPartPostResponse,
    isLoading,
    error,
    mutateData: createKillingPart,
  } = useMutation(postKillingPart);

  return { killingPartPostResponse, isLoading, error, createKillingPart };
};
