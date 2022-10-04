import { ElMessage } from "element-plus";
//判断是移动端还是 pc 端 ，true 表示是移动端，false 表示是 pc 端
export function isMobileOrPc() {
    if (window.screen.width <= 990) {
        return true;
    }
    return /Android|webOS|iPhone|iPod|BlackBerry/i.test(navigator.userAgent);
}
export function get_message(msg, type) {
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
            offset: 250,
            duration: 1000
        });
    }
}
