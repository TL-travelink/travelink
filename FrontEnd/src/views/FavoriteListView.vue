<script setup>
import axios from "axios";
import {storeToRefs} from "pinia";
import {onMounted, ref} from "vue";
import FavoriteComponent from "@/components/FavoriteComponent.vue";
import router from "@/router/index.js";

const favoriteList = ref([]);

import {useMemberStore} from "@/stores/member.js"

const memberStore = useMemberStore()

const {userInfo} = storeToRefs(memberStore)


// 내 favotite
const loadFavorite = async () => {
  try {
    const response = await axios.get(`${import.meta.env.VITE_API_URL}/travel/favorite`, {
      headers: {
        Authorization: `Bearer ${sessionStorage.getItem('accessToken')}`
      }
    });
    favoriteList.value = [];
    favoriteList.value = response.data;
  } catch (error) {
    console.error('Failed to fetch followers list:', error);
    await router.push({name: 'exception'})
  }
};

// 친구 favotite
const loadFavoriteWithId = async (id) => {
  try {
    const response = await axios.get(`${import.meta.env.VITE_API_URL}/travel/favorite/` + id, {
      headers: {
        Authorization: `Bearer ${sessionStorage.getItem('accessToken')}`
      }
    });
    favoriteList.value = [];

    favoriteList.value = response.data;
  } catch (error) {
    console.error('Failed to fetch followers list:', error);
    await router.push({name: 'exception'})
  }
};

const followingsList = ref([]);

// 팔로우 목록
const fetchFollowingsList = async () => {
  try {
    const response = await axios.get(`${import.meta.env.VITE_API_URL}/member/following`, {
      headers: {
        Authorization: `Bearer ${sessionStorage.getItem('accessToken')}`
      }
    });

    followingsList.value = response.data.profiles;
    console.log(followingsList.value)

  } catch (error) {
    console.error('Failed to fetch followings list:', error);
    await router.push({name: 'exception'})
  }
};

const decoding = (img) => {
  return `data:image/jpeg;base64,${img}`;
}

onMounted(() => {
  loadFavorite();
  fetchFollowingsList();
})

</script>


<template>
  <div class="pt-24 mt-24">
    <section class="bg-white border-b py-8">
      <div class="flex">
        <!-- Followings Sidebar -->
        <aside class="w-1/4 h-screen overflow-y-auto bg-gray-100 p-4 flex flex-col items-center">
          <h2 class="text-2xl font-bold mb-4 text-center">Followings</h2>
          <ul class="w-full">
            <li class="mb-4 w-full">
              <div class="flex items-center justify-between">
                <img class="rounded-full w-10 h-10 relative object-cover"
                     v-if="decoding(userInfo.proImg) !== 'data:image/jpeg;base64,null'" :src="decoding(userInfo.proImg)"
                     alt="이미지 없음">
                <img v-else src="@/assets/no-user-image.gif"
                     alt="이미지 없음"
                     class="profile-image w-12 h-12 rounded-full object-cover border-2 border-gray-200 mr-4">
                <span> me </span>
                <button @click="loadFavorite"
                        class="px-4 py-2 bg-blue-500 text-white rounded-md hover:bg-blue-600 transition duration-300">
                  View Favorites
                </button>
              </div>
            </li>
            <li v-for="following in followingsList" :key="following.id" class="mb-4 w-full">
              <div class="flex items-center justify-between">
                <img v-if="decoding(following.proImg) !== 'data:image/jpeg;base64,null'"
                     :src="decoding(following.proImg)"
                     alt="Profile Image"
                     class="profile-image w-12 h-12 rounded-full object-cover border-2 border-gray-200 mr-4"/>
                <img v-else src="@/assets/no-user-image.gif"
                     alt="이미지 없음"
                     class="profile-image w-12 h-12 rounded-full object-cover border-2 border-gray-200 mr-4">
                <span>{{ following.id }}</span>
                <button @click="loadFavoriteWithId(following.id)"
                        class="px-4 py-2 bg-blue-500 text-white rounded-md hover:bg-blue-600 transition duration-300">
                  View Favorites
                </button>
              </div>
            </li>
          </ul>
        </aside>


        <!-- Main Content -->
        <section class="bg-white border-b py-8 flex-grow">
          <div class="container mx-auto flex flex-wrap pt-4 pb-12">
            <h2 class="w-full my-2 text-5xl font-bold leading-tight text-center text-gray-800">Favorite</h2>
            <div class="w-full mb-4">
              <div class="h-1 mx-auto gradient w-64 opacity-25 my-0 py-0 rounded-t"></div>
            </div>
            <div class="w-full flex justify-end">
            </div>
            <FavoriteComponent v-for="favorite in favoriteList" :key="favorite.id" :favorite="favorite"/>
          </div>
        </section>
      </div>
    </section>
  </div>
</template>


<style scoped>
aside {
  display: flex;
  flex-direction: column;
  align-items: center;
}
</style>
