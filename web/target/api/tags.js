import request from "./request";
/**
 * @data {
 *     none
 * }
 * @param data
 */
export function getHottestTag(data) {
    return request.get("/tags/info/hottest/", data)
        .then(resp => resp.data.data);
}
/**
 * @data {
 *     tags: string
 * }
 * @param data
 */
export function getArticlesByTags(data) {
    return request.get("/tags/info/articles/", data).then(resp => resp.data);
}
