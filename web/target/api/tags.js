import request from "./request";
export function getHottestTag(data) {
    return request.get("/tags/info/hottest/", data)
        .then(resp => resp.data.data);
}
