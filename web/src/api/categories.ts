import request from "@/api/request";


/**
 * @data {
 *     none
 * }
 * @param data
 */
export function getCategoriesList(data: object) {
    return request.get("/categories/info/getlist/", data).then(resp => resp.data);
}

/**
 * @data {
 *     categories: string
 * }
 * @param data
 */
export function getArticlesByCategories(data: object) {
    return request.post("/categories/info/articles/", data).then(resp => resp.data);
}

export function dropCategories(data: object) {
    return request.post("/categories/remove/info/", data).then(resp => resp.data);
}

export function createCategories(data: object) {
    return request.post("/categories/create/info/", data).then(resp => resp.data);
}

export function updateCategories(data: object) {
    return request.post("/categories/update/info/", data).then(resp => resp.data);
}