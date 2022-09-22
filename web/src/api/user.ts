import request from "./request"

/**
 * @data {
 *     username: string
 *     password: string
 *     confirm_password: string
 * }
 * @param data
 */
export function register(data: object) {
    return request.post("/user/account/register/", data).then(resp => resp.data);
}

/**
 * @data {
 *     username: string
 *     password: string
 * }
 * @param data
 */
export function login(data: object) {
    return request.post("/user/account/token/", data).then(resp => resp.data);
}

/**
 * @data {
 *     token: string
 * }
 * @param data
 */
export function getUserInfo(data: object) {
    return request.get("/user/account/info/", data).then(resp => resp.data);
}