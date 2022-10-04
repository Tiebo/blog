<template>
  <div :class="IsActive === '1'?'container':'container right-panel-active'" id="container">
    <div class="form-container sign-up-container">
      <form @submit.prevent="GetRegister()">
        <h1>Sign up</h1>
        <div class="social-container">
          <a href="#"><i class="bi bi-wechat"></i></a>
          <a href="#"><i class="bi bi-facebook"></i></a>
          <a href="#"> <i class="bi bi-google"></i> </a>
        </div>
        <span>or use your email for registration</span>
        <input v-model="username" type="text" placeholder="Name" />
        <input v-model="password" type="password" placeholder="password" />
        <input v-model="confirm_password" type="password" placeholder="confirm password" />
        <span>{{ error_message }}</span>
        <button type="submit">Sign Up</button>
      </form>
    </div>
    <div class="form-container sign-in-container">
      <form @submit.prevent="GetLogin">
        <h1>Sign in</h1>

        <div class="social-container">
          <a href="#"><i class="bi bi-facebook"></i></a>
          <a href="#"><i class="bi bi-wechat"></i></a>
          <a href="#"> <i class="bi bi-google"></i> </a>
        </div>

        <span>or use your Email</span>
        <input v-model="username" type="text" placeholder="Username" />
        <input v-model="password" type="password" placeholder="Password" />
        <a href="/">Forgot your password?</a>

        <button type="submit">Sign In</button>
      </form>
    </div>
    <div class="overlay-container">
      <div class="overlay">
        <div class="overlay-panel overlay-left">
          <h1>Welcome Back!</h1>
          <p>
            To keep connected with us please login with your personal info
          </p>
          <button class="ghost" @click="ToActive()" id="signIn">Sign In</button>
        </div>
        <div class="overlay-panel overlay-right">
          <h1>Hello,Friend!</h1>
          <p>Enter your personal details and start journey with us</p>
          <button class="ghost" @click="ToActive()" id="signUp">Sign Up</button>
        </div>
      </div>
    </div>
  </div>

</template>


<script setup lang="ts">
  import { qq_imgUrl } from "@/api/other";
  import router from "@/router";
  import { useApiStore } from "@/stores/api";
  import { useUserStore } from "@/stores/user";
  import { onMounted, onUnmounted, ref } from 'vue';


  const $api = useApiStore();
  const userStore = useUserStore();

  let IsActive = ref('1');
  let username = ref('');
  let password = ref('');
  let confirm_password = ref('');
  let error_message = ref('');

  onMounted(() => {
    if (localStorage.getItem("login_register")) {
      IsActive.value = localStorage.getItem("login_register") as string;
    }
  })

  onUnmounted(() => {
    localStorage.removeItem("login_register");
  })

  const GetLogin = (): void => {

    error_message.value = "";
    $api.apiUser.login({
      username: username.value,
      password: password.value
    }).then(async data => {
      if (data.msg === "success") {
        userStore.token = data.data.token;
        localStorage.setItem("token", userStore.token);
        await userStore.updateInfo();
        qq_imgUrl(userStore.qqAccount).then(resp => {
          userStore.qqName = resp.name;
          userStore.qqPhoto = resp.imgurl;
        })
        router.push({ name: "home_index" });
      }
    })
  }

  const GetRegister = (): void => {
    error_message.value = "";
    $api.apiUser.register({
      username: username.value,
      password: password.value,
      confirm_password: confirm_password.value
    }).then(data => {
      if (data.data.error_message !== "success")
        error_message.value = data.data.error_message;
      else {
        username.value = "";
        password.value = "";
        confirm_password.value = "";
        ToActive();
      }
    })
  }

  const ToActive = () => {
    IsActive.value === "1" ? IsActive.value = "0" : IsActive.value = "1";
    localStorage.setItem("login_register", IsActive.value);
  }

</script>


