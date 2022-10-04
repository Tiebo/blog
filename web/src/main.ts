// 引入v-md-editor组件
import VMdEditor from '@kangc/v-md-editor';
// 代码复制
import '@kangc/v-md-editor/lib/plugins/copy-code/copy-code.css';
import createCopyCodePlugin from '@kangc/v-md-editor/lib/plugins/copy-code/index';
// 引入katex
import createKatexPlugin from '@kangc/v-md-editor/lib/plugins/katex/cdn';

// 代码行号
import createLineNumbertPlugin from '@kangc/v-md-editor/lib/plugins/line-number/index';
import VMdPreview from '@kangc/v-md-editor/lib/preview';
import '@kangc/v-md-editor/lib/style/base-editor.css';
import githubTheme from '@kangc/v-md-editor/lib/theme/github.js';
import '@kangc/v-md-editor/lib/theme/style/github.css';
// 引入bootstrap  bootstrap-icons
import 'bootstrap-icons/font/bootstrap-icons.css'
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap/dist/js/bootstrap';
// 引入 elementPlus
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
// 引入 代码高亮highlightjs
import hljs from 'highlight.js';
// 引入 Pinia
import { createPinia } from 'pinia'

import { createApp } from 'vue'
import App from './App.vue'
// 全局css
import './assets/css/index.css'
import router from './router'

VMdPreview.use(githubTheme, {
    Hljs: hljs,
}).use(createCopyCodePlugin()).use(createLineNumbertPlugin()).use(createKatexPlugin());
VMdEditor.use(githubTheme, {
    Hljs: hljs,
}).use(createCopyCodePlugin()).use(createKatexPlugin());

const app = createApp(App)
app.use(VMdPreview).use(VMdEditor);
app.use(ElementPlus)
app.use(router).use(createPinia()).mount('#app')


