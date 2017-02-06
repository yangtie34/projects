var path = require("path");

module.exports = {
    context: __dirname,
    //devtool: 'eval',//配置生成Source Maps,用于调试编译后的代码，该项在需要在发布的时候注释掉
    //插件项
    plugins: require("./webpack-config/webpack-plugins-config"),
    //页面入口文件配置
    entry: require("./webpack-config/webpack-entry-config"),
    //入口文件输出配置
    output: {
        publicPath: "../../public/",
        path:  "./public",
        filename: '[name]',
        sourceMapFilename:"[file].map"
    },

    //观察者模式开启，自动编译
    // watch : true,
    // watchOptions: { aggregateTimeout: 300, poll: 100 },

    module: require("./webpack-config/webpack-module-config")
};