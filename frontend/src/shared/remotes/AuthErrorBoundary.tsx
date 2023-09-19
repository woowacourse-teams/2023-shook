import { Component } from 'react';
import LoginPopUp from '@/features/auth/components/LoginPopUp';
import type { ErrorInfo, ReactNode } from 'react';

interface Props {
  children?: ReactNode;
}

interface State {
  hasError: boolean;
}

class AuthErrorBoundary extends Component<Props, State> {
  public state: State = {
    hasError: false,
  };

  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  public static getDerivedStateFromError(_: Error): State {
    console.log('derivedCatched!');
    return { hasError: true };
  }

  public componentDidCatch(error: Error, errorInfo: ErrorInfo) {
    console.error('componentDidCatched!', error, errorInfo);
  }

  public render() {
    if (this.state.hasError) {
      return <LoginPopUp />;
    }

    return this.props.children;
  }
}

export default AuthErrorBoundary;
