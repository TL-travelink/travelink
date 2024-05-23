<script setup>
import {computed, ref} from 'vue';
import axios from 'axios';
import {GoogleGenerativeAI} from '@google/generative-ai'
import {KakaoMap, KakaoMapCustomOverlay, KakaoMapMarker, KakaoMapMarkerPolyline} from 'vue3-kakao-maps';
import draggable from 'vuedraggable';
import {useRouter} from "vue-router";

const router = useRouter();

const visibleRef = ref([]);

const favorites = ref([]);

const API_KEY = 'AIzaSyAs1nvmo88BWaTlmCdr2SxuQiia5FLB20Y'

const pro = ref('이제 부터 우리는 여행지를 추천해줘야하는데 이 사람의 기분을 적을거야 그러면 그거에 맞게 너가 여행지를 추천해줘' +
    '아래와 같은 예시로 JSON 데이터를 보내줘 {\n' +
    '    "responses": [\n' +
    '        {\n' +
    '            "contentId": 125266,\n' +
    '            "contentType": 12,\n' +
    '            "title": "국립 청태산자연휴양림",\n' +
    '            "addr1": "강원도 횡성군 둔내면 청태산로 610",\n' +
    '            "addr2": "",\n' +
    '            "firstImage": "http://tong.visitkorea.or.kr/cms/resource/21/2657021_image2_1.jpg",\n' +
    '            "mapY": 37.52251412000000000,\n' +
    '            "mapX": 128.29191150000000000\n' +
    '        },\n' +
    '        {\n' +
    '            "contentId": 337517,\n' +
    '            "contentType": 12,\n' +
    '            "title": "국립횡성숲체원",\n' +
    '            "addr1": "강원도 횡성군 둔내면 청태산로 777",\n' +
    '            "addr2": "(둔내면)",\n' +
    '            "firstImage": "http://tong.visitkorea.or.kr/cms/resource/31/2553931_image2_1.jpg",\n' +
    '            "mapY": 37.53727343000000000,\n' +
    '            "mapX": 128.28671160000000000\n' +
    '        }\n' +
    '    ]\n' +
    '}' +
    'contentId의 경우 3000000 이상의 값으로 나오게 해줘'
);
const question = ref('')
const answer = ref('')
const map = ref();
const searchResults = ref([]);

const onLoadKakaoMap = (mapRef) => {
  map.value = mapRef;
};

const isLoading = ref(false)

const fetchAnswer = async () => {
  answer.value = ''
  isLoading.value = true

  try {
    answer.value = await useGetGenerativeModelGP(question.value)
    loadMarkers();
  } catch (error) {
    console.error('Error fetching answer:', error)
    await router.push({name: 'exception'});
  } finally {
    isLoading.value = false
    question.value = ''
  }
}

const useGetGenerativeModelGP = async (prompt) => {
  const model = await useGenAi()
  const result = await model.generateContent(pro.value + prompt)
  const text = await result.response.text() // Ensure we await the text if it's a promise

  return text
}

const useGenAi = async () => {
  const genAI = new GoogleGenerativeAI(API_KEY)
  const model = await genAI.getGenerativeModel({model: 'gemini-pro'})

  return model
}

// 즐겨찾기 추가
const addToFavorites = (result) => {
  if (!favorites.value.find((fav) => fav.contentId === result.contentId)) {
    favorites.value.push({contentId: result.contentId, title: result.title, lat: result.mapY, lng: result.mapX});
  }
};
const markerList = computed(() => {
  return favorites.value.map(i => ({lat: i.lat, lng: i.lng}));
})


// 즐겨찾기 제거
const removeFavorite = (index) => {
  favorites.value.splice(index, 1);
};


