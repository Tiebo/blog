import { defineStore } from 'pinia'
import {useApiStore} from "@/stores/api";

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
        }
    },
    actions: {
        updateInfo(): void {
            useApiStore().apiUser.getUserInfo({token: this.token})
                .then(resp => {
                    const data = resp.data.data;
                    this.id = data.uid;
                    this.username = data.username;
                    this.photo = data.photo;
                    this.is_login = true;
                    this.is_admin = data.admin;
                    this.email = data.email;
                    this.description = data.description;
                    this.mobile_photo = data.mobilePhoneNumber;
                    this.status = data.status;
                    this.pulling_info = true;
                })
                .catch(resp => {
                    this.pulling_info = false;
                })
        },
        logout(): void {
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
            })
            localStorage.removeItem("token");
        }
    }
})
