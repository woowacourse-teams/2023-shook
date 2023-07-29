const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const webpack = require('webpack');
const dotenv = require('dotenv');

let envPath;
if (process.NODE_ENV === 'production') {
  envPath = '.env/.env.production';
} else {
  envPath = '.env/.env.development';
}

dotenv.config({ path: envPath });

module.exports = {
  entry: './src/index.tsx',
  output: {
    assetModuleFilename: 'assets/[hash][ext]',
    publicPath: '/',
  },
  module: {
    rules: [
      {
        test: /\.(png|svg|jpe?g)$/,
        type: 'asset/resource',
      },
    ],
  },
  plugins: [
    new HtmlWebpackPlugin({
      template: './public/index.html',
    }),
    new webpack.DefinePlugin({
      'process.env': JSON.stringify(process.env),
    }),
  ],
  resolve: {
    extensions: ['.tsx', '.ts', '.jsx', '.js'],
    alias: {
      '@': path.resolve(__dirname, 'src/'),
    },
  },
};
