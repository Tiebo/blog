import axios from 'axios';
import { ref } from 'vue';
// 返回一张二次元图片url
export function ani_imgUrl() {
    return axios.request({
        url: "https://api.dearxuan.com/image",
        method: "GET",
        params: {
            result: "json",
        }
    }).then(resp => resp.data);
}
export function qq_imgUrl(qq) {
    return axios.request({
        url: "https://api.vvhan.com/api/qq",
        method: "GET",
        params: {
            qq: qq,
        }
    }).then(resp => resp.data);
}