<style scoped>
  * {
    box-sizing: border-box;
  }

  body {
    background: #f6f5f7;
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
    font-family: 'Montserrat', sans-serif;
    height: 100vh;
    margin: -20px 0 50px;
  }

  h1 {
    font-weight: bold;
    margin: 0;
  }

  h2 {
    text-align: center;
  }

  p {
    font-size: 14px;
    font-weight: 100;
    line-height: 20px;
    letter-spacing: 0.5px;
    margin: 20px 0 30px;
  }

  span {
    font-size: 12px;
  }

  a {
    color: #333;
    font-size: 14px;
    text-decoration: none;
    margin: 15px 0;
  }

  button {
    border-radius: 20px;
    border: 1px solid #56b6c2;
    background-color: #56b6c2;
    color: #ffffff;
    font-size: 12px;
    font-weight: bold;
    padding: 12px 45px;
    letter-spacing: 1px;
    text-transform: uppercase;
    transition: transform 80ms ease-in;
  }

  button:active {
    transform: scale(0.95);
  }

  button:focus {
    outline: none;
  }

  button.ghost {
    background-color: transparent;
    border-color: #ffffff;
  }

  form {
    background-color: #ffffff;
    display: flex;
    align-items: center;
    justify-content: center;
    flex-direction: column;
    padding: 0 50px;
    height: 100%;
    text-align: center;
  }

  input {
    background-color: #eee;
    border: none;
    padding: 12px 15px;
    margin: 8px 0;
    width: 100%;
  }

  .container {
    background-color: #fff;
    border-radius: 15px;
    box-shadow: 0 14px 28px rgba(0, 0, 0, 0.25), 0 10px 10px rgba(0, 0, 0, 0.22);
    position: relative;
    overflow: hidden;
    width: 768px;
    max-width: 100%;
    min-height: 480px;
    margin-top: 10vh;
  }

  .form-container {
    position: absolute;
    top: 0;
    height: 100%;
    transition: all 0.6s ease-in-out;
  }

  .sign-in-container {
    left: 0;
    width: 50%;
    z-index: 2;
  }

  .container.right-panel-active .sign-in-container {
    transform: translateX(100%) !important;
  }

  .sign-up-container {
    left: 0;
    width: 50%;
    opacity: 0;
    z-index: 1;
  }

  .container.right-panel-active .sign-up-container {
    transform: translateX(100%);
    opacity: 1;
    z-index: 5;
    animation: show 0.6s;
  }

  @keyframes show {

    0%,
    49.99% {
      opacity: 0;
      z-index: 1;
    }

    50%,
    100% {
      opacity: 1;
      z-index: 5;
    }
  }


  .overlay-container {
    position: absolute;
    top: 0;
    left: 50%;
    width: 50%;
    height: 100%;
    overflow: hidden;
    transition: transform 0.6s ease-in-out;
    z-index: 100;
  }

  .container.right-panel-active .overlay-container {
    transform: translateX(-100%);
  }

  .overlay {
    background: #ff4b2b;
    background: -webkit-linear-gradient(to right, #ff4b2b, #ff416c);
    background: linear-gradient(to right, #7171af, #6b82d0);
    background-repeat: no-repeat;
    background-size: cover;
    background-position: 0 0;
    color: #ffffff;
    position: relative;
    left: -100%;
    height: 100%;
    width: 200%;
    transform: translateX(0);
    transition: transform 0.6s ease-in-out;
  }

  .container.right-panel-active .overlay {
    transform: translateX(50%);
  }

  .overlay-panel {
    position: absolute;
    display: flex;
    align-items: center;
    justify-content: center;
    flex-direction: column;
    text-align: center;
    top: 0;
    height: 100%;
    width: 50%;
    transform: translateX(0);
    transition: transform 0.6s ease-in-out;
  }

  .overlay-left {
    transform: translateX(-20%);
  }

  .container.right-panel-active .overlay-left {
    transform: translateX(0);
  }

  .overlay-right {
    right: 0;
    transform: translateX(0);
  }

  .container.right-panel-active .overlay-right {
    transform: translateX(20%);
  }

  .social-container {
    margin: 20px 0;
  }

  .social-container a {
    border: 1px solid #dddddd;
    border-radius: 50%;
    display: inline-flex;
    justify-content: center;
    align-items: center;
    margin: 0 5px;
    height: 40px;
    width: 40px;
  }
</style>