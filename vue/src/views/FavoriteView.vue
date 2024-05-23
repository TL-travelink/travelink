<script setup>
import ButtonComponent from "@/components/ButtonComponent.vue";
import { onBeforeMount, ref, watch } from "vue";
import axios from "axios";
import { useRoute } from "vue-router";
import {
  KakaoMap,
  KakaoMapCustomOverlay,
  KakaoMapMarker,
  KakaoMapMarkerPolyline
} from 'vue3-kakao-maps';
import router from "@/router/index.js";

const route = useRoute();
const favorite = ref(null);
const map = ref(null);

const markerList = ref([]);
const visibleRef = ref([]);
const polylinePath = ref([]);

const loadBoard = async () => {
  try {
    console.log(route.params.id);
    const response = await axios.get(
        `${import.meta.env.VITE_API_URL}/travel/favorite/info/${route.params.id}`,
        {
          headers: {
            Authorization: `Bearer ${sessionStorage.getItem('accessToken')}`
          }
        }
    );

    favorite.value = response.data;
    console.log(favorite.value);

    if (favorite.value && favorite.value.travels) {
      markerList.value = favorite.value.travels.map((travel, index) => ({
        lat: travel.mapY,
        lng: travel.mapX,
        title: travel.title,
        addr1: travel.addr1,
        addr2: travel.addr2,
        index: index,
        firstImage: travel.firstImage // 이미지 추가
      }));

      // 마커 사이에 선을 그릴 경로 추가
      polylinePath.value = markerList.value.map(marker => ({
        lat: marker.lat,
        lng: marker.lng
      }));

      visibleRef.value = new Array(favorite.value.travels.length).fill(false);

      if (map.value) {
        const bounds = new kakao.maps.LatLngBounds();
        markerList.value.forEach(marker => {
          bounds.extend(new kakao.maps.LatLng(marker.lat, marker.lng));
        });
        map.value.setBounds(bounds);
      }
    }
  } catch (error) {
    console.error('Failed to fetch board data:', error);
    await router.push({ name: 'exception'})
  }
};

const onLoadKakaoMap = (mapRef) => {
  map.value = mapRef;
  if (markerList.value.length > 0) {
    const bounds = new kakao.maps.LatLngBounds();
    markerList.value.forEach(marker => {
      bounds.extend(new kakao.maps.LatLng(marker.lat, marker.lng));
    });
    map.value.setBounds(bounds);
  }
};

const onClickKakaoMapMarker = (index) => {
  visibleRef.value = visibleRef.value.map((visible, i) => (i === index ? !visible : false));
};

onBeforeMount(() => {
  loadBoard();
});

watch(favorite, (newFavorite) => {
  if (newFavorite && newFavorite.travels) {
    markerList.value = newFavorite.travels.map((travel, index) => ({
      lat: travel.mapY,
      lng: travel.mapX,
      title: travel.title,
      addr1: travel.addr1,
      addr2: travel.addr2,
      index: index,
      firstImage: travel.firstImage // 이미지 추가
    }));



    // 마커 사이에 선을 그릴 경로 추가
    polylinePath.value = markerList.value.map(marker => ({
      lat: marker.lat,
      lng: marker.lng,
    }));

    visibleRef.value = new Array(newFavorite.travels.length).fill(false);

    if (map.value) {
      const bounds = new kakao.maps.LatLngBounds();
      markerList.value.forEach(marker => {
        bounds.extend(new kakao.maps.LatLng(marker.lat, marker.lng));
      });
      map.value.setBounds(bounds);
    }
  }
});

</script>

<template>
  <div class="container mx-auto pt-24 mt-24">
    <section class="bg-white border-b py-8 shadow-lg rounded-lg">
      <div class="container mx-auto flex flex-wrap pt-4 pb-12">
        <h2 class="w-full my-2 text-5xl font-bold leading-tight text-center text-gray-800">즐겨찾기</h2>
        <div class="w-full mb-4">
          <div class="h-1 mx-auto gradient w-64 opacity-25 my-0 py-0 rounded-t"></div>
        </div>
        <div class="w-full bg-gray-100 p-6 rounded-lg shadow-md">
          <div class="w-full title mb-4 text-center text-3xl font-semibold text-gray-700">
            {{ favorite?.title }}
          </div>

          <div class="text-center my-4">
            <p class="text-lg text-gray-600">작성자: {{ favorite?.memberId }}</p>
            <p class="text-lg text-gray-600">시작일: {{
                new Date(favorite?.startAt).toLocaleDateString()
              }}</p>
            <p class="text-lg text-gray-600">종료일: {{
                new Date(favorite?.endAt).toLocaleDateString()
              }}</p>
          </div>

          <div class="w-full">
            <KakaoMap :lat="37.566826" :lng="126.9786567" @onLoadKakaoMap="onLoadKakaoMap"
                      width="100%" height="500px" class="map">
              <!-- 선 추가 -->
              <KakaoMapMarkerPolyline :markerList="polylinePath" :strokeWeight="5" :strokeColor="'#FF0000'" :strokeOpacity="0.7" :endArrow="true"/>
              <!-- 마커와 오버레이 부분 -->
              <template v-for="(marker, index) in markerList" :key="index">
                <KakaoMapMarker :lat="marker.lat" :lng="marker.lng" :clickable="true"
                                @onClickKakaoMapMarker="() => onClickKakaoMapMarker(index)"/>
                <KakaoMapCustomOverlay
                    :lat="marker.lat"
                    :lng="marker.lng"
                    :yAnchor="1.4"
                    :visible="visibleRef[index]"
                >
                  <div class="info-window">
                    <div class="place-name text-ellipsis">{{ marker.title }}</div>
                    <div class="address text-ellipsis">{{ marker.addr1 }} {{ marker.addr2 }}</div>
                    <!-- 이미지 표시 -->
                    <img :src="marker.firstImage" alt="Image" style="width: 100%; height: auto;">
                    <button @click="visibleRef[index] = false" class="close-button">닫기</button>
                  </div>
                </KakaoMapCustomOverlay>
              </template>
            </KakaoMap>
          </div>
          <div class="mt-6">
            <h3 class="text-2xl font-semibold text-gray-700 mb-4">여행 목록</h3>
            <div v-for="travel in favorite?.travels" :key="travel.contentId"
                 class="mb-4 p-4 bg-white rounded-lg shadow">
              <h4 class="text-xl font-bold text-gray-800">{{ travel.title }}</h4>
              <p class="text-gray-600">{{ travel.addr1 }} {{ travel.addr2 }}</p>
            </div>
          </div>

          <div class="flex items-center justify-center mt-4 space-x-4">
            <button-component>
              <router-link :to="{ name: 'favorite' }">뒤로가기</router-link>
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

.info-window {
  position: relative;
  padding: 10px;
  background: white;
  border: 1px solid #ccc;
  border-radius: 10px;
  box-shadow: 0px 0px 5px rgba(0,0,0,0.2);
  width: 200px;
}

.place-name {
  font-size: 16px;
  font-weight: bold;
}

.address {
  font-size: 14px;
  color: #555;
}

.close-button {
  position: absolute;
  top: 5px;
  right: 5px;
  background: none;
  border: none;
  font-size: 16px;
  cursor: pointer;
}
.text-ellipsis {
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

</style>
