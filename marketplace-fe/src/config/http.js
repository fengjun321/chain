// import axios from "axios"

axios.defaults.baseURL = process.env.VUE_APP_API_URL
axios.defaults.timeout = 15000
axios.defaults.headers.post['Content-Type'] = 'application/json'

axios.interceptors.request.use(config => {
    if (config.method === 'post') {
        config.params = JSON.stringify(config.params)
    }
    if (config.method === 'get') {
        if (config.params === undefined) {
            config.params = {
                v: (new Date()).valueOf()
            }
        } else {
            config.params.v = (new Date()).valueOf()
        }
    }
    return config
}, (error) => {
    return Promise.reject(error)
})

export default axios
