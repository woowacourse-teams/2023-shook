const parseJWT = (token: string) => {
  const payloadUrl = token.split('.')[1];
  const base64 = payloadUrl.replace(/-/g, '+').replace(/_/g, '/');
  const jsonPayload = decodeURIComponent(
    window
      .atob(base64)
      .split('')
      .map((c) => '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2))
      .join('')
  );

  return JSON.parse(jsonPayload);
};

export default parseJWT;
