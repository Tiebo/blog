import request from "@/api/request";

export function getCategoriesList(data: object) {
    return request.get("/categories/info/getlist/", data).then(resp => resp.data)
}