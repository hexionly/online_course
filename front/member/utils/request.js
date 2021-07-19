import axios from "axios"
import { MessageBox, Message } from 'element-ui'
import cookie from 'js-cookie';

//创建axios实例
const service= axios.create({
    baseURL:"http://localhost:9001",
    timeout:20000 //请求超时
})

//拦截器
service.interceptors.request.use(config=>{
        if(cookie.get("guli_token")){
            config.headers['token']=cookie.get("guli_token")
        }
        return config
    },
    error=>{
        return Promise.reject(error)
    }
)

export default service