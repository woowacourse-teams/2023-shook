import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuthContext } from '@/features/auth/components/AuthProvider';
import { MAX_LENGTH_NICKNAME, MIN_LENGTH_NICKNAME } from '@/features/member/constants/nickname';
import { updateNickname } from '@/features/member/remotes/nickname';
import ROUTE_PATH from '@/shared/constants/path';
import { useMutation } from '@/shared/hooks/useMutation';

const useNickname = () => {
  const { user, logout } = useAuthContext();
  // TODO: 피드백 반영하여 error throw. 그러나 error 핸들링 반드시 필요
  if (!user) {
    throw new Error('현재 user 로그인된 정보가 없습니다.');
  }

  const [nicknameEntered, setNicknameEntered] = useState(user.nickname);

  const [nicknameErrorMessage, setNicknameErrorMessage] =
    useState('이전과 다른 닉네임으로 변경해주세요.');

  const { mutateData: changeNickname } = useMutation(() =>
    updateNickname(user.memberId, nicknameEntered)
  );

  const navigate = useNavigate();

  const hasError = nicknameErrorMessage.length !== 0;
  const handleChangeNickname: React.ChangeEventHandler<HTMLInputElement> = (event) => {
    const currentNickname = event.currentTarget.value;

    if (currentNickname.length > MAX_LENGTH_NICKNAME) {
      setNicknameErrorMessage('2글자 이상 20글자 이하 문자만 가능합니다.');
      return;
    } else if (currentNickname.length < MIN_LENGTH_NICKNAME) {
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
