import axios from 'axios';
import {useUserStore} from "@/stores/user";
import router from "@/router";

const createAxios = axios.create({
    baseURL: 'http://127.0.0.1:4000',
    headers: {
        "Content-Type": "application/x-www-form-urlencoded;charset=UTF-8"
    },
    timeout: 4000,
});

interface formObject {
    [key: string]: any;
}
// 辅助函数
function objectToFormData(obj: formObject) {
    let formData = new FormData();
    for (let i in obj) {
        formData.append(i, obj[i]);
    }
    return formData;
}


function apiAxios(method: string, url: any, params: object) {
    const data = objectToFormData(params as formObject);
    if (data.get("token")) {
        console.log("token")
        return createAxios({
            method: method,
            url: url,
            data: method === 'POST' ? data : null,
            params: method === 'GET' ? data : null,
            headers: {
                authorization: "Bearer " + data.get("token"),
            }
        })
    }
    else {
        console.log("no token")
        return createAxios({
            method: method,
            url: url,
            data: method === 'POST' ? data : null,
            params: method === 'GET' ? data : null,
        })
    }
}

function get(url: string, params: object) {
    return apiAxios('GET', url, params)
}

function post(url: string, params: object) {
    return apiAxios('POST', url, params)
}


export default {
    get,
    post
}