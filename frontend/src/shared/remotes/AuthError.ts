class AuthError extends Error {
  code: number;
  name: string;
  constructor({ code, message }: { code: number; message: string }) {
    super(message);
    this.code = code;
    this.name = 'AuthError';
  }
}

export default AuthError;
