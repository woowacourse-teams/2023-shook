module.exports = {
  presets: [
    [
      '@babel/preset-env',
      {
        targets: 'last 2 years, not dead',
        useBuiltIns: 'entry',
        corejs: '3.31.1',
      },
    ],
    [
      '@babel/preset-react',
      {
        runtime: 'automatic',
      },
    ],
    ['@babel/preset-typescript'],
  ],
};
