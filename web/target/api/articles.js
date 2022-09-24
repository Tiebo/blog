import request from "./request";
/**
 * @data {
 *     page: int, pageSize: int
 * }
 * @param data
 */
export function getArticlesList(data) {
    return request.post("/articles/info/getlist/", data).then(resp => resp.data);
}
/**
 * @data {
 *     tagsId : string
 * }
 * @param data
 */
export function getTagsByArticle(data) {
    return request.post("/articles/info/arctags/", data).then(resp => resp.data);
}
export function orArticleByHot() {
    return request.get("/articles/info/hottest/", {}).then(resp => resp.data);
}
export function orArticleByCreateDate() {
    return request.get("/articles/info/newest/", {}).then(resp => resp.data);
}
export function getArticleById(data) {
    return request.post(`/articles/info/article/`, data).then(resp => resp.data);
}
