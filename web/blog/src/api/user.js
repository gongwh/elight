/**
 * Created by SNOW on 2018.07.19
 */
import http from '@/api/util/http'

const registration = (user) => http.post('/user/registration', user)

const profile = () => http.get('/user/profile')

export default {
  registration,
  profile
}
