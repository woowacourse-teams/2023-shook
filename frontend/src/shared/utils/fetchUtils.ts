export const isExpiredAfter60seconds = (tokenExp: number) => {
  return tokenExp * 1000 - 30 * 1000 < Date.now();
};
