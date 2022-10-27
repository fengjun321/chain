const routes = [{
    path: '/',
    name: 'index',
    component: () => import('@/views/index.vue'),
    redirect: '/market',
    children: [{
        path: '/market',
        name: 'market',
        component: () => import('@/views/market/mine.vue'),
      },
      {
        path: '/storehouse',
        name: 'storehouse',
        component: () => import('@/views/storehouse/index.vue')
      },
      {
        path: '/count',
        name: 'count',
        component: () => import('@/views/count/index.vue')
      },
      {
        path: '/token',
        name: 'token',
        component: () => import('@/views/storehouse/buy/buy.vue'),
        props: route => ({
          query: route.query.id
        })
      }
    ]
  },



]
export default routes
