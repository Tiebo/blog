import request from "@/api/request";
/**
 * @data {
 *     none
 * }
 * @param data
 */
export function getCategoriesList(data) {
    return request.get("/categories/info/getlist/", data).then(resp => resp.data);
}
/**
 * @data {
 *     categories: string
 * }
 * @param data
 */
export function getArticlesByCategories(data) {
    return request.post("/categories/info/articles/", data).then(resp => resp.data);
}
