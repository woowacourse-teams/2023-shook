const { merge } = require('webpack-merge');
const common = require('./webpack.common');
const ReactRefreshWebpackPlugin = require('@pmmmwh/react-refresh-webpack-plugin');

module.exports = merge(common, {
  mode: 'development',
  devServer: {
    historyApiFallback: true,
    open: true,
  },

  plugins: [new ReactRefreshWebpackPlugin()],
});
