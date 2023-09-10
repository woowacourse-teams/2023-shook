const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const CopyWebpackPlugin = require('copy-webpack-plugin');

module.exports = {
  entry: './src/index.tsx',
  output: {
    assetModuleFilename: 'assets/[hash][ext]',
    publicPath: '/',
  },
  module: {
    rules: [
      {
        test: /\.(png|jpe?g)$/,
        type: 'asset/resource',
        generator: {
          filename: 'static/image/[contenthash][ext]',
        },
      },
      {
        test: /\.(svg)$/i,
        type: 'asset/resource',
        generator: {
          filename: 'static/icon/[contenthash][ext]',
        },
      },
      {
        test: /\.(woff|woff2|eot|ttf|otf)$/i,
        type: 'asset/resource',
        generator: {
          filename: 'static/font/[contenthash][ext]',
        },
      },
    ],
  },
  plugins: [
    new HtmlWebpackPlugin({
      template: './public/index.html',
    }),
    new CopyWebpackPlugin({
      patterns: [{ from: './public/assets/og/og-image.jpg', to: 'static/og/og-image.jpg' }],
    }),
  ],
  resolve: {
    extensions: ['.tsx', '.ts', '.jsx', '.js'],
    alias: {
      '@': path.resolve(__dirname, 'src/'),
    },
  },
};
