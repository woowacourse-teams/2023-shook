import { createContext, useCallback, useEffect, useRef, useState } from 'react';
import type { PropsWithChildren } from 'react';

interface TimerContextProps {
  countedTime: number;
  startTimer: () => void;
  resetTimer: () => void;
  pauseTimer: () => void;
  toggleAutoRestart: () => void;
}

export const TimerContext = createContext<TimerContextProps | null>(null);

interface TimerProviderProps {
  time: number;
}

export const TimerProvider = ({ children, time }: PropsWithChildren<TimerProviderProps>) => {
  const [countedTime, setCountedTime] = useState(0);
  const [isStart, setIsStart] = useState(false);
  const [autoRestart, setAutoRestart] = useState(false);

  const initialTimeRef = useRef(time);
  const intervalRef = useRef<number | null>(null);

  const updateIntervalRef = () => {
    if (intervalRef.current) return;
    intervalRef.current = window.setInterval(() => setCountedTime((prev) => prev + 0.1), 100);
  };

  const clearIntervalRef = () => {
    if (intervalRef.current === null) return;

    window.clearInterval(intervalRef.current);
    intervalRef.current = null;
  };

  const resetTimer = useCallback(() => {
    setCountedTime(0);
    setIsStart(false);
    clearIntervalRef();
  }, []);

  const startTimer = useCallback(() => {
    setIsStart(true);
    updateIntervalRef();
  }, []);

  const pauseTimer = useCallback(() => {
    clearIntervalRef();
  }, []);

  const toggleAutoRestart = () => setAutoRestart((prev) => !prev);

  // 타이머 리셋 Effect
  useEffect(() => {
    if (countedTime < initialTimeRef.current) return;
    resetTimer();

    return () => clearIntervalRef();
  }, [countedTime, resetTimer]);

  // 타이머 자동 재시작 Effect
  useEffect(() => {
    if (isStart || !autoRestart) return;
    startTimer();

    return () => clearIntervalRef();
  }, [isStart, autoRestart, startTimer]);

  return (
    <TimerContext.Provider
      value={{
        countedTime,
        startTimer,
        resetTimer,
        pauseTimer,
        toggleAutoRestart,
      }}
    >
      {children}
    </TimerContext.Provider>
  );
};

export default TimerProvider;
