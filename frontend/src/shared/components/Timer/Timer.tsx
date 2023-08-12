import { useCallback, useEffect, useRef, useState } from 'react';

const Timer = () => {
  const [initialTime, setInitialTime] = useState(10);
  const [countTime, setCountTime] = useState(0);
  const [isStart, setIsStart] = useState(false);
  const [isPause, setIsPause] = useState(false);

  const intervalRef = useRef<number | null>(null);

  const count = useCallback(() => {
    setCountTime((prev) => prev + 0.1);
  }, []);

  const setIntervalRef = useCallback(() => {
    intervalRef.current = window.setInterval(count, 100);
  }, [count]);

  const resetCount = useCallback(() => {
    clearIntervalRef();
    setCountTime(0);
    setIsPause(false);
    setIsStart(false);
  }, []);

  const startCount = useCallback(() => {
    setIsStart(true);
    setIntervalRef();
  }, [setIntervalRef]);

  const startTimer = useCallback(() => {
    if (isStart) return;

    resetCount();
    startCount();
  }, [isStart, resetCount, startCount]);

  const togglePauseAndResume = () => {
    if (countTime !== 0) {
      if (!isPause) {
        clearIntervalRef();
        setIsPause(true);
      } else {
        startCount();
        setIsPause(false);
      }
    }
  };

  const clearIntervalRef = () => {
    if (intervalRef.current === null) return;

    window.clearInterval(intervalRef.current);
    intervalRef.current = null;
  };

  useEffect(() => {
    if (countTime < initialTime) return;
    resetCount();

    return () => clearIntervalRef();
  }, [countTime, initialTime, resetCount]);

  return (
    <>
      <div>카운트 시간: {countTime}</div>
      <div>{`시작: ${isStart}`}</div>
      <div>{`멈춤: ${isPause}`}</div>
      <div>{countTime}</div>
      <button style={{ border: '1px solid black' }} onClick={startTimer}>
        시작
      </button>
      <button style={{ border: '1px solid black' }} onClick={togglePauseAndResume}>
        일시정지/시작
      </button>
    </>
  );
};

export default Timer;

// 밖에서 원하는 시간 지정 가능해야함
// 타이머 실행 가능해야함
// 중간에 pause 할 수 있어야함.
// 중간에 stop 할 수 있어야함.
// pause에서 다시 실행 가능해야함.
