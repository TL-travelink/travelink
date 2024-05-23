<script setup>
import axios from "axios";
import {computed} from "vue";

const props = defineProps({
  user: Object,
  index: Number
})

const decoding = (img) => {
  return `data:image/jpeg;base64,${img}`;
}

const rankImage = computed(() => {
  return `@/assets/rank${props.index + 1}.png`;
});

const src = `@/assets/rank${props.index + 1}.png`
</script>

<template>
  <div
      class="flex flex-col w-5/6 lg:w-1/4 mx-auto lg:mx-0 rounded-none lg:rounded-l-lg bg-white mt-4"
  >
    <img v-if="props.index === 0" src="@/assets/rank1.png" alt="no">
    <img v-if="props.index === 1" src="@/assets/rank2.png" alt="no">
    <img v-if="props.index === 2" src="@/assets/rank3.png" alt="no">
    <div
        class="flex-1 bg-white text-gray-600 rounded-t rounded-b-none overflow-hidden shadow"
    >
      <div class="p-8 text-3xl font-bold text-center border-b-4 justify-center flex">
        <div class="inline">
          <img class="rounded-full w-10 h-10 relative object-cover"
               v-if="decoding(props.user.proImg) !== 'data:image/jpeg;base64,null'" :src="decoding(props.user.proImg)"
               alt="이미지 없음">
          <img class="rounded-full w-10 h-10 relative object-cover" v-else src="@/assets/no-user-image.gif"
               alt="이미지 없음">
        </div>

        <div class="inline">
          {{props.user.id}}
        </div>
      </div>
      <ul class="w-full text-center text-sm">
        <li class="border-b py-4">{{props.user.name}}</li>
        <li class="border-b py-4">{{props.user.point}} point</li>
      </ul>
    </div>
  </div>
</template>

<style scoped>
</style>
