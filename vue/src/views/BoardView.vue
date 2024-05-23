<script setup>
import {onMounted, ref} from "vue";
import BoardComponent from "@/components/BoardComponent.vue";
import ButtonComponent from "@/components/ButtonComponent.vue";
import axios from "axios";
import {useRouter} from "vue-router";
const router = useRouter()

const boardList = ref([])
const loadBoard = async () => {
  try {
    const response = await axios.get(`${import.meta.env.VITE_API_URL}/board`, {
      headers: {
        Authorization: `Bearer ${sessionStorage.getItem('accessToken')}`
      }
    });
    console.log(response)
    boardList.value = response.data;
  } catch (error) {
    console.error('Failed to fetch followers list:', error);
    await router.push({name: 'exception'});
  }
};
onMounted(() => {
  loadBoard();
})

</script>

<template>
  <div class="pt-24 mt-24">
  </div>
  <section class="bg-white border-b py-8">
    <div class="container mx-auto flex flex-wrap pt-4 pb-12">
      <h2 class="w-full my-2 text-5xl font-bold leading-tight text-center text-gray-800">Board</h2>

      <div class="w-full mb-4">
        <div class="h-1 mx-auto gradient w-64 opacity-25 my-0 py-0 rounded-t"></div>
      </div>
      <div class="w-full flex justify-end">
        <button-component>
          <RouterLink :to="{name:'create-board'}">글쓰기</RouterLink>
        </button-component>
      </div>
      <BoardComponent v-for="board in boardList" :board="board"/>
    </div>
  </section>
</template>

<style scoped>

</style>