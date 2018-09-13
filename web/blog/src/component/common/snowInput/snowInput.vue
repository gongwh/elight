<template>
  <div class="snow_input">
    <el-input v-model="currentValue" @blur="blur" @focus="focus" :placeholder="placeholder" :clearable="true"
              prefix-icon="el-icon-search"></el-input>
  </div>
</template>

<script type="text/ecmascript-6">
  export default {
    props: {
      value: [String, Number],
      placeholder: {
        type: String,
        default: '请输入'
      }
    },
    data () {
      return {
        currentValue: this.value === undefined || this.value === null ? '' : this.value
      }
    },
    watch: {
      'value' (val, oldValue) {
        this.setCurrentValue(val)
      },
      'currentValue' (val, oldValue) {
        this.setCurrentValue(val)
        this.$emit('input', val)
        this.$emit('change', val)
      }
    },
    computed: {},
    created () {
    },
    destroyed () {
    },
    methods: {
      setCurrentValue (value) {
        this.currentValue = value
      },
      focus (val) {
        this.$emit('focus', val)
      },
      blur (val) {
        this.$emit('blur', val)
      }
    }
  }
</script>

<style lang="stylus" rel="stylesheet/stylus">
  .snow_input
    .el-input
      max-width 200px
      .el-input__inner
        border-radius 6px
        height 36px
        background-color transparent
        border-color #c4c4c4
        &:focus
          border-color #626262
      .el-input__prefix, .el-input__suffix
        color black
      :-moz-placeholder
        color #8e8e8e
        opacity 1
      ::-moz-placeholder
        color #8e8e8e
        opacity 1
      input:-ms-input-placeholder
        color #8e8e8e
        opacity 1
      input:
      :-webkit-input-placeholder
        color #8e8e8e
        opacity 1
</style>
