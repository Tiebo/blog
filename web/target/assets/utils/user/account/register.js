import { useApiStore } from "@/stores/api";
export default class registerView {
    static register(username, password, confirm_password) {
        const $api = useApiStore();
        $api.apiUser.register({
            username,
            password,
            confirm_password
        }).then(resp => {
            console.log(resp.data);
        });
        return "";
    }
}
