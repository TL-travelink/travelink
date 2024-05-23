<script setup>
import router from "@/router/index.js";
import { defineProps, computed } from "vue";
import ButtonComponent from "@/components/ButtonComponent.vue";

const props = defineProps({
  favorite: Object
});

const pageNav = (id) => {
  router.push({ name: 'favorite-view', params: { id: id } })
};

const shortStartAt = computed(() => {
  return props.favorite.startAt ? props.favorite.startAt.substring(0, 10) : '';
});

const shortEndAt = computed(() => {
  return props.favorite.endAt ? props.favorite.endAt.substring(0, 10) : '';
});

// 이미지 경로를 동적으로 생성하여 내보냄
const randomImage = `/src/assets/ex${getRandomInt(1, 10)}.jpg`;

function getRandomInt(min, max) {
  min = Math.ceil(min);
  max = Math.floor(max);
  return Math.floor(Math.random() * (max - min + 1)) + min;
}
</script>

<template>
  <div class="w-full md:w-1/4 p-6 flex flex-col flex-shrink">
    <div class="flex-1 bg-white rounded-t rounded-b-none overflow-hidden shadow">
      <a href="#" class="flex flex-wrap no-underline hover:no-underline">
        <!-- 이미지 경로를 바인딩하여 표시 -->
        <img :src="randomImage" class="object-cover w-full h-40"/>
        <div class="w-full font-bold text-xl text-gray-800 px-6 py-4">
          {{ props.favorite.title }}
        </div>
      </a>
      <div class="flex items-center justify-between text-gray-500 px-6 py-2">
        <button class="flex items-center gap-2 px-3 py-1 rounded-full bg-gray-100 hover:bg-gray-200">
          <svg class="w-5 h-5 fill-current text-gray-600" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24">
            <path
                d="M12 21.35l-1.45-1.32C6.11 15.36 2 12.28 2 8.5 2 5.42 4.42 3 7.5 3c1.74 0 3.41.81 4.5 2.09C13.09 3.81 14.76 3 16.5 3 19.58 3 22 5.42 22 8.5c0 3.78-4.11 6.86-8.55 11.54L12 21.35z"/>
          </svg>
          <span>{{ shortStartAt }}</span>
        </button>
        <button class="flex items-center gap-2 px-3 py-1 rounded-full bg-gray-100 hover:bg-gray-200">
          <svg class="w-5 h-5 fill-current text-gray-600" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24">
            <path
                d="M12 21.35l-1.45-1.32C6.11 15.36 2 12.28 2 8.5 2 5.42 4.42 3 7.5 3c1.74 0 3.41.81 4.5 2.09C13.09 3.81 14.76 3 16.5 3 19.58 3 22 5.42 22 8.5c0 3.78-4.11 6.86-8.55 11.54L12 21.35z"/>
          </svg>
          <span>{{ shortEndAt }}</span>
        </button>
      </div>
    </div>

    <div class="flex-none mt-auto bg-white rounded-b rounded-t-none overflow-hidden shadow p-6">
      <div class="flex items-center justify-between mb-2">
        <div class="inline-block rounded-full bg-teal-500 px-3 py-1.5 text-xs font-bold uppercase text-white">
          <p>{{ props.favorite.author }}</p>
        </div>
        <div class="inline-block rounded-full bg-teal-500 px-3 py-1.5 text-xs font-bold uppercase text-white">
          <p>{{ props.favorite.visibility }}</p>
        </div>
      </div>
      <div class="flex items-center justify-center">
        <button-component @click="pageNav(props.favorite.id)">상세보기</button-component>
      </div>
    </div>
  </div>
</template>

<style scoped>
.thumbnail {
  width: 100%;
  height: 100%;
}
</style>
