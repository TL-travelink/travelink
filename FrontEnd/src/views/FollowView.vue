<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import router from "@/router/index.js";

// 사용자 데이터를 저장할 상태
const users = ref([]);

// 서버에서 사용자 데이터를 가져오는 함수
const fetchUsers = async () => {
  try {
    const response = await axios.get(`${import.meta.env.VITE_API_URL}/member/search`, {
      headers: {
        Authorization: `Bearer ${sessionStorage.getItem('accessToken')}`
      }
    });
    users.value = response.data.map(user => ({
      ...user,
      isFollowed: false, // follow 상태를 추가합니다.
    }));
    console.log(response)
  } catch (error) {
    console.error('Error fetching users:', error);
    await router.push({ name: 'exception' })
  }
};

// Follow 상태를 토글하는 함수
const toggleFollow = async (user) => {
  user.isFollowed = !user.isFollowed;
  // Follow/Unfollow 요청을 서버로 보내는 로직을 여기에 추가할 수 있습니다.
  await axios.post(`${import.meta.env.VITE_API_URL}/member/follow/` + user.id, null, {
    headers: {
      Authorization: `Bearer ${sessionStorage.getItem('accessToken')}`
    }
  });

  console.log(`${user.id} is now ${user.isFollowed ? 'followed' : 'unfollowed'}`);
};

const decoding = (img) => {
  return `data:image/jpeg;base64,${img}`;
}

// 컴포넌트가 마운트될 때 사용자 데이터를 가져옴
onMounted(() => {
  fetchUsers();
});
</script>

<template>
  <div class="pt-24 mt-24"></div>
  <section class="bg-white border-b py-8">
    <div class="container mx-auto flex flex-wrap pt-4 pb-12">
      <div class="w-full max-w-full px-3 mb-6 mx-auto">
        <div class="relative flex-[1_auto] flex flex-col break-words min-w-0 bg-clip-border rounded-[.95rem] bg-white m-5">
          <div class="relative flex flex-col min-w-0 break-words border border-dashed bg-clip-border rounded-2xl border-stone-200 bg-light/30">
            <div class="px-9 pt-5 flex justify-between items-stretch flex-wrap min-h-[70px] pb-0 bg-transparent">
              <h3 class="flex flex-col items-start justify-center m-2 ml-0 font-medium text-xl/tight text-dark">
                <span class="mr-3 font-semibold text-dark">사용자 목록</span>
                <span class="mt-1 font-medium text-secondary-dark text-lg/normal">새로운 친구를 사귀어 보세요!</span>
              </h3>
              <div class="relative flex flex-wrap items-center my-2">
                <a href="javascript:void(0)" class="inline-block text-[.925rem] font-medium leading-normal text-center align-middle cursor-pointer rounded-2xl transition-colors duration-150 ease-in-out text-light-inverse bg-light-dark border-light shadow-none border-0 py-2 px-5 hover:bg-secondary active:bg-light focus:bg-light">
                  See other projects
                </a>
              </div>
            </div>
            <div class="flex-auto block py-8 pt-6 px-9">
              <div class="overflow-x-auto">
                <table class="w-full my-0 align-middle text-dark border-neutral-200">
                  <thead class="align-bottom">
                  <tr class="font-semibold text-[0.95rem] text-secondary-dark">
                    <th class="pb-3 text-start min-w-[175px]">사용자</th>
                    <th class="pb-3 text-end min-w-[100px]">나이</th>
                    <th class="pb-3 pr-12 text-end min-w-[175px]">성별</th>
                    <th class="pb-3 text-end min-w-[50px]">Follow</th>
                  </tr>
                  </thead>
                  <tbody>
                  <tr v-for="user in users" :key="user.id" class="border-b border-dashed last:border-b-0">
                    <td class="p-3 pl-0">
                      <div class="flex items-center">
                        <div class="relative inline-block shrink-0 rounded-2xl me-3">
                          <img v-if="decoding(user.proImg) !== 'data:image/jpeg;base64,null'" :src="decoding(user.proImg)" class="w-[50px] h-[50px] inline-block shrink-0 rounded-2xl" alt="">
                          <img v-else src="@/assets/no-user-image.gif" class="w-[50px] h-[50px] inline-block shrink-0 rounded-2xl" alt="">
                        </div>
                        <div class="flex flex-col justify-start">
                          <a href="javascript:void(0)" class="mb-1 font-semibold transition-colors duration-200 ease-in-out text-lg/normal text-secondary-inverse hover:text-primary">
                            {{ user.id }}
                          </a>
                        </div>
                      </div>
                    </td>
                    <td class="p-3 pr-0 text-end">
                      <span class="font-semibold text-light-inverse text-md/normal">{{ user.age || '미설정' }}</span>
                    </td>
                    <td class="p-3 pr-12 text-end">
                        <span class="text-center align-baseline inline-flex px-4 py-3 mr-auto items-center font-semibold text-[.95rem] leading-none text-primary bg-primary-light rounded-lg">
                          {{ user.gender || '미설정' }}
                        </span>
                    </td>
                    <td class="p-3 pr-0 text-end">
                      <button @click="toggleFollow(user)" class="ml-auto relative text-white bg-gradient-to-r from-blue-500 to-purple-600 hover:from-blue-600 hover:to-purple-700 flex items-center h-[35px] w-[100px] text-base font-medium leading-normal text-center align-middle cursor-pointer rounded-2xl transition-colors duration-200 ease-in-out shadow-lg justify-center px-4">
                        {{ user.isFollowed ? 'Unfollow' : 'Follow' }}
                      </button>
                    </td>
                  </tr>
                  </tbody>
                </table>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="flex flex-wrap -mx-3 mb-5">
      <div class="w-full max-w-full sm:w-3/4 mx-auto text-center">
        <p class="text-sm text-slate-500 py-1"> Travelink <a href="https://www.loopple.com/theme/riva-dashboard-tailwind?ref=tailwindcomponents" class="text-slate-700 hover:text-slate-900" target="_blank">TS</a> with <a href="https://www.loopple.com" class="text-slate-700 hover:text-slate-900" target="_blank">Trip</a>. </p>
      </div>
    </div>
  </section>
</template>

<style scoped>
</style>
