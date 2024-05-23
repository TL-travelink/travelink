<script setup>
import { QuillEditor } from "@vueup/vue-quill";
import ButtonComponent from "@/components/ButtonComponent.vue";
import { ref } from "vue";
import axios from "axios";
import router from "@/router/index.js";

const board = ref({
  boardId: 1,
  memberId: "ssafy",
  title: "",
  content: "", // content는 HTML 문자열로 설정
  status: "active",
  upload: null,
});

const handleFileChange = (event) => {
  board.value.upload = event.target.files[0];
};

const create = async () => {
  const formData = new FormData();
  formData.append("memberId", board.value.memberId);
  formData.append("title", board.value.title);
  formData.append("content", board.value.content); // content를 그대로 추가
  formData.append("status", board.value.status);
  formData.append("upfile", board.value.upload);
  console.log(formData.values())
  console.log(board.value)
  try {
    const res = await axios.post(`${import.meta.env.VITE_API_URL}/board`, formData, {
      headers: {
        Authorization: `Bearer ${sessionStorage.getItem('accessToken')}`,
        "Content-Type": "multipart/form-data"
      }
    });
    await router.push({ name: 'board'})
  } catch (error) {
    console.log(error);
    await router.push({ name: 'exception'})
  }
};

// Quill Editor에서 HTML 내용을 가져오는 함수
const handleContentChange = (delta, oldDelta, source) => {
  const quillEditor = document.querySelector('.ql-editor');
  if (quillEditor) {
    board.value.content = quillEditor.innerHTML;
  }
};
</script>

<template>
  <div class="pt-24"></div>
  <section class="bg-white border-b py-8">
    <div class="container mx-auto flex flex-wrap pt-4 pb-12">
      <h2 class="w-full my-2 text-5xl font-bold leading-tight text-center text-gray-800">Board</h2>
      <div class="w-full mb-4">
        <div class="h-1 mx-auto gradient w-64 opacity-25 my-0 py-0 rounded-t"></div>
      </div>
      <form class="w-full">
        <input type="text" class="w-full title mb-4" placeholder="제목을 입력해주세요.." v-model="board.title"/>
        <div class="custom-quill-editor mb-10">
          <quill-editor v-model:value="board.content" @text-change="handleContentChange"></quill-editor>
        </div>
        <input type="file" class="w-full title mt-4" @change="handleFileChange"/>
        <div class="flex items-center justify-center">
          <button-component type="button" @click="create">생성</button-component>
        </div>
      </form>
    </div>
  </section>
</template>

<style scoped>
.title {
  border: 1px solid #d1d5db;
}
.custom-quill-editor {
  width: 100%;
  height: 500px;
}

:deep(.ql-container) {
  height: 100%; /* 컨테이너의 높이를 채우도록 설정 */
}

:deep(.ql-editor) {
  height: 100%;
}
</style>
