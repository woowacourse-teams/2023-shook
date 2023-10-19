import { useNavigate } from 'react-router-dom';
import { useAuthContext } from '@/features/auth/components/AuthProvider';
import { deleteMember } from '@/features/member/remotes/member';
import ROUTE_PATH from '@/shared/constants/path';
import { useMutation } from '@/shared/hooks/useMutation';

const useWithdrawal = () => {
  const navigate = useNavigate();
  const { user, logout } = useAuthContext();
  // TODO: 피드백 반영하여 error throw. 그러나 error 핸들링 반드시 필요
  if (!user) {
    throw new Error('현재 user 로그인된 정보가 없습니다.');
  }

  const { mutateData: withdrawMember } = useMutation(() => deleteMember(user.memberId));

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
