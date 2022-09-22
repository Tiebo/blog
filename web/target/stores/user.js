import { defineStore } from 'pinia';
import { useApiStore } from "@/stores/api";
import { qq_imgUrl } from "@/api/other";
export const useUserStore = defineStore('user', {
    state: () => {
        return {
            id: "",
            username: "",
            token: "",
            photo: "",
            is_login: false,
            is_admin: false,
            description: "",
            email: "",
            mobile_photo: "",
            status: "",
            pulling_info: false,
            qqAccount: "",
            qqPhoto: "",
            qqName: "",
        };
    },
    actions: {
        async updateInfo() {
            await useApiStore().apiUser.getUserInfo({ token: this.token })
                .then(respData => {
                const data = respData.data;
                this.id = data.user.uid;
                this.username = data.user.username;
                this.photo = data.user.photo;
                this.is_login = true;
                this.is_admin = data.user.admin;
                this.email = data.user.email;
                this.description = data.user.description;
                this.mobile_photo = data.user.mobilePhoneNumber;
                this.status = data.user.status;
                this.qqAccount = data.user.qqAccount;
                qq_imgUrl(this.qqAccount).then(data => {
                    this.qqPhoto = data.imgurl;
                    this.qqName = data.name;
                });
                this.pulling_info = true;
            })
                .catch(resp => {
                this.pulling_info = false;
            });
        },
        logout() {
            this.$patch({
                id: "",
                username: "",
                token: "",
                photo: "",
                is_login: false,
                is_admin: false,
                description: "",
                email: "",
                mobile_photo: "",
                status: "",
                pulling_info: false,
                qqAccount: "",
            });
            localStorage.removeItem("token");
        }
    }
});
