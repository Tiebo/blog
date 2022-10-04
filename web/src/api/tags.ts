import request from "./request";

/**
 * @data {
 *     none
 * }
 * @param data
 */
export function getHottestTag(data: object) {
    return request.get("/tags/info/hottest/", data)
        .then(resp => resp.data.data);
}

/**
 * @data {
 *     tags: string
 * }
 * @param data
 */
export function getArticlesByTags(data: object) {
    return request.post("/tags/info/articles/", data).then(resp => resp.data);
}

export function createTags(data: object) {
    return request.post("/tags/create/info/", data).then(resp => resp.data);
}

export function removeTags(data: object) {
    return request.post("/tags/remove/info/", data).then(resp => resp.data);
}