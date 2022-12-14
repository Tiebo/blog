import axios from 'axios';

const createAxios = axios.create({
    baseURL: 'http://127.0.0.1:3000/api',
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
        return createAxios({
            method: method,
            url: url,
            data: method === "POST" ? data : null,
            params: method === "GET" ? data : null,
            headers: {
                authorization: "Bearer " + data.get("token"),
            }
        })
    }
    else {
        return createAxios({
            method: method,
            url: url,
            data: method === "POST" ? data : null,
            params: method === "GET" ? data : null,
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