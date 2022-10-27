// import Vue from 'vue';
import App from './App.vue';
import router from './router';
import store from './store';
// import ElementUI from 'element-ui';
// import 'element-ui/lib/theme-chalk/index.css';
// import VueClipboard from 'vue-clipboard2';
// import Web3 from 'web3';
import erc20 from './config/contract/erc20.json';
import erc721 from './config/contract/erc721.json';
import erc1155 from './config/contract/erc1155.json';
import IsTarship from './config/contract/IStarShip.json';
// import './mock'; // Mock

//初始化样式normalize 
// import 'normalize.css';

// 适配flex
import './common/lib-flexible/flexible';

//全局样式
import './styles/mixins.scss';

//防止2次点击路由
// import Router from 'vue-router';

const originalPush = VueRouter.prototype.replace;
VueRouter.prototype.replace = function replace(location) {
  return originalPush.call(this, location).catch(err => err);
};

// console.log(IsTarship);

if (window.web3 !== undefined) {
  let _web3 = new Web3(window.web3.currentProvider);
  // 货币合约
  Vue.prototype.$erc20 = new _web3.eth.Contract(
    erc20,
    process.env.VUE_APP_ERC20
  );
  // 商品合约
  Vue.prototype.$erc721 = new _web3.eth.Contract(
    erc721,
    process.env.VUE_APP_ERC721
  );
  Vue.prototype.$erc1155 = new _web3.eth.Contract(
    erc1155,
    process.env.VUE_APP_ERC1155
  );
  Vue.prototype.$erc721HSE = new _web3.eth.Contract(
    erc721,
    process.env.VUE_APP_ERC721_HSE
  );
  Vue.prototype.$erc721PSE = new _web3.eth.Contract(
    erc721,
    process.env.VUE_APP_ERC721_PSE
  );
  Vue.prototype.$erc721EGSE = new _web3.eth.Contract(
    erc721,
    process.env.VUE_APP_ERC721_EGSE
  );
  Vue.prototype.$erc721SHIP = new _web3.eth.Contract(
    erc721,
    process.env.VUE_APP_ERC721_ISTARSHIP
  );
  Vue.prototype.$IsTarship = new _web3.eth.Contract(
    IsTarship.abi,
    process.env.VUE_APP_ERC721_ISTARSHIP
  );
  Vue.prototype.$erc721MBSE = new _web3.eth.Contract(
    erc721,
    process.env.VUE_APP_ERC721_MBSE
  );
  Vue.prototype.$erc721EGG = new _web3.eth.Contract(
    erc721,
    process.env.VUE_APP_ERC721_EGG
  );
  Vue.prototype.$erc721ASE = new _web3.eth.Contract(
    erc721,
    process.env.VUE_APP_ERC721_ASE
  );
  Vue.prototype.$web3 = _web3;
}

console.log('VUE_APP_ERC20', process.env.VUE_APP_ERC20);

Vue.use(ELEMENT);
Vue.use(VueClipboard);
Vue.config.productionTip = false;

store.commit('updateIsPc', window.innerWidth > 767);

// 事件总线
Vue.prototype.$EventBus = new Vue()

new Vue({
  router,
  store,
  render: h => h(App),
}).$mount('#app');
