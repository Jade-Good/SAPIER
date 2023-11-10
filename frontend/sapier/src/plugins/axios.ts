import axios from 'axios'

const instance = axios.create({
  baseURL: `${import.meta.env.VITE_SERVER_URL}`,
  headers: {
    'Content-Type': 'application/json',
  },
  withCredentials: true,
})

instance.interceptors.request.use(
  (config) => {
    // console.log('axios.js request : ', config)
    return config
  },
  (error) => {
    return Promise.reject(error)
  },
)
instance.interceptors.response.use(
  (res) => {
    // console.log('axios.js response : ', res)
    return res
  },
  (error) => {
    if (error.response && error.response.status === 401) {
      // 오류 응답이 401일 경우 로그인 페이지로 리다이렉트
      window.location.href = '/login/oauth'
    }

    return Promise.reject(error)
  },
)
export default instance
