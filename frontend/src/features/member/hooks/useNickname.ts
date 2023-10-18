import { useMemo, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuthContext } from '@/features/auth/components/AuthProvider';
import { updateNickname } from '@/features/member/remotes/nickname';
import ROUTE_PATH from '@/shared/constants/path';
import { useMutation } from '@/shared/hooks/useMutation';

const useNickname = () => {
  const { user, logout } = useAuthContext();

  const [nicknameEntered, setNicknameEntered] = useState(user?.nickname);
  const [nicknameErrorMessage, setNicknameErrorMessage] =
    useState('이전과 다른 닉네임으로 변경해주세요.');
  const mutateNickname = useMemo(
    () => updateNickname(user?.memberId, nicknameEntered),
    [nicknameEntered, user?.memberId]
  );
  const { mutateData: changeNickname } = useMutation(mutateNickname);
  const navigate = useNavigate();

  const hasError = nicknameErrorMessage.length !== 0;
  const handleChangeNickname: React.ChangeEventHandler<HTMLInputElement> = (event) => {
    const currentNickname = event.currentTarget.value;

    if (currentNickname.length > 20) {
      setNicknameErrorMessage('2글자 이상 20글자 이하 문자만 가능합니다.');
      return;
    } else if (currentNickname.length < 2) {
      setNicknameErrorMessage('2글자 이상 20글자 이하 문자만 가능합니다.');
    } else if (currentNickname === user?.nickname) {
      setNicknameErrorMessage('이전과 다른 닉네임으로 변경해주세요.');
    } else {
      setNicknameErrorMessage('');
    }

    setNicknameEntered(currentNickname);
  };

  const submitNicknameChanged = async () => {
    await changeNickname();
    logout();
    navigate(ROUTE_PATH.LOGIN);
  };

  return {
    nicknameEntered,
    nicknameErrorMessage,
    hasError,
    handleChangeNickname,
    submitNicknameChanged,
    setNicknameErrorMessage,
  };
};

export default useNickname;
