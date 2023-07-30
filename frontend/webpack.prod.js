const path = require('path');
const { merge } = require('webpack-merge');
const common = require('./webpack.common');
const webpack = require('webpack');
const dotenv = require('dotenv');

dotenv.config({ path: '.env/.env.production' });

module.exports = merge(common, {
  mode: 'production',
  output: {
    filename: 'static/[contenthash].js',
    path: path.resolve(__dirname, 'dist'),
    clean: true,
  },
  module: {
    rules: [
      {
        test: /\.tsx?$/,
        exclude: /node_modules/,
        use: ['babel-loader', 'ts-loader'],
      },
    ],
  },
  plugins: [
    new webpack.DefinePlugin({
      'process.env': JSON.stringify(process.env),
    }),
  ],
});
