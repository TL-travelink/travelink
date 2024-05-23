import {createRouter, createWebHistory} from "vue-router";
import HomeView from "@/views/HomeView.vue";
import LoginView from "@/views/LoginView.vue";
import BoardView from "@/views/BoardView.vue";
import MapView from "@/views/MapView.vue";
import CreateBoardView from "@/views/CreateBoardView.vue"
import SignUpView from "@/views/SignUpView.vue";
import MyPageView from "@/views/MyPageView.vue";

import {storeToRefs} from "pinia";

import {useMemberStore} from "@/stores/member";
import FollowView from "@/views/FollowView.vue";
import AIView from "@/views/AIView.vue";
import FavoriteListView from "@/views/FavoriteListView.vue";
import FavoriteView from "@/views/FavoriteView.vue";

const onlyAuthUser = async (to, from, next) => {
  const memberStore = useMemberStore();
  const {userInfo, isValidToken} = storeToRefs(memberStore);
  const {getUserInfo} = memberStore;

  let token = sessionStorage.getItem("accessToken");
  if (userInfo.value != null && token) {
    await getUserInfo(token);
  }
  if (!isValidToken.value || userInfo.value === null) {
    console.log("로그인 먼저하자")
    next({name: "login"});
  } else {
    next();
  }
};

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "home",
      beforeEnter: onlyAuthUser,
      component: HomeView,
    },
    {
      path: "/login",
      name: "login",
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: LoginView,
    },
    {
      path: "/board",
      name: "board",
      beforeEnter: onlyAuthUser,
      component: BoardView,
    },
    {
      path: "/map",
      name: "map",
      beforeEnter: onlyAuthUser,
      component: MapView,
    },
    {
      path: "/create-board",
      name: "create-board",
      beforeEnter: onlyAuthUser,
      component: CreateBoardView
    },
    {
      path: "/sign-up",
      name: "sign-up",
      component: SignUpView
    },
    {
      path: "/my-page",
      name: "my-page",
      beforeEnter: onlyAuthUser,
      component: MyPageView
    },
    {
      path: "/exception",
      name: "exception",
      component: () => import("../views/ExceptionView.vue"),
    },
    {
      path: "/board-view/:id",
      name: "board-view",
      beforeEnter: onlyAuthUser,
      component: () => import("../views/BoardDetailView.vue"),
    },
    {
      path: "/follow",
      name: "follow",
      beforeEnter: onlyAuthUser,
      component: FollowView
    },
    {
      path: "/ai",
      name: "ai",
      beforeEnter: onlyAuthUser,
      component: AIView
    },
    {
      path: "/favorite",
      name: "favorite",
      beforeEnter: onlyAuthUser,
      component: FavoriteListView
    },{
      path: "/favorite-view/:id",
      name: "favorite-view",
      beforeEnter: onlyAuthUser,
      component: FavoriteView
    },{
      path: '/:pathMatch(.*)*',
      redirect: "/404"
    }, {
      path: "/404",
      name: "notFound",
      component: () => import("../views/ExceptionView.vue")
    }
  ],
});

export default router;

// key = AIzaSyAs1nvmo88BWaTlmCdr2SxuQiia5FLB20Y