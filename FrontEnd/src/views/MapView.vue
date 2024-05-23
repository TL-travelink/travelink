<script setup>
import {computed, ref, watch} from 'vue';
import axios from 'axios';
import {
  KakaoMap,
  KakaoMapCustomOverlay,
  KakaoMapMarker,
  KakaoMapMarkerPolyline
} from 'vue3-kakao-maps';
import draggable from 'vuedraggable';
import router from "@/router/index.js";

const selectedRegion = ref(0);
const selectedDistrict = ref(0);
const selectedType = ref(0);
const keyword = ref('');
const dynamicOptions = ref([]);
const searchResults = ref([]);

// 지도 범위 재설정
const map = ref();

// 지도를 재설정할 범위정보를 가지고 있을 LatLngBounds 객체를 생성합니다
let bounds;

// 클릭시 이벤트
const visibleRef = ref([]);

const onClickKakaoMapMarker = (index) => {
  visibleRef.value[index] = !visibleRef.value[index];
};

// 검색하기
const search = async () => {
  try {
    const response = await axios.get(`${import.meta.env.VITE_API_URL}/travel/search`, {
      params: {
        areaCode: selectedRegion.value,
        gugun: selectedDistrict.value,
        contentTypeId: selectedType.value,
        keyword: keyword.value,
      }
    });

    searchResults.value.splice(0, searchResults.value.length);

    console.log(searchResults.value)

    response.data.responses.forEach((i) => searchResults.value.push(i));
    visibleRef.value.splice(0, visibleRef.value.length,
        ...response.data.responses.map(() => false));

    bounds = new kakao.maps.LatLngBounds();
    searchResults.value.forEach((markerInfo) => {
      // 배열의 좌표들이 잘 보이게 마커를 지도에 추가합니다

      const lat = markerInfo.mapY;
      const x = markerInfo.mapX;

      const point = new kakao.maps.LatLng(Number(lat), Number(x));

      // LatLngBounds 객체에 좌표를 추가합니다
      bounds.extend(point);
    });

    map.value?.setBounds(bounds);
  } catch (error) {
    console.error('Failed to perform search:', error);
    await router.push({name: 'exception'})
  }
};

// 지역 검색하면 구군 나오게하기 비동기 사용
watch(selectedRegion, async (newRegion) => {
  if (newRegion !== 0) {
    try {
      const response = await axios.get(
          `${import.meta.env.VITE_API_URL}/travel/guguns?areaCode=${newRegion}`);
      const data = response.data.guguns;
      dynamicOptions.value = data.map((district, index) => ({value: index + 1, label: district}));
    } catch (error) {
      console.error('Failed to fetch dynamic options:', error);
      await router.push({name: 'exception'})
    }
  } else {
    dynamicOptions.value = [];
  }
});

const favorites = ref([]);

// 즐겨찾기 추가
const addToFavorites = (result) => {
  if (!favorites.value.find((fav) => fav.contentId === result.contentId)) {
    favorites.value.push(
        {contentId: result.contentId, title: result.title, lat: result.mapY, lng: result.mapX});
  }
};
const markerList = computed(() => {
  return favorites.value.map(i => ({lat: i.lat, lng: i.lng}));
})

// 즐겨찾기 제거
const removeFavorite = (index) => {
  favorites.value.splice(index, 1);
};

const onLoadKakaoMap = (mapRef) => {
  map.value = mapRef;
};

const showModal = ref(false);
const scheduleTitle = ref('');
const scheduleStartDate = ref('');
const scheduleEndDate = ref('');
const scheduleVisibility = ref('public') // 기본값은 '전체 공개'

const handleAddToSchedule = () => {

  console.log(showModal.value)
  showModal.value = true;
  console.log(showModal.value)
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
    await router.push({name: 'exception'})
  }
};

// 디테일
const detailData = ref('');

const showDetailModalWithDetail = async (result) => {
  try {
    const response = await axios.get(
        `${import.meta.env.VITE_API_URL}/travel/detail/${result.contentId}`);

    detailData.value = response.data.detail; // 자세한 정보를 받아옴

  } catch (error) {
    console.error('Failed to fetch detail data:', error);
    await router.push({name: 'exception'})
  }
};


</script>

