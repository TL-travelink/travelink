<script setup>
import {ref, watch} from 'vue';
import axios from 'axios';
import ButtonComponent from "@/components/ButtonComponent.vue";
import {useRouter} from 'vue-router';
import Swal from 'sweetalert2';

const userId = ref('');
const userName = ref('');
const userAddress = ref('');
const validate = ref(false);
const password = ref('');
const confirmPassword = ref('');
const router = useRouter();

const validateUserId = () => {
  axios.get(`${import.meta.env.VITE_API_URL}/sign-up/${userId.value}`)
      .then(response => {
        if (response.data) {
          Swal.fire({
            icon: 'success',
            title: '유효한 아이디입니다.',
            showConfirmButton: false,
            timer: 1500
          });
          validate.value = true;
        } else {
          Swal.fire({
            icon: 'error',
            title: '유효하지 않은 아이디입니다.',
            showConfirmButton: false,
            timer: 1500
          });
          validate.value = false;
        }
      })
      .catch(error => {
        console.error('Error:', error);
        Swal.fire({
          icon: 'error',
          title: '오류가 발생했습니다.',
          text: '잠시 후 다시 시도해주세요.',
          showConfirmButton: true
        }).then(() => {
          router.push({name: 'exception'});
        });
      });
};

watch(userId, () => {
  validate.value = false;
});

const signUp = () => {
  if (password.value !== confirmPassword.value) {
    Swal.fire({
      icon: 'error',
      title: '비밀번호가 다릅니다.',
      showConfirmButton: true
    });
    return;
  }

  if (userName.value == "" || password.value == "" || confirmPassword.value == "" || userAddress.value == "") {
    Swal.fire({
      icon: 'warning',
      title: '모든 필드를 입력해주세요.',
      showConfirmButton: true
    });
    return;
  }

  if (!validate.value) {
    Swal.fire({
      icon: 'info',
      title: '아이디 유효성 검사를 진행해주세요.',
      showConfirmButton: true
    });
    return;
  }

  axios.post(`${import.meta.env.VITE_API_URL}/sign-up`, {
    id: userId.value,
    password: password.value,
    name: userName.value,
    address: userAddress.value
  })
      .then(() => {
        Swal.fire({
          icon: 'success',
          title: '회원가입이 완료되었습니다.',
          showConfirmButton: false,
          timer: 1500
        }).then(() => {
          router.push({name: 'login'});
        });
      })
      .catch(error => {
        console.error('Error:', error);
        Swal.fire({
          icon: 'error',
          title: '회원가입에 실패했습니다.',
          text: '잠시 후 다시 시도해주세요.',
          showConfirmButton: true
        }).then(() => {
          // router.push({name: 'exception'});
        });
      });
};
</script>

<template>
  <div class="h-screen flex">
    <div class="hidden lg:flex w-full lg:w-1/2 login_img_section justify-around items-center">
      <div class="bg-black opacity-20 inset-0 z-0"></div>
      <div class="w-full mx-auto px-20 flex-col items-center space-y-6">
        <h1 class="text-white font-bold text-4xl font-sans">Simple App</h1>
        <p class="text-white mt-1">The simplest app to use</p>
        <div class="flex justify-center lg:justify-start mt-6">
          <a
              href="#"
              class="hover:bg-indigo-700 hover:text-white hover:-translate-y-1 transition-all duration-500 bg-white text-indigo-800 mt-4 px-4 py-2 rounded-2xl font-bold mb-2"
          >Get Started</a
          >
        </div>
      </div>
    </div>
    <div class="flex w-full lg:w-1/2 justify-center items-center bg-white space-y-8">
      <div class="w-full px-8 md:px-32 lg:px-24">
        <form @submit.prevent="signUp" class="grid grid-cols-1 gap-6">
          <div>
            <label class="block text-sm text-gray-600 dark:text-black mb-2">이름</label>
            <input v-model="userName" type="text" placeholder="Enter your name" class="input"/>
          </div>
          <div>
            <label class="block text-sm text-gray-600 dark:text-black mb-2">아이디</label>
            <div class="flex">
              <input v-model="userId" type="text" placeholder="Enter your id" class="input flex-1"/>
              <button-component type="button" @click="validateUserId" class="btn ml-2" style="margin-left: 20px">아이디
                중복 체크
              </button-component>
            </div>
          </div>
          <div>
            <label class="block text-sm text-gray-600 dark:text-black mb-2">비밀번호</label>
            <input v-model="password" type="password" placeholder="Enter your password" class="input"/>
          </div>
          <div>
            <label class="block text-sm text-gray-600 dark:text-black mb-2">비밀번호 확인</label>
            <input v-model="confirmPassword" type="password" placeholder="Enter your password" class="input"/>
          </div>
          <div>
            <label class="block text-sm text-gray-600 dark:text-black mb-2">주소</label>
            <input v-model="userAddress" type="text" placeholder="Enter your address" class="input"/>
          </div>
          <button-component>Sign Up</button-component>

        </form>
      </div>
    </div>
  </div>
</template>

<style scoped>
.input {
  padding: 0.75rem 1rem;
  width: 100%;
  border-radius: 0.5rem;
  border: 1px solid #e2e8f0;
  transition: border-color 0.15s ease-in-out, box-shadow 0.15s ease-in-out;
}

.btn {
  padding: 0.75rem 1rem;
  border-radius: 0.5rem;
  border: none;
  cursor: pointer;
}

@media (min-width: 768px) {
  .input {
    max-width: 420px;
  }
}
</style>
