/**
 * Created by SNOW on 2018.07.19
 */
import http from '@/api/util/http'

const login = (username, password) => http.post('/auth/login', username, password)

const logout = () => http.post('/auth/logout')

export default {
  login,
  logout
}