<template>
  <div class="mt-24 pt-24">

    <div class="w-4/5 mx-auto">
      <form class="flex items-center justify-center" @submit.prevent="search" role="search">
        <div class="flex flex-wrap w-full space-x-2">
          <div class="flex-1">
            <select v-model="selectedRegion" id="search-area"
                    class="form-select w-full p-2 border border-gray-300 rounded-md">
              <option value="0" selected>검색 할 지역 선택</option>
              <option value="1">서울</option>
              <option value="2">인천</option>
              <option value="3">대전</option>
              <option value="4">대구</option>
              <option value="5">광주</option>
              <option value="6">부산</option>
              <option value="7">울산</option>
              <option value="8">세종특별자치시</option>
              <option value="31">경기도</option>
              <option value="32">강원도</option>
              <option value="33">충청북도</option>
              <option value="34">충청남도</option>
              <option value="35">경상북도</option>
              <option value="36">경상남도</option>
              <option value="37">전라북도</option>
              <option value="38">전라남도</option>
              <option value="39">제주도</option>
            </select>
          </div>
          <div class="flex-1">
            <select v-model="selectedDistrict" class="form-select w-full p-2 border border-gray-300 rounded-md">
              <option value="0" selected>도 선택</option>
              <option v-for="option in dynamicOptions" :key="option.value" :value="option.value">
                {{ option.label }}
              </option>
            </select>
          </div>
          <div class="flex-1">
            <select v-model="selectedType" id="search-content-id"
                    class="form-select w-full p-2 border border-gray-300 rounded-md">
              <option value="0" selected>관광지 유형</option>
              <option value="12">관광지</option>
              <option value="14">문화시설</option>
              <option value="15">축제공연행사</option>
              <option value="25">여행코스</option>
              <option value="28">레포츠</option>
              <option value="32">숙박</option>
              <option value="38">쇼핑</option>
              <option value="39">음식점</option>
            </select>
          </div>
          <div class="flex-1">
            <input v-model="keyword" id="search-keyword"
                   class="form-control w-full p-2 border border-gray-300 rounded-md" type="search" placeholder="검색어"
                   aria-label="검색어"/>
          </div>
          <div class="flex-none">
            <button id="btn-search" class="btn btn-outline-success w-full bg-green-500 text-white p-2 rounded-md"
                    type="submit">
              검색
            </button>
          </div>
        </div>
      </form>

      <section class="bg-white border-t py-8 mt-8 flex">
        <div class="w-3/4">
          <KakaoMap :lat="37.566826" :lng="126.9786567" @onLoadKakaoMap="onLoadKakaoMap"
                    width="100%" height="700px" class="map">
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

                  <button @click="showDetailModalWithDetail(result)" class="detail-button">자세히
                    보기
                  </button>

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
            <draggable :list="favorites" group="favorites" @start="onDragStart" @end="onDragEnd"
                       itemKey="id">
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

      {{ showModal }}

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

      <div v-if="detailData" class="mt-6">
        <div class="text-center text-2xl font-semibold mt-4">상세 정보</div>
        <div class="p-4 border border-gray-300 rounded-md">
          <p>{{ detailData }}</p>
        </div>
      </div>


      <div class="mt-6">
        <div class="text-center text-2xl font-semibold mt-4">
          <caption id="searchResult">검색 기록</caption>
        </div>
        <table class="table table-striped mt-4 w-full" v-if="searchResults">
          <thead>
          <tr>
            <th class="p-2 border border-gray-300">대표이미지</th>
            <th class="p-2 border border-gray-300">관광지명</th>
            <th class="p-2 border border-gray-300">주소</th>
          </tr>
          </thead>
          <tbody id="trip-list">
          <tr v-for="result in searchResults" :key="result">
            <td class="p-2 border border-gray-300">
              <img :src="result.firstImage !== '' ? result.firstImage : 'src/assets/no-image.jpg'"
                   alt="Image" style="width: 70%; height: auto;"/>
            </td>
            <td class="p-2 border border-gray-300">{{ result.title }}</td>
            <td class="p-2 border border-gray-300">{{ result.addr1 + result.addr2 }}</td>
          </tr>
          </tbody>
        </table>
        <div v-else class="text-center mt-4">검색 결과가 없습니다.</div>
      </div>
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
