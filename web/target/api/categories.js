import request from "@/api/request";
export function getCategoriesList(data) {
    return request.get("/categories/info/getlist/", data).then(resp => resp.data);
}
