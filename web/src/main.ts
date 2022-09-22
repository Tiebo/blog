import {createApp} from 'vue'
import App from './App.vue'
import router from './router'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap/dist/js/bootstrap';
import 'bootstrap-icons/font/bootstrap-icons.css'
import './assets/css/index.css'
import {createPinia} from 'pinia'

const app = createApp(App)

app.use(router).use(ElementPlus).use(createPinia()).mount('#app')


