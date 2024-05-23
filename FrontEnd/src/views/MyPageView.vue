<script setup>

import {storeToRefs} from "pinia";
import {ref, onMounted} from 'vue';

import axios from 'axios';
import router from "@/router/index.js";
import {useMemberStore} from "@/stores/member.js"
import ButtonComponent from "@/components/ButtonComponent.vue";

const memberStore = useMemberStore()

const {userInfo} = storeToRefs(memberStore)
const {getUserInfo} = memberStore;
const isFollow = ref(false);


// 팔로잉 가져오기
const followingsCount = ref(0);
const followingsList = ref([]);


const fetchFollowingsCount = async () => {
  try {
    const response = await axios.get(`${import.meta.env.VITE_API_URL}/member/following`, {
      headers: {
        Authorization: `Bearer ${sessionStorage.getItem('accessToken')}`
      }
    });
    followingsCount.value = response.data.profiles.length;
  } catch (error) {
    console.error('Failed to fetch followings count:', error);
    await router.push({name: 'exception'})
  }
};

const fetchFollowingsList = async () => {
  try {
    const response = await axios.get(`${import.meta.env.VITE_API_URL}/member/following`, {
      headers: {
        Authorization: `Bearer ${sessionStorage.getItem('accessToken')}`
      }
    });

    followingsList.value = response.data.profiles;
    console.log()

  } catch (error) {
    console.error('Failed to fetch followings list:', error);
    await router.push({name: 'exception'})
  }
};

// 팔로잉 가져오기
const followersCount = ref(0);
const followersList = ref([]);


// Fetch followers count and list
const fetchFollowersCount = async () => {
  try {
    const response = await axios.get(`${import.meta.env.VITE_API_URL}/member/follower`, {
      headers: {
        Authorization: `Bearer ${sessionStorage.getItem('accessToken')}`
      }
    });
    followersCount.value = response.data.profiles.length;
  } catch (error) {
    console.error('Failed to fetch followers count:', error);
    await router.push({name: 'exception'})
  }
};

const fetchFollowersList = async () => {
  try {
    const response = await axios.get(`${import.meta.env.VITE_API_URL}/member/follower`, {
      headers: {
        Authorization: `Bearer ${sessionStorage.getItem('accessToken')}`
      }
    });
    console.log(response)
    followersList.value = response.data.profiles;
  } catch (error) {
    console.error('Failed to fetch followers list:', error);
    await router.push({name: 'exception'})
  }
};

const openFollowersModal = async () => {
  // Call the method to fetch followers list here if needed
  await fetchFollowersList();
  isFollow.value = true;
  // showModal2.value = true; // Show the modal
};

const handleFollowingsClick = async () => {
  await fetchFollowingsList();
  // showModal.value = true;
  isFollow.value = false;
};

onMounted(() => {
  fetchFollowingsCount();
  fetchFollowingsList();
  fetchFollowersCount();
  fetchFollowersList();
});

// 언팔
const unfollow = async (id) => {
  try {
    await axios.post(`${import.meta.env.VITE_API_URL}/member/follow/` + id, null, {
      headers: {
        Authorization: `Bearer ${sessionStorage.getItem('accessToken')}`
      }
    });
    followingsCount.value -= 1;
    followingsList.value = followingsList.value.filter((f) => f.id !== id);

  } catch (error) {
    console.error('Failed to fetch followers list:', error);
    await router.push({name: 'exception'})
  }
}


// 정보 수정
// 상태 변수 정의
const showModal3 = ref(false);


// 모달 열기
const openModal3 = () => {
  showModal3.value = true;
};

// 모달 닫기
const closeModal3 = () => {
  showModal3.value = false;
};

// 파일 변경 핸들러

// 폼 제출 핸들러

const formData2 = ref({
  height: '',
  gender: '',
  age: '',
  image: null
});

const handleFileChange = (event) => {
  formData2.value.image = event.target.files[0];
};

const submitForm = async () => {
  const formData = new FormData();
  formData.append("height", formData2.value.height);
  formData.append("gender", formData2.value.gender);
  formData.append("age", formData2.value.age);
  formData.append("image", formData2.value.image);
  console.log(formData)
  try {
    const response = await axios.put(`${import.meta.env.VITE_API_URL}/member/info`, formData, {
      headers: {
        Authorization: `Bearer ${sessionStorage.getItem('accessToken')}`,
        "Content-Type": "multipart/form-data"
      }
    });
    console.log('Server response:', response.data);
    closeModal3();
    let token = sessionStorage.getItem("accessToken");
    await getUserInfo(token);

  } catch (error) {
    console.error('Error submitting form:', error);
    await router.push({name: 'exception'})
  }
};

