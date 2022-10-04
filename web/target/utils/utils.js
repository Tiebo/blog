import { ElMessage } from "element-plus";
//判断是移动端还是 pc 端 ，true 表示是移动端，false 表示是 pc 端
export function isMobileOrPc() {
    if (window.screen.width <= 990) {
        return true;
    }
    return /Android|webOS|iPhone|iPod|BlackBerry/i.test(navigator.userAgent);
}
function get_message(msg, type) {
    if (type === "success") {
        ElMessage({
            message: msg,
            type: 'success',
            duration: 1000
        });
    }
    else {
        ElMessage({
            message: msg,
            type: 'error',
            duration: 1000
        });
    }
}
export function resp_message(resp, msg, refresh_info) {
    if (resp.msg === "success") {
        get_message(msg, "success");
        refresh_info();
    }
    else {
        get_message(resp.msg, "error");
    }
}