const loadMarkers = () => {
  const response = JSON.parse(answer.value);

  console.log(response);

  searchResults.value.splice(0, searchResults.value.length);

  response.responses.forEach(async (item) => {
    searchResults.value.push(item);
    visibleRef.value.push(false);

    // 서버에 POST 요청을 보냅니다.
    try {
      await fetch(`${import.meta.env.VITE_API_URL}/travel`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({
          contentId: item.contentId,
          title: item.title,
          addr1: item.addr1,
          addr2: item.addr2,
          firstImage: item.firstImage,
          mapY: item.mapY,
          mapX: item.mapX
        })
      });

      console.log('Successfully posted:', item);
    } catch (error) {
      console.error('Error posting data:', error);
      await router.push({name: 'exception'});
    }
  });

  let bounds = new kakao.maps.LatLngBounds();
  searchResults.value.forEach((markerInfo) => {
    const lat = markerInfo.mapY;
    const lng = markerInfo.mapX;
    const point = new kakao.maps.LatLng(Number(lat), Number(lng));
    bounds.extend(point);
  });

  map.value?.setBounds(bounds);
};


const onClickKakaoMapMarker = (index) => {
  visibleRef.value[index] = !visibleRef.value[index];
};

const showModal = ref(false);
const scheduleTitle = ref('');
const scheduleStartDate = ref('');
const scheduleEndDate = ref('');
const scheduleVisibility = ref('public') // 기본값은 '전체 공개'

const handleAddToSchedule = () => {
  showModal.value = true;
};

const saveSchedule = async () => {
  try {
    const scheduleData = {
      title: scheduleTitle.value,
      startDate: scheduleStartDate.value,
      endDate: scheduleEndDate.value,
      travels: favorites.value.map(i => i.contentId),
      visibility: scheduleVisibility.value
    };

    const response = await axios.post(`${import.meta.env.VITE_API_URL}/travel/schedule`,
        scheduleData, {
          headers: {
            Authorization: `Bearer ${sessionStorage.getItem('accessToken')}`
          }
        });

    console.log('Schedule saved:', response.data);

    scheduleTitle.value = '';
    scheduleStartDate.value = '';
    scheduleEndDate.value = '';
    showModal.value = false;

    favorites.value = [];

  } catch (error) {
    console.error('Failed to save schedule:', error);
    await router.push({name: 'exception'});
  }
};

</script>

<template>
  <div class="mt-24 pt-24">

    <div class="w-4/5 mx-auto text-center">
      <h1 class="text-3xl font-bold mb-8">여행지 추천 서비스</h1>
      <form class="mb-8" @submit.prevent="fetchAnswer">
        <div class="flex items-center justify-center space-x-4 mb-4">
          <label for="question" class="font-semibold whitespace-nowrap">오늘의 기분을 알려주세요:</label>
          <textarea name="question" id="question" cols="30" rows="3" v-model="question"
                    class="flex-1 p-2 border border-gray-300 rounded-md resize-none"></textarea>
          <button type="submit" :disabled="!question"
                  class="px-4 py-2 bg-blue-500 text-white rounded-md hover:bg-blue-600 transition duration-300">
            {{ isLoading ? '로딩 중...' : '여행지 추천 받기' }}
          </button>
        </div>
      </form>


    <section class="bg-white border-t py-8 mt-8 flex">
        <div class="w-3/4">
          <KakaoMap :lat="37.566826" :lng="126.9786567" @onLoadKakaoMap="onLoadKakaoMap" width="100%" height="700px"
                    class="map">
            <KakaoMapMarkerPolyline :markerList="markerList" :endArrow="true"/>
            <div v-for="(result, index) in searchResults" :key="result">
              <KakaoMapMarker :lat="result.mapY" :lng="result.mapX" :clickable="true"
                              @onClickKakaoMapMarker="() => onClickKakaoMapMarker(index)"/>
              <KakaoMapCustomOverlay
                  v-for="(result, index) in searchResults"
                  :key="result"
                  :lat="result.mapY"
                  :lng="result.mapX"
                  :yAnchor="1.4"
                  :visible="visibleRef[index]"
              >
                <div class="info-window">
                  <div class="place-name">{{ result.title }}</div>
                  <div class="address">{{ result.addr1 }}</div>
                  <div class="foot">
                    <img
                        :src="result.firstImage !== '' ? result.firstImage : 'src/assets/no-image.jpg'"
                        alt="Image"
                        style="width: 100%; height: auto;"
                    />
                    <button @click="addToFavorites(result)" class="fav-button">추가</button>
                  </div>
                  <!-- 닫기 버튼 추가 -->
                  <button @click="visibleRef[index] = false" class="close-button">닫기</button>
                </div>
              </KakaoMapCustomOverlay>

            </div>

          </KakaoMap>
        </div>

        <div class="w-1/4 pl-4">
          <div class="favorites-container">
            <div class="favorites-title">Favorites</div>
            <draggable :list="favorites" group="favorites" @start="onDragStart" @end="onDragEnd" itemKey="id">
              <template #item="{ element, index }">
                <div class="favorite-item">
                  {{ element.title }}
                  <button @click="removeFavorite(index)" class="remove-button">Remove</button>
                </div>
              </template>
            </draggable>
            <button @click="handleAddToSchedule"
                    class="btn btn-outline-success w-full bg-blue-500 text-white p-2 rounded-md mt-4">
              일정 담기
            </button>
          </div>
        </div>
      </section>

      <!-- Modal -->
      <div v-if="showModal" class="modal">
        <div class="modal-content">
          <span class="close" @click="showModal = false">&times;</span>
          <h2>일정 담기</h2>
          <input v-model="scheduleTitle" placeholder="일정 제목을 입력하세요" class="form-control mb-2"/>
          <input v-model="scheduleStartDate" type="date" placeholder="시작일정"
                 class="form-control mb-2"/>
          <input v-model="scheduleEndDate" type="date" placeholder="종료일정"
                 class="form-control mb-2"/>

          <!-- 공개 범위 선택 -->
          <select v-model="scheduleVisibility" class="form-control mb-2">
            <option value="public">전체 공개</option>
            <option value="mutual">맞팔만 공개</option>
            <option value="private">비공개</option>
          </select>

          <button @click="saveSchedule"
                  class="btn btn-outline-success w-full bg-blue-500 text-white p-2 rounded-md">
            저장
          </button>
        </div>
      </div>
      <!--      <div>-->
      <!--        <p v-if="answer">{{ answer }}</p>-->
      <!--      </div>-->

    </div>
  </div>
