const path = require("path");
const resolve = dir => {
    return path.join(__dirname, dir);
};
const CompressionPlugin = require('compression-webpack-plugin')
const BundleAnalyzerPlugin = require('webpack-bundle-analyzer').BundleAnalyzerPlugin;

module.exports = {

    publicPath: "./",
    productionSourceMap: false,
    configureWebpack: {
        externals:{
            'Vue': 'Vue',
            'Vuex': 'vuex',
            'VueClipboard': 'VueClipboard',
            'VueRouter': 'VueRouter',
            'element-ui':'ELEMENT',
            'Web3': 'Web3',
        },
        resolve: {
            alias: {
                assets: "@/assets",
                common: "@/common",
                components: "@/components",
                views: "@/views",
                api: '@/api',
                utils: '@utils'
            },
        },
        plugins: [
            new CompressionPlugin({
                test: /\.(js|css|html)$/,
                threshold: 10240,
            }),
            // new BundleAnalyzerPlugin()
        ]
    },
    //npm install style-resources-loader vue-cli-plugin-style-resources-loader --save-dev
    pluginOptions: {
        "style-resources-loader": {
            preProcessor: "scss",
            patterns: [
                path.resolve(__dirname, "src/styles/mixins.scss"),
                // path.resolve(__dirname, "src/styles/mixins.less"),
            ],
        },
    },
    //npm install postcss postcss-pxtorem --save-dev
    //npm install px2rem-loader
    //npm install lib-flexible
    css: {
        extract: true,
        sourceMap: false,
        loaderOptions: {
            sass: {
                javascriptEnabled: true,
            },
            postcss: {
                plugins: [
                    require("postcss-pxtorem")({
                        rootValue: 37.5,
                        propList: ["*"],
                    }),
                ],
            },
        },
    },
};
