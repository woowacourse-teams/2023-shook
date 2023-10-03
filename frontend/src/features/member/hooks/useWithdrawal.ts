import { useNavigate } from 'react-router-dom';
import { useAuthContext } from '@/features/auth/components/AuthProvider';
import { deleteMember } from '@/features/member/remotes/member';
import ROUTE_PATH from '@/shared/constants/path';
import { useMutation } from '@/shared/hooks/useMutation';

const useWithdrawal = () => {
  const navigate = useNavigate();
  const { user, logout } = useAuthContext();
  const { mutateData: withdrawMember } = useMutation(deleteMember(user?.memberId));

  const handleWithdrawal = async () => {
    await withdrawMember();
    logout();
    navigate(ROUTE_PATH.ROOT);
  };

  return {
    handleWithdrawal,
  };
};

export default useWithdrawal;
