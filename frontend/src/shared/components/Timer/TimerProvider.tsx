import { createContext, useCallback, useEffect, useRef, useState } from 'react';
import type { PropsWithChildren } from 'react';

interface TimerContextProps {
  countTime: number;
  startTimer: () => void;
  togglePauseAndResume: () => void;
  toggleAutoRestart: () => void;
}

export const TimerContext = createContext<TimerContextProps | null>(null);

interface TimerProviderProps {
  time: number;
}

export const TimerProvider = ({ children, time }: PropsWithChildren<TimerProviderProps>) => {
  const [initialTime, setInitialTime] = useState(time);
  const [countTime, setCountTime] = useState(0);
  const [isStart, setIsStart] = useState(false);
  const [isPause, setIsPause] = useState(false);
  const [autoRestart, setAutoRestart] = useState(false);

  const intervalRef = useRef<number | null>(null);

  const count = useCallback(() => {
    setCountTime((prev) => prev + 0.1);
  }, []);

  const updateIntervalRef = useCallback(() => {
    intervalRef.current = window.setInterval(count, 100);
  }, [count]);

  const clearIntervalRef = () => {
    if (intervalRef.current === null) return;

    window.clearInterval(intervalRef.current);
    intervalRef.current = null;
  };

  const resetTimer = useCallback(() => {
    clearIntervalRef();
    setCountTime(0);
    setIsPause(false);
    setIsStart(false);
  }, []);

  const startTimer = useCallback(() => {
    if (isStart) return;

    resetTimer();
    setIsStart(true);
    updateIntervalRef();
  }, [isStart, resetTimer, updateIntervalRef]);

  const togglePauseAndResume = () => {
    if (countTime !== 0) {
      if (isPause) {
        updateIntervalRef();
        setIsPause(false);
      } else {
        clearIntervalRef();
        setIsPause(true);
      }
    }
  };

  const toggleAutoRestart = () => setAutoRestart((prev) => !prev);

  useEffect(() => {
    if (countTime <= initialTime) return;
    clearIntervalRef();
    setIsStart(false);

    return () => clearIntervalRef();
  }, [countTime, initialTime, resetTimer]);

  useEffect(() => {
    if (isStart || !autoRestart) return;
    startTimer();

    return () => clearIntervalRef();
  }, [isStart, autoRestart, startTimer]);

  return (
    <TimerContext.Provider
      value={{ countTime, startTimer, toggleAutoRestart, togglePauseAndResume }}
    >
      {children}
    </TimerContext.Provider>
  );
};

export default TimerProvider;
