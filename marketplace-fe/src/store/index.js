// import Vue from 'vue';
// import Vuex from 'vuex';
import persistedstate from 'vuex-persistedstate';
// console.log('persistedstate',persistedstate);

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    isWallet: false,
    isPc: true,
    address: '',
    orderFiled: 0,
    orderType: 0,
    searchValue: '',
    filter: []
  },
  mutations: {
    updateWallet(state, isConnection) {
      state.isWallet = isConnection;
    },
    updateAddress(state, address) {
      state.address = address;
    },
    updateOrderFiled(state, value) {
      state.orderFiled = value;
    },
    updateOrderType(state, value) {
      state.orderType = value;
    },
    updateSearch(state, value) {
      state.searchValue = value;
    },
    updateIsPc(state, value) {
      state.isPc = value;
    },
    updateFilter({ filter }, payload = {}) {
      // console.log('updateFilter', payload);
      if (payload.name) {
        const targetIndex = filter.findIndex(item => item.name === payload.name)
        if (targetIndex > -1) {
          filter.splice(targetIndex, 1, payload)
        } else {
          filter.push(payload)
        }
      }
    },
    removeFilter({ filter }, payload = {}) {
      if (payload.name) {
        const targetIndex = filter.findIndex(item => item.name === payload.name)
        if (targetIndex > -1) {
          filter.splice(targetIndex, 1)
        }
      }
    },
    clearFilter(state) {
      // console.log('clearFilter', state.filter);
      state.filter = [];
    },
  },
  actions: {},
  modules: {},
  plugins: [
    persistedstate({
      storage: window.sessionStorage,
    }),
  ],
});