const decoding = (img) => {
  return `data:image/jpeg;base64,${img}`;
}


</script>

<template>
  <div class="pt-24 mt-24">

  </div>
  <!-- component -->
  <link rel="preconnect" href="https://rsms.me/">
  <link rel="stylesheet" href="https://rsms.me/inter/inter.css">

  <div class="antialiased bg-white bg-opacity-50 w-full min-h-screen relative py-4">
    <div
        class="grid grid-cols-12 mx-auto gap-2 sm:gap-4 md:gap-6 lg:gap-10 xl:gap-14 max-w-7xl my-10 px-2">
      <div id="menu" class="bg-white/10 col-span-3 rounded-lg p-4 ">
        <h1 class="font-bold text-lg lg:text-3xl bg-gradient-to-br to-transparent bg-clip-text">
          Dashboard
        </h1>
        <a href="#"
           class="flex flex-col space-y-2 md:space-y-0 md:flex-row mb-5 items-center md:space-x-2 hover:bg-white/10 group transition duration-150 ease-linear rounded-lg group w-full py-3 px-2">
          <div>
            <img class="rounded-full w-10 h-10 relative object-cover"
                 v-if="decoding(userInfo.proImg) !== 'data:image/jpeg;base64,null'" :src="decoding(userInfo.proImg)"
                 alt="이미지 없음">
            <img class="rounded-full w-10 h-10 relative object-cover" v-else src="@/assets/no-user-image.gif"
                 alt="이미지 없음">
          </div>
          <div>
            <p class="font-medium group-hover:text-indigo-400 leading-4">{{ userInfo.name }}</p>
            <span class="text-xs">{{ userInfo.id }}</span>
          </div>
        </a>
        <hr class="my-2 border-slate-700">
        <div id="menu" class="flex flex-col space-y-2 my-5">
          <a href="#"
             class="hover:bg-white/10 transition duration-150 ease-linear rounded-lg py-3 px-2 group">
            <div class="flex flex-col space-y-2 md:flex-row md:space-y-0 space-x-2 items-center">
              <div>
                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24"
                     stroke-width="1.5" stroke="currentColor"
                     class="w-6 h-6 group-hover:text-indigo-400">
                  <path stroke-linecap="round" stroke-linejoin="round"
                        d="M2.25 12l8.954-8.955c.44-.439 1.152-.439 1.591 0L21.75 12M4.5 9.75v10.125c0 .621.504 1.125 1.125 1.125H9.75v-4.875c0-.621.504-1.125 1.125-1.125h2.25c.621 0 1.125.504 1.125 1.125V21h4.125c.621 0 1.125-.504 1.125-1.125V9.75M8.25 21h8.25"/>
                </svg>

              </div>
              <div>
                <p class="font-bold text-base text-black lg:text-lg leading-4 group-hover:text-indigo-400">
                  Dashboard</p>
                <p class="text-sm hidden md:block">Data overview</p>
              </div>

            </div>
          </a>

          <!-- 모달 -->
          <div v-if="showModal3" class="fixed inset-0 z-50 flex items-center justify-center bg-black bg-opacity-50">

            <!-- 모달 내용 -->
            <div class="bg-white rounded-lg shadow-lg p-8 max-w-md mx-auto mt-10">
              <div class="flex justify-between items-center mb-6">
                <h2 class="text-2xl font-semibold text-gray-800">개인정보 수정</h2>
                <span class="text-3xl font-bold cursor-pointer text-gray-500 hover:text-gray-800" @click="closeModal3">&times;</span>
              </div>
              <form>
                <div class="mb-4">
                  <label for="height" class="block text-gray-700 font-bold mb-2">키</label>
                  <input v-model="formData2.height" id="height" type="text" placeholder="키를 입력해주세요"
                         class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-blue-500">
                </div>
                <div class="mb-4">
                  <label for="gender" class="block text-gray-700 font-bold mb-2">성별</label>
                  <select v-model="formData2.gender" id="gender"
                          class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-blue-500">
                    <option value="">성별 선택</option>
                    <option value="남자">남자</option>
                    <option value="여자">여자</option>
                  </select>
                </div>
                <div class="mb-4">
                  <label for="age" class="block text-gray-700 font-bold mb-2">나이</label>
                  <input v-model="formData2.age" id="age" type="text" placeholder="나이를 입력해주세요"
                         class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-blue-500">
                </div>
                <div class="mb-6">
                  <label for="profileImage" class="block text-gray-700 font-bold mb-2">프로필 이미지</label>
                  <input type="file" id="profileImage" @change="handleFileChange"
                         class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-blue-500">
                </div>
                <div class="text-center"> <!-- 추가된 부분 -->
                  <button-component type="button" @click="submitForm"
                                    class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-opacity-50">
                    수정
                  </button-component>
                </div> <!-- 추가된 부분 끝 -->
              </form>
            </div>


          </div>

          <!-- 클릭할 요소 -->
          <a href="#" @click="openModal3"
             class="hover:bg-white/10 transition duration-150 ease-linear rounded-lg py-3 px-2 group">
            <div class="flex flex-col space-y-2 md:flex-row md:space-y-0 space-x-2 items-center">
              <div>
                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24"
                     stroke-width="1.5" stroke="currentColor"
                     class="w-6 h-6 group-hover:text-indigo-400">
                  <path stroke-linecap="round" stroke-linejoin="round"
                        d="M15 19.128a9.38 9.38 0 002.625.372 9.337 9.337 0 004.121-.952 4.125 4.125 0 00-7.533-2.493M15 19.128v-.003c0-1.113-.285-2.16-.786-3.07M15 19.128v.106A12.318 12.318 0 018.624 21c-2.331 0-4.512-.645-6.374-1.766l-.001-.109a6.375 6.375 0 0111.964-3.07M12 6.375a3.375 3.375 0 11-6.75 0 3.375 3.375 0 016.75 0zm8.25 2.25a2.625 2.625 0 11-5.25 0 2.625 2.625 0 015.25 0z"/>
                </svg>
              </div>
              <div>
                <p class="font-bold text-base lg:text-lg leading-4 group-hover:text-indigo-400">
                  Users</p>
                <p class="text-sm hidden md:block">Manage users</p>
              </div>
            </div>
          </a>


          <a href="#"
             class="hover:bg-white/10 transition duration-150 ease-linear rounded-lg py-3 px-2 group">
            <div class="flex flex-col space-y-2 md:flex-row md:space-y-0 space-x-2 items-center">
              <div>
                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24"
                     stroke-width="1.5" stroke="currentColor"
                     class="w-6 h-6 group-hover:text-indigo-400">
                  <path stroke-linecap="round" stroke-linejoin="round"
                        d="M9.594 3.94c.09-.542.56-.94 1.11-.94h2.593c.55 0 1.02.398 1.11.94l.213 1.281c.063.374.313.686.645.87.074.04.147.083.22.127.324.196.72.257 1.075.124l1.217-.456a1.125 1.125 0 011.37.49l1.296 2.247a1.125 1.125 0 01-.26 1.431l-1.003.827c-.293.24-.438.613-.431.992a6.759 6.759 0 010 .255c-.007.378.138.75.43.99l1.005.828c.424.35.534.954.26 1.43l-1.298 2.247a1.125 1.125 0 01-1.369.491l-1.217-.456c-.355-.133-.75-.072-1.076.124a6.57 6.57 0 01-.22.128c-.331.183-.581.495-.644.869l-.213 1.28c-.09.543-.56.941-1.11.941h-2.594c-.55 0-1.02-.398-1.11-.94l-.213-1.281c-.062-.374-.312-.686-.644-.87a6.52 6.52 0 01-.22-.127c-.325-.196-.72-.257-1.076-.124l-1.217.456a1.125 1.125 0 01-1.369-.49l-1.297-2.247a1.125 1.125 0 01.26-1.431l1.004-.827c.292-.24.437-.613.43-.992a6.932 6.932 0 010-.255c.007-.378-.138-.75-.43-.99l-1.004-.828a1.125 1.125 0 01-.26-1.43l1.297-2.247a1.125 1.125 0 011.37-.491l1.216.456c.356.133.751.072 1.076-.124.072-.044.146-.087.22-.128.332-.183.582-.495.644-.869l.214-1.281z"/>
                  <path stroke-linecap="round" stroke-linejoin="round"
                        d="M15 12a3 3 0 11-6 0 3 3 0 016 0z"/>
                </svg>

              </div>
              <div>
                <p class="font-bold text-base lg:text-lg leading-4 group-hover:text-indigo-400">
                  Settings</p>
                <p class=" text-sm hidden md:block">Edit settings</p>
              </div>

            </div>
          </a>
        </div>
        <p class="text-sm text-center text-gray-600">v1.0.5.3 | &copy; 2024 travelink-soft</p>
      </div>
      <div id="content" class="bg-white/10 col-span-9 rounded-lg p-6">
        <div id="24h">
          <h1 class="font-bold py-4 uppercase">Last 24h Statistics</h1>
          <div id="stats" class="grid gird-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">

            <div class="bg-black/60 to-white/5 p-6 rounded-lg" @click="openFollowersModal">
              <div class="flex flex-row space-x-4 items-center">
                <div id="stats-1">
                  <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24"
                       stroke-width="1.5" stroke="currentColor" class="w-10 h-10 text-white">
                    <path stroke-linecap="round" stroke-linejoin="round"
                          d="M15 19.128a9.38 9.38 0 002.625.372 9.337 9.337 0 004.121-.952 4.125 4.125 0 00-7.533-2.493M15 19.128v-.003c0-1.113-.285-2.16-.786-3.07M15 19.128v.106A12.318 12.318 0 018.624 21c-2.331 0-4.512-.645-6.374-1.766l-.001-.109a6.375 6.375 0 0111.964-3.07M12 6.375a3.375 3.375 0 11-6.75 0 3.375 3.375 0 016.75 0zm8.25 2.25a2.625 2.625 0 11-5.25 0 2.625 2.625 0 015.25 0z"/>
                  </svg>
                </div>
                <div>
                  <p class="text-indigo-300 text-sm font-medium uppercase leading-4">Followers</p>
                  <p class="text-white font-bold text-2xl inline-flex items-center space-x-2">
                    <span>{{ followersCount }}</span>
                    <span>
          <svg xmlns="http://www.w3.org/2000/svg" fill="none"
               viewBox="0 0 24 24" stroke-width="1.5"
               stroke="currentColor" class="w-6 h-6">
            <path stroke-linecap="round" stroke-linejoin="round"
                  d="M2.25 18L9 11.25l4.306 4.307a11.95 11.95 0 015.814-5.519l2.74-1.22m0 0l-5.94-2.28m5.94 2.28l-2.28 5.941"/>
          </svg>
        </span>
                  </p>
                </div>
              </div>
            </div>


            <div class="bg-black/60 p-6 rounded-lg">
              <div class="flex flex-row space-x-4 items-center cursor-pointer"
                   @click="handleFollowingsClick">
                <div id="stats-1">
                  <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24"
                       stroke-width="1.5" stroke="currentColor" class="w-10 h-10 text-white">
                    <path stroke-linecap="round" stroke-linejoin="round"
                          d="M15 19.128a9.38 9.38 0 002.625.372 9.337 9.337 0 004.121-.952 4.125 4.125 0 00-7.533-2.493M15 19.128v-.003c0-1.113-.285-2.16-.786-3.07M15 19.128v.106A12.318 12.318 0 018.624 21c-2.331 0-4.512-.645-6.374-1.766l-.001-.109a6.375 6.375 0 0111.964-3.07M12 6.375a3.375 3.375 0 11-6.75 0 3.375 3.375 0 016.75 0zm8.25 2.25a2.625 2.625 0 11-5.25 0 2.625 2.625 0 015.25 0z"/>
                  </svg>
                </div>
                <div>
                  <p class="text-blue-300 text-sm font-medium uppercase leading-4">Followings</p>
                  <p class="text-white font-bold text-2xl inline-flex items-center space-x-2">
                    <span>{{ followingsCount }}</span>
                    <span>
          <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5"
               stroke="currentColor" class="w-6 h-6">
            <path stroke-linecap="round" stroke-linejoin="round"
                  d="M2.25 18L9 11.25l4.306 4.307a11.95 11.95 0 015.814-5.519l2.74-1.22m0 0l-5.94-2.28m5.94 2.28l-2.28 5.941"/>
          </svg>
        </span>
                  </p>
                </div>
              </div>
            </div>
            <div class="bg-black/60 p-6 rounded-lg">
              <div class="flex flex-row space-x-4 items-center">
                <div id="stats-1">
                  <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24"
                       stroke-width="1.5" stroke="currentColor" class="w-10 h-10 text-white">
                    <path stroke-linecap="round" stroke-linejoin="round"
                          d="M12 6v12m-3-2.818l.879.659c1.171.879 3.07.879 4.242 0 1.172-.879 1.172-2.303 0-3.182C13.536 12.219 12.768 12 12 12c-.725 0-1.45-.22-2.003-.659-1.106-.879-1.106-2.303 0-3.182s2.9-.879 4.006 0l.415.33M21 12a9 9 0 11-18 0 9 9 0 0118 0z"/>
                  </svg>

                </div>
                <div>
                  <p class="text-teal-300 text-sm font-medium uppercase leading-4">Point</p>
                  <p class="text-white font-bold text-2xl inline-flex items-center space-x-2">
                    <span>{{ userInfo.point }}</span>
                  </p>
                </div>
              </div>
            </div>
          </div>
        </div>

        <div class="container mx-auto">
          <div class="flex justify-center my-4">
            <button @click="isFollow = true"
                    class="bg-blue-500 text-white px-6 py-2 rounded-lg mx-2 transition hover:bg-blue-600">Followers
            </button>
            <button @click="isFollow = false"
                    class="bg-green-500 text-white px-6 py-2 rounded-lg mx-2 transition hover:bg-green-600">Followings
            </button>
          </div>

          <div v-if="isFollow" class="max-w-2xl mx-auto bg-white rounded-lg shadow-lg p-6 mb-8">
            <div class="flex justify-between items-center mb-6">
              <h2 class="text-2xl font-semibold">Followers</h2>
            </div>
            <ul>
              <li v-for="follower in followersList" :key="follower.id" class="flex items-center mb-4">
                <img v-if="decoding(follower.proImg) !== 'data:image/jpeg;base64,null'" :src="decoding(follower.proImg)"
                     alt="Profile Image"
                     class="profile-image w-12 h-12 rounded-full object-cover border-2 border-gray-200 mr-4"/>
                <img v-else src="@/assets/no-user-image.gif" alt="이미지 없음"
                     class="profile-image w-12 h-12 rounded-full object-cover border-2 border-gray-200 mr-4">
                <div class="follower-info">
                  <span class="text-lg">{{ follower.id }}</span>
                </div>
              </li>
            </ul>
          </div>

          <div v-else class="max-w-2xl mx-auto bg-white rounded-lg shadow-lg p-6 mb-8">
            <div class="flex justify-between items-center mb-6">
              <h2 class="text-2xl font-semibold">Followings</h2>
            </div>
            <ul>
              <li v-for="following in followingsList" :key="following.id"
                  class="flex items-center justify-between mb-4">
                <div class="flex items-center">
                  <img v-if="decoding(following.proImg) !== 'data:image/jpeg;base64,null'"
                       :src="decoding(following.proImg)" alt="Profile Image"
                       class="profile-image w-12 h-12 rounded-full object-cover border-2 border-gray-200 mr-4"/>
                  <img v-else src="@/assets/no-user-image.gif" alt="이미지 없음"
                       class="profile-image w-12 h-12 rounded-full object-cover border-2 border-gray-200 mr-4">
                  <span class="text-lg">{{ following.id }}</span>
                </div>
                <button @click="unfollow(following.id)"
                        class="bg-red-500 text-white px-4 py-2 rounded-lg transition hover:bg-red-600">Unfollow
                </button>
              </li>
            </ul>
          </div>
        </div>

      </div>
    </div>
  </div>


</template>

<style>
:root {
  font-family: 'Inter', sans-serif;
}

@supports (font-variation-settings: normal) {
  :root {
    font-family: 'Inter var', sans-serif;
  }
}


/* Modal styles */
.modal {
  display: block;
  position: fixed;
  z-index: 1;
  left: 0;
  top: 50px;
  width: 100%;
  height: 100%;
  overflow: auto;
  background-color: rgba(0, 0, 0, 0.4);
  padding-top: 60px;
}

.modal-content {
  background-color: #fefefe;
  margin: 5% auto;
  padding: 20px;
  border: 1px solid #888;
  width: 80%;
  border-radius: 10px;
}

.close {
  color: #aaa;
  float: right;
  font-size: 28px;
  font-weight: bold;
}

.close:hover,
.close:focus {
  color: black;
  text-decoration: none;
  cursor: pointer;
}

.following-item {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
}

.profile-image {
  width: 50px;
  height: 50px;
  border-radius: 50%;
  margin-right: 10px;
}

.unfollow-button {
  background-color: #ff6347;
  color: white;
  border: none;
  padding: 5px 10px;
  border-radius: 5px;
  cursor: pointer;
}

.unfollow-button:hover {
  background-color: #cc4c3c;
}

</style>
