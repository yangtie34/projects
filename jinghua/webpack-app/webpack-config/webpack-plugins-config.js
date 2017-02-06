/**
 * Created by Administrator on 2017-1-17.
 */
var webpack = require('webpack');
//var ExtractTextPlugin = require("extract-text-webpack-plugin"); //提取css
//var CopyWebpackPlugin = require("copy-webpack-plugin") //文件拷贝插件
module.exports = [
    new webpack.optimize.UglifyJsPlugin({ mangle: true, sourcemap: false }),//压缩工具，可以减少网络流量
    new webpack.ProvidePlugin({
        $: "jquery",
        jQuery: "jquery",
        "window.jQuery": "jquery"
    }),
    new webpack.ProvidePlugin({
        toastr: "toastr"
    }),
    new webpack.optimize.CommonsChunkPlugin('common/common','common/common.js'),

    /*new ExtractTextPlugin("styles.css"),*/ //提取样式文件

    /*new CopyWebpackPlugin([{
     from: __dirname + '/app',
     ignore : "*.js"
     }]),*/ //文件拷贝插件，用来拷贝文件到编译目录
]