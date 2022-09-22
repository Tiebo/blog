import request from "./request";

export function getHottestTag(data: object) {
    return request.get("/tags/info/hottest/", data)
        .then(resp => resp.data.data);
}