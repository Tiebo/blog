import request from "./request"


/**
 * @data {
 *     page: int, pageSize: int
 * }
 * @param data
 */
export function getArticlesList(data: object) {
    return request.post(
        "/articles/info/getlist/",
        data,
    ).then(resp => resp.data);
}

/**
 * @data {
 *     tagsId : string
 * }
 * @param data
 */
export function getTagsByArticle(data: object) {
    return request.post(
        "/articles/info/arctags/",
        data,
    ).then(resp => resp.data);
}

export function orArticleByHot() {
    return request.get("/articles/info/hottest/", {
    }).then(resp => resp.data);
}

export function orArticleByCreateDate() {
    return request.get("/articles/info/newest/", {
    }).then(resp => resp.data);
}

/**
 * @data {
 *  id: string
 * }
 */
export function getArticleById(data: object) {
    return request.post(`/articles/info/article/`,
        data
    ).then(resp => resp.data);
}

/**
 * @data {
 *  body_id: string
 * }
 */
export function updateArticle(data: object) {
    return request.post("/articles/update/info/", data).then(resp => resp.data)
}