</template>

<style scoped>
.mb-5 {
  margin-bottom: 5rem;
}

#searchResult {
  display: inline-block;
  white-space: nowrap;
  text-align: center;
}

.info-window {
  padding: 10px;
  background-color: #fff;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.place-name {
  font-size: 18px;
  font-weight: bold;
  margin-bottom: 5px;
}

.address {
  font-size: 14px;
  margin-bottom: 5px;
}

.image-container {
  width: 100%;
  margin-bottom: 10px;
}

.info-image {
  width: 100%;
  height: auto;
}

.fav-button {
  background-color: #007bff;
  color: #fff;
  border: none;
  padding: 5px 10px;
  border-radius: 5px;
  cursor: pointer;
}

.fav-button:hover {
  background-color: #0056b3;
}

.favorites-container {
  border: 1px solid #ccc;
  border-radius: 5px;
  padding: 10px;
  margin-bottom: 10px;
}

.favorites-title {
  font-size: 18px;
  font-weight: bold;
  margin-bottom: 10px;
}

.favorite-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 5px;
}

.remove-button {
  background-color: #ff4d4d;
  color: #fff;
  border: none;
  padding: 5px 10px;
  border-radius: 5px;
  cursor: pointer;
}

.remove-button:hover {
  background-color: #cc0000;
}

.modal {
  display: flex;
  justify-content: center;
  align-items: center;
  position: fixed;
  z-index: 1;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  overflow: auto;
  background-color: rgb(0, 0, 0);
  background-color: rgba(0, 0, 0, 0.4);
}

.modal-content {
  background-color: #fefefe;
  margin: auto;
  padding: 20px;
  border: 1px solid #888;
  width: 80%;
  max-width: 500px;
  border-radius: 10px;
}

.modal-content input[type="date"] {
  width: 100%;
  padding: 10px;
  margin-bottom: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
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

.close-button {
  position: absolute;
  top: 5px;
  right: 5px;
  background-color: transparent;
  border: none;
  color: #007bff;
  cursor: pointer;
}

.close-button:hover {
  text-decoration: underline;
}
</style>
