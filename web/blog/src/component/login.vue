<template>
  <div class="login_wrapper">
    <div class="login_wrapper_inner">
      <div class="login">
        <div class="login_content">
          <div class="content_header">
            <div class="header_title">Elight</div>
            <div class="header_content">Making Edit and Share lighter</div>
          </div>
          <div class="content_body" :class="`content_body_${isLogin ? 'login':'registration'}`">
            <el-form :rules="loginRules" v-if="isLogin" ref="loginForm" :model="loginForm">
              <table class="login_table">
                <tr class="row">
                  <td>
                    <el-form-item prop="username">
                      <el-input
                        class="element"
                        placeholder="用户名或邮箱"
                        v-model="loginForm.username"
                        clearable>
                      </el-input>
                    </el-form-item>
                  </td>
                </tr>
                <tr class="row">
                  <td>
                    <el-form-item prop="password">
                      <el-input
                        auto-complete="off"
                        type="password"
                        class="element"
                        placeholder="密码"
                        v-model="loginForm.password"
                        clearable>
                      </el-input>
                    </el-form-item>
                  </td>
                </tr>
                <tr class="row">
                  <td>
                    <el-button type="primary" @click="localLogin">Login</el-button>
                  </td>
                </tr>
              </table>
            </el-form>
            <el-form :rules="registerRules" v-if="!isLogin" ref="registerForm" :model="registerForm">
              <table class="login_table">
                <tr class="row">
                  <td>
                    <el-form-item prop="email">
                      <el-input
                        class="element"
                        placeholder="邮箱"
                        v-model="registerForm.email"
                        clearable>
                      </el-input>
                    </el-form-item>
                  </td>
                </tr>
                <tr class="row">
                  <td>
                    <el-form-item prop="username">
                      <el-input
                        class="element"
                        placeholder="用户名"
                        v-model="registerForm.username"
                        clearable>
                      </el-input>
                    </el-form-item>
                  </td>
                </tr>
                <tr class="row">
                  <td>
                    <el-form-item prop="password">
                      <el-input
                        auto-complete="off"
                        type="password"
                        class="element"
                        placeholder="密码"
                        v-model="registerForm.password"
                        clearable>
                      </el-input>
                    </el-form-item>
                  </td>
                </tr>
                <tr class="row">
                  <td>
                    <el-form-item prop="matchingPassword">
                      <el-input
                        auto-complete="off"
                        type="password"
                        class="element"
                        placeholder="确认密码"
                        v-model="registerForm.matchingPassword"
                        clearable>
                      </el-input>
                    </el-form-item>
                  </td>
                </tr>
                <tr class="row">
                  <td>
                    <el-button type="primary" @click="localRegistration">Registration</el-button>
                  </td>
                </tr>
              </table>
            </el-form>
          </div>
          <div class="content_foot">
            <span @click="changeSwitch">{{`${isLogin ? 'Registration':'Login'}`}}</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script type="text/ecmascript-6">
  import {mapActions} from 'vuex'

  export default {
    data () {
      const registerValidatePass2 = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请再次输入密码'))
        } else if (value !== this.registerForm.password) {
          callback(new Error('两次输入密码不一致!'))
        } else {
          callback()
        }
      }
      const usernameValidate = (rule, value, callback) => {
        let reg = /^[a-zA-Z0-9]+([a-zA-Z0-9_\u4e00-\u9fa5])*[a-zA-Z0-9\u4e00-\u9fa5]+$/
        // let reg = /^[a-zA-Z0-9]+^[A-Za-z0-9\u4e00-\u9fa5]+$/
        if (!value || value === '') {
          callback(new Error('请输入用户名'))
        } else if (value.length < 3 || value.length > 20) {
          callback(new Error('长度在3至20个字符'))
        } else if (!reg.test(value)) {
          callback(new Error('请以数字字母开头,不以下划线结尾'))
        } else {
          callback()
        }
      }
      return {
        loginForm: {
          username: '',
          password: ''
        },
        registerForm: {
          email: '',
          username: '',
          password: '',
          matchingPassword: ''
        },
        loginRules: {
          username: [
            {required: true, message: '请输入用户名', trigger: 'blur'}
          ],
          password: [
            {required: true, message: '请输入密码', trigger: 'blur'}
          ]
        },
        registerRules: {
          email: [
            {required: true, message: '请输入邮箱地址', trigger: 'blur'},
            {type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur'}
          ],
          username: [
            // {required: true, message: '请输入用户名', trigger: 'blur'},
            // {min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur'}
            {validator: usernameValidate, trigger: 'blur'}
          ],
          password: [
            {required: true, message: '请输入密码', trigger: 'blur'}
          ],
          matchingPassword: [
            {validator: registerValidatePass2, trigger: 'blur'}
          ]
        },
        isLogin: true // true 登陆状态， false 注册状态
      }
    },
    created () {
      document.addEventListener('keydown', this.e_enter)
    },
    destroyed () {
      document.removeEventListener('keydown', this.e_enter)
    },
    methods: {
      ...mapActions('user', ['registration', 'profile']),
      ...mapActions('auth', ['login', 'logout']),
      changeSwitch () {
        this.l_clearForm()
        // this.isLogin = !this.isLogin
      },
      l_clearForm () {
        this.isLogin = !this.isLogin
        this.$nextTick(function () {
          if (this.isLogin) {
            this.$refs.loginForm.resetFields()
          } else {
            this.$refs.registerForm.resetFields()
          }
        })
      },
      e_enter (e) {
        if (e.keyCode === 13) {
          if (this.isLogin) {
            this.localLogin()
          } else {
            this.localRegistration()
          }
        }
      },
      async localLogin () {
        const that = this
        that.$nextTick(function () {
          that.$refs.loginForm.validate((valid) => {
            if (valid) {
              // 进行登陆
              that.login(this.loginForm).then((loginOk) => {
                // console.log('登陆结果', loginOk)
                if (loginOk) {
                  if (that.$route.query.redirect) {
                    window.location.replace(that.$route.query.redirect)
                  } else {
                    window.location.replace(window.location.origin)
                  }
                } else {
                  that.$message('账户名或密码错误')
                }
              })
            } else {
              // 提醒填写正确
            }
          })
        })
      },
      localRegistration () {
        const that = this
        that.$nextTick(function () {
          that.$refs.registerForm.validate((valid) => {
            if (valid) {
              // 进行注册
              that.registration(that.registerForm).then(
                (registerOk) => {
                  if (registerOk) {
                    that.$message('注册成功')
                    that.changeSwitch()
                  } else {
                    that.$message('注册失败')
                  }
                }
              )
              // console.log(valid)
              return true
            } else {
              // 提醒填写正确
              return false
            }
          })
        })
      }
    }
  }
