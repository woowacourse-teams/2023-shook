module.exports = {
  presets: [
    [
      '@babel/preset-env',
      {
        targets: {
          // TODO: 브라우저 지원 범위
          chrome: '51',
        },
        useBuiltIns: 'entry',
        corejs: '3.31.1',
      },
    ],
    ['@babel/preset-react'],
  ],
  plugins: ['react-refresh/babel'],
};
