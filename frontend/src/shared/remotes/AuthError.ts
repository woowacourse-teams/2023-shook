class AuthError extends Error {
  constructor(message: string = '') {
    console.log('new error created!');
    super(message);
    this.name = 'AuthError';
  }
}

export default AuthError;
