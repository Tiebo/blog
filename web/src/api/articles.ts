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
    )
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
    )
}

export function orArticleByHot() {
    return request.get("/articles/info/hottest/", {
        token: useUserStore().token,
    });
}

export function orArticleByCreateDate() {
    return request.get("/articles/info/newest/", {
        token: useUserStore().token,
    });
}