import request from "./request";
/**
 * @data {
 *     username: string
 *     password: string
 *     confirm_password: string
 * }
 * @param data
 */
export function register(data) {
    return request.post("/user/account/register/", data);
}
/**
 * @data {
 *     username: string
 *     password: string
 * }
 * @param data
 */
export function login(data) {
    return request.post("/user/account/token/", data);
}
/**
 * @data {
 *     token: string
 * }
 * @param data
 */
export function getUserInfo(data) {
    return request.get("/user/account/info/", data);
}
