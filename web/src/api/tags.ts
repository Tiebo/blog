import request from "./request";

export function getHottestTag() {
    return request.get("/tags/info/hottest/", {});
}