</script>

<style lang="stylus" rel="stylesheet/stylus">
  @import "../common/stylus/index.styl"
  .login_wrapper
    bg-image('../assets/login_background.png')
    z-index 2000
    position fixed
    right 0
    bottom 0
    left 0
    overflow auto
    margin 0
    .login_wrapper_inner
      display table
      width 100%
      height 100%
      .login
        display table-cell
        text-align center
        vertical-align middle
        .login_content
          box-shadow: 0 0 2px #b0b0b0
          border-radius 1px
          display inline-block
          width 430px
          .content_header
            padding-top 10px
            height 90px
            *
              color blue
            .header_title
              font-size 60px
              font-family Comic Sans MS
            .header_content
              height 30px
              line-height 30px
              font-size 22px
              font-family Bookman
          .content_body_login
            .login
              height 400px
            .registration
              height 300px
          .content_body_registration
            .registration
              height 300px
          .content_body
            table
              margin 10%
              width 80%
              height 80%
              .el-button
                width 100%
                height 37px
                border-radius 2px
                *
                  color #ffffff
                .el-button--primary
                  background-color blue
              .el-input__inner
                border-radius 2px
                height 40px
              .button_interval
                width 40%
          .content_foot
            display flex
            height 60px
            align-items center
            justify-content center
            overflow hidden
            width 100%
            background-color #f6f6f6
            border-top 1px solid #ebebeb
            font-size 16px
            span
              margin-left 10px
              cursor pointer
              color blue

</style>
