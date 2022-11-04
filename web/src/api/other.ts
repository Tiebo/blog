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
    }).then(resp => resp.data)
}
export function qq_imgUrl(qq: string) {
    return axios.request({
        url: "https://api.vvhan.com/api/qq",
        method: "GET",
        params: {
            qq: qq,
        }
    }).then(resp => resp.data)
}
// 返回一张二次元背景图
export function get_body_img() {
    return axios.request({
        url: "https://api.dearxuan.com/image?result=json",
        method: "GET",
    }).then(resp => resp.data)
}
// 返回随机网易云歌曲
export function get_music() {
    return axios.request({
        url: "https://api.vvhan.com/api/rand.music?type=json&sort=热歌榜",
        method: "GET",
    }).then(resp => resp.data);
}