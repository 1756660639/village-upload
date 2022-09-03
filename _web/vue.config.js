'use strict'
const path = require('path')
const webpack = require('webpack')

function resolve(dir) {
  return path.join(__dirname, dir)
}
// const UglifyJsPlugin = require('uglifyjs-webpack-plugin')
module.exports = {
  /* 部署生产环境和开发环境下的URL：可对当前环境进行区分，baseUrl 从 Vue CLI 3.3 起已弃用，要使用publicPath */
  publicPath: process.env.NODE_ENV === 'production' ? './' : './',
  /* 输出文件目录：在npm run build时，生成文件的目录名称 */
  outputDir: 'dists',
  /* 放置生成的静态资源 (js、css、img、fonts) 的 (相对于 outputDir 的) 目录 */
  assetsDir: 'assets',
  /* 是否在构建生产包时生成 sourceMap 文件，false将提高构建速度 */
  productionSourceMap: false,
  /* 默认情况下，生成的静态资源在它们的文件名中包含了 hash 以便更好的控制缓存，你可以通过将这个选项设为 false 来关闭文件名哈希。(false的时候就是让原来的文件名不改变) */
  filenameHashing: false,
  /* 代码保存时进行eslint检测 */
  lintOnSave: true,
  /* webpack-dev-server 相关配置 */
  devServer: {
    /* 自动打开浏览器 */
    open: true,
    proxy: {
      '/api': {
        target: process.env.VUE_APP_URL,
        ws: true, // 如果要代理 websockets，配置这个参数
        // secure: false,  // 如果是https接口，需要配置这个参数
        changeOrigin: true, // 是否跨域
        pathRewrite: {
          '^/api': '/'
        }
      }
    }
  },
  configureWebpack: {
    resolve: {
      alias: {
        '@': resolve('src'),
        '#': resolve('node_modules')
      }
    },
    externals: {
      BMap: 'BMap'
      // BMapLib: 'BMapLib',
      // BMAP_DRAWING_MARKER: 'BMAP_DRAWING_MARKER',
      // BMAP_DRAWING_POLYLINE: 'BMAP_DRAWING_POLYLINE',
      // BMAP_DRAWING_CIRCLE: 'BMAP_DRAWING_CIRCLE',
      // BMAP_DRAWING_RECTANGLE: 'BMAP_DRAWING_RECTANGLE',
      // BMAP_DRAWING_POLYGON: 'BMAP_DRAWING_POLYGON'
    },
    plugins: [
      new webpack.ProvidePlugin({
        $: 'jquery',
        jQuery: 'jquery',
        'windows.jQuery': 'jquery'
      })
      // new UglifyJsPlugin({
      //   uglifyOptions: {
      //     compress: {
      //       // warnings: false,
      //       drop_console: true, // console
      //       drop_debugger: true,
      //       pure_funcs: ['console.log', 'console.info'] // 移除console
      //     }
      //   }
      // })
    ]
  },

  chainWebpack: config => {
    config
      .plugin('html')
      .tap(args => {
        args[0].title = 'village文件系统'
        return args
      })
  }
}
