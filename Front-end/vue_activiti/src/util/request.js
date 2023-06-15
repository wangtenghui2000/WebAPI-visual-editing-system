/*
    请求app后端请求类
*/

import axios from 'axios';
import Qs from 'qs'

// 创建axios实例
const service = axios.create({
    timeout: 15000, // 请求超时时间（毫秒）
    headers: {  
        "Content-Type": "application/x-www-form-urlencoded;charset=UTF-8",
     }
})

// service.defaults.withCredentials = true

// request拦截器
service.interceptors.request.use(config => {
    let current = window.current ? JSON.parse(window.current) : {}
    config.baseURL = 'http://127.0.0.1:8084/'; 
    config.data =  Qs.stringify(config.data,  { arrayFormat: 'brackets' })
    return config;
})

service.interceptors.response.use(data => {
    return data;
}, err => {
    if (err.response) {
        const { response: { data, status, statusText } } = err;
        console.error("出错: " + data.exception);
        return Promise.resolve({data, status});
    } else {
        console.error("出错: " + err.message);
        return Promise.resolve({});
    }
    
})
export default service