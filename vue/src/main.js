import { createApp } from 'vue'
import { createPinia } from 'pinia'
import {useKakao} from "vue3-kakao-maps";
import App from './App.vue'
import router from './router'
import {Quill, QuillEditor} from "@vueup/vue-quill"
import ImageResize from "quill-image-resize-vue"
import '@vueup/vue-quill/dist/vue-quill.snow.css'
import '@/assets/index.css';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'
import { library } from '@fortawesome/fontawesome-svg-core'
import { faHeart } from '@fortawesome/free-solid-svg-icons'

library.add(faHeart)

Quill.register('modules/imageResize', ImageResize)

const app = createApp(App)

const globalOptions = {
    modules: {
        toolbar: [
            ['bold', 'italic', 'underline', 'strike'],
            ['blockquote', 'code-block'],
            [{ header: 1 }, { header: 2 }],
            [{ list: 'ordered' }, { list: 'bullet' }],
            [{ script: 'sub' }, { script: 'super' }],
            [{ indent: '-1' }, { indent: '+1' }],
            [{ direction: 'rtl' }],
            [{ size: ['small', false, 'large', 'huge'] }],
            [{ header: [1, 2, 3, 4, 5, 6, false] }],
            [{ color: [] }, { background: [] }],
            [{ font: [] }],
            [{ align: [] }],
            ['clean'],
            ['link', 'image', 'video']
        ],
        imageResize : {
        },
    },
    placeholder: '내용 입력',
    // readOnly: true,
    // theme: 'snow',
    disabled: false
}
// set default globalOptions prop
QuillEditor.props.globalOptions.default = () => globalOptions
app.use(createPinia())
app.use(router)


app.component('QuilEditor', QuillEditor)
app.component('font-awesome-icon', FontAwesomeIcon)
useKakao("304f50ffbd8f913ecfdc4c20b7d14750");

app.mount('#app')

