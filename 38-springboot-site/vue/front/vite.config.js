import path from 'path'
import vue from '@vitejs/plugin-vue'
import Components from 'unplugin-vue-components/vite'
import AutoImport from 'unplugin-auto-import/vite'

import {
  ElementPlusResolver,
  // AntDesignVueResolver,
} from 'unplugin-vue-components/resolvers'

import { defineConfig } from 'vite'

// https://vitejs.dev/config/
export default defineConfig({
  resolve: {
    alias: {
      '@': path.resolve(__dirname, 'src'),
      components: path.resolve(__dirname, 'src/components'),
      layouts: path.resolve(__dirname, 'src/layouts'),
      assets: path.resolve(__dirname, 'src/assets'),
      plugins: path.resolve(__dirname, 'src/plugins'),
      views: path.resolve(__dirname, 'src/views'),
      utils: path.resolve(__dirname, 'src/utils'),
    },
  },
  css: {
    preprocessorOptions: {
      scss: {
        additionalData: `@import "@/assets/styles/scss/variables/index.scss";`,
      },
    },
  },

  plugins: [
    vue(),
    AutoImport({
      resolvers: [ElementPlusResolver()],
    }),
    Components({
      /* options */
      resolvers: [
        ElementPlusResolver(),
        //  AntDesignVueResolver()
      ],
    }),
  ],
  // 代理请求
  server: {
    port: '3000',
    cors: true,
    proxy: {
      '/api': {
        //target: 'http://172.20.21.208:8019/ta404',
        //target: 'http://192.168.1.110:8080',
        //target:"http://127.0.0.1:8081/ta404",
        // target:"http://10.108.248.208:8009/ta404",
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api/, ''),
      },
    },
  },
})
