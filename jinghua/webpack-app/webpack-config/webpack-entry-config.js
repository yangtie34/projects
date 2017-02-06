/**
 * Created by Administrator on 2017-1-17.
 */
var path = require("path");
var entry = {
    "common/common": [
        'jquery',
        'angular',
        'toastr',
        'bootstrap-loader',
        './node_modules/toastr/build/toastr.css',
        "./static/font-awesome-4.5.0/css/font-awesome.min.css"
    ],
    "common/system.js":'./src/common/system.js',
}

//引入源文件目录配置文件
var sources = require("./webpack-source-config")
sources.forEach(function(it){
    entry[it] = "./src/" + it
});

module.exports = entry;