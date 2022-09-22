import request from "./request"
import {useUserStore} from "@/stores/user";

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

export function getArticleBody(data: object) {
    return request.get("/articles/info/body/",
        {
            body_id: 1,
        }
    ).then(resp => resp.data);
}