<script setup>
import ButtonComponent from "@/components/ButtonComponent.vue";
import {useMemberStore} from "@/stores/member.js"
import {storeToRefs} from "pinia";
import {onMounted, ref} from "vue";
import axios from "axios";
import { useRoute, useRouter } from "vue-router";
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'
import { faHeart } from '@fortawesome/free-solid-svg-icons'

// Initialize the store
const memberStore = useMemberStore()

// Use storeToRefs to access reactive properties
const { userInfo } = storeToRefs(memberStore)

const route = useRoute();
const router = useRouter();

const board = ref(null);
const comments = ref([]);
const newComment = ref({
  memberId: "", // Initialize memberId
  content: "",
  createAt: new Date(),
});

const loadBoard = async () => {
  try {
    const response = await axios.get(`${import.meta.env.VITE_API_URL}/board/${route.params.id}`, {
      headers: {
        Authorization: `Bearer ${sessionStorage.getItem('accessToken')}`
      }
    });
    board.value = response.data;
    comments.value = response.data.commentList || [];
    console.log(board.value)
  } catch (error) {
    console.error('Failed to fetch board data:', error);
    await router.push({ name: 'exception' });
  }
};

const addComment = async () => {
  if (!newComment.value.content.trim()) return;

  // Ensure memberId is set before adding a comment
  newComment.value.memberId = userInfo.value.id;

  const formData = new FormData();
  formData.append("memberId", newComment.value.memberId);
  formData.append("content", newComment.value.content);
  formData.append("createAt", newComment.value.createAt);

  try {
    await axios.post(`${import.meta.env.VITE_API_URL}/comment/${route.params.id}`, formData, {
      headers: {
        Authorization: `Bearer ${sessionStorage.getItem('accessToken')}`
      }
    });

    // Create a new comment object
    const newCommentObj = {
      memberId: newComment.value.memberId,
      content: newComment.value.content,
      createAt: newComment.value.createAt,
    };
    console.log(userInfo.value)
    console.log(newCommentObj.memberId);
    // Push the new comment object to comments array
    comments.value.push(newCommentObj);

    // Reset newComment
    newComment.value.content = "";
    newComment.value.createAt = new Date();
  } catch (error) {
    console.error('Failed to add comment:', error);
    await router.push({ name: 'exception' });
  }
};

const likeBoard = async () => {
  try {
    const formData = new FormData();
    formData.append("memberId", userInfo.value.id); // Corrected memberId access
    await axios.post(`${import.meta.env.VITE_API_URL}/like/${route.params.id}`, formData, {
      headers: {
        Authorization: `Bearer ${sessionStorage.getItem('accessToken')}`
      }
    });
    await loadBoard();
    // board.value.likeCount += 1; // Assuming the board object has a 'likes' field
  } catch (error) {
    console.error('Failed to like the board:', error);
    await router.push({ name: 'exception' });
  }
};

const decoding = (img) => {
  return `data:image/jpeg;base64,${img}`;
}

onMounted(() => {
  // Ensure userInfo is available when the component is mounted
  if (userInfo.value.id) {
    newComment.value.memberId = userInfo.value.id;
  }
  loadBoard();
});
</script>


<template>
  <div class="container mx-auto pt-24 mt-24">
    <section class="bg-white border-b py-8 shadow-lg rounded-lg">
      <div class="container mx-auto flex flex-wrap justify-center pt-4 pb-12">
        <div>
          <img class="mr-5 mb-5 rounded-full w-11 h-11 object-cover inline" v-if="decoding(board?.fileInfo.saveFile) !== 'data:image/jpeg;base64,null'" :src="decoding(board?.fileInfo.saveFile)" alt="이미지 없음">
          <img class="mr-5 rounded-full w-11 h-11 object-cover inline" v-else src="@/assets/no-user-image.gif" alt="이미지 없음">
          <h2 class="my-2 text-5xl font-bold leading-tight text-gray-800 inline">
          {{board?.memberId}}
          </h2>
        </div>
        <div class="w-full mb-4">
          <div class="h-1 mx-auto gradient w-80 opacity-25 my-0 py-0 rounded-t"></div>
        </div>
        <div class="w-full bg-gray-100 p-6 rounded-lg shadow-md lg:w-2/3">
          <div class="w-full title mb-4 text-center text-3xl font-semibold text-gray-700">{{ board?.title }}</div>
          <div class="custom-quill-editor mb-10 bg-white p-4 rounded-lg shadow-inner">
            <div v-html="board?.content"></div>
          </div>

          <div class="flex items-center justify-center mt-4 space-x-4">
            <button-component>
              <router-link :to="{name: 'board'}">뒤로가기</router-link>
            </button-component>
            <button-component>수정하기</button-component>
          </div>
        </div>
        <div class="w-full bg-gray-100 p-6 rounded-lg shadow-md lg:w-1/3" >
<!--          <h3 class="w-full title mb-4 text-center text-3xl font-semibold text-gray-800 mb-4">댓글달기</h3>-->
          <div class="lg:h-3/4">
            <div v-for="comment in comments" :key="comment.id" class="mb-4 p-4 bg-white rounded-lg shadow-md flex">
              <div class="text-gray-1000 mr-2">{{ comment.memberId }} : </div>
              <div class="text-gray-800" >{{comment.content }}</div>
            </div>
          </div>
          <div class="flex items-center space-x-4">
            <button @click="likeBoard" class="flex items-center space-x-2 px-4 py-2 bg-red-500 text-white rounded-lg shadow-md hover:bg-red-600">
              <FontAwesomeIcon :icon="faHeart" />
              <span>{{ board?.likeCount || 0 }}</span>
            </button>
          </div>

          <div class="flex items-start lg:h-1/4 mt-5">
            <textarea v-model="newComment.content" class="w-full p-4 border rounded-lg" placeholder="댓글을 입력하세요..."></textarea>
            <button-component @click="addComment" class="mb-2 px-6 py-2 bg-blue-600 text-white rounded-lg shadow-md hover:bg-blue-700">
              작성
            </button-component>
          </div>
        </div>
      </div>
    </section>
  </div>
</template>

<style scoped>
.title {
  border: 1px solid #d1d5db;
  padding: 1rem;
  background-color: #f3f4f6;
  border-radius: 0.375rem;
}

.custom-quill-editor {
  border: 1px solid #d1d5db;
  height: 500px;
  overflow-y: auto;
  background-color: #fff;
}

textarea {
  border: 1px solid #d1d5db;
  resize: none;
}
</style>
