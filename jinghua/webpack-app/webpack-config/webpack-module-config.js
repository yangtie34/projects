/**
 * Created by Administrator on 2017-1-17.
 */
module.exports = {
    //加载器配置
    loaders: [
        {test: /\.css$/,loader: 'style-loader!css-loader' },//loader:ExtractTextPlugin.extract("style-loader","css-loader")
        {test: /\.(png|jpg|jpeg|gif)$/,loader: 'file?name=img/[name].[ext]?[hash]'},
        {test: /\.scss$/, loader: 'style!css!sass?sourceMap'},
        {test: /\.js$/,loader: 'babel-loader!jsx-loader?harmony'},
        {
            // 专供iconfont方案使用的，后面会带一串时间戳，需要特别匹配到
            test: /\.(woff|woff2|svg|eot|ttf)\??.*$/,
            loader: 'file?name=common/fonts/[name].[ext]',
        },
        {
            test: /\.html$/,
            loader: 'html',
        },
    ]
};