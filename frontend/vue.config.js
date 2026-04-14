module.exports = {
  devServer: {
    port: 8081,
    proxy: {
      '/api': {
        target: process.env.VUE_APP_PROXY_TARGET || 'http://127.0.0.1:8080',
        changeOrigin: true
      }
    }
  }
}
