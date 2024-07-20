/*
 * @Description: 
 * @Author: 
 * @Date: 2024-07-14 09:22:05
 */
const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    port: 9192, // 端口
  },
})
