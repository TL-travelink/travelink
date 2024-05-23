<script setup>
import {RouterLink} from "vue-router";
import { useMemberStore} from "@/stores/member.js"
import {storeToRefs} from "pinia";

const memberStore = useMemberStore()

const {isLogin, isLoginError} = storeToRefs(memberStore)

const {userLogout} = memberStore;

const logout = () => {
  userLogout();
}
</script>

<template>
  <!--Nav-->
  <nav id="header" class="fixed w-full z-30 top-0 text-white">
    <div class="w-full container mx-auto flex flex-wrap items-center justify-between mt-0 py-2">
      <div class="pl-4 flex items-center">
        <a
          class="toggleColour text-white no-underline hover:no-underline font-bold text-2xl lg:text-4xl"
          href="#"
        >
          <img src="@/assets/image%20(1).png" class="h-8 fill-current inline logo" />
        </a>
      </div>
      <div class="block lg:hidden pr-4">
        <button
          id="nav-toggle"
          class="flex items-center p-1 text-pink-800 hover:text-gray-900 focus:outline-none focus:shadow-outline transform transition hover:scale-105 duration-300 ease-in-out"
        >
          <svg class="fill-current h-6 w-6" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
            <title>Menu</title>
            <path d="M0 3h20v2H0V3zm0 6h20v2H0V9zm0 6h20v2H0v-2z" />
          </svg>
        </button>
      </div>
      <div
        class="w-full flex-grow lg:flex lg:items-center lg:w-auto hidden mt-2 lg:mt-0 bg-white lg:bg-transparent text-black p-4 lg:p-0 z-20"
        id="nav-content"
      >
        <ul class="list-reset lg:flex justify-end flex-1 items-center">
          <li class="mr-3">
            <RouterLink :to="{name : 'home'}"
                        class="inline-block text-black no-underline hover:text-gray-800 hover:text-underline py-2 px-4"
            >home</RouterLink
            >
          </li>
          <li class="mr-3">
            <RouterLink :to="{name : 'follow'}"
              class="inline-block text-black no-underline hover:text-gray-800 hover:text-underline py-2 px-4"
              >friends</RouterLink
            >
          </li>
          <li class="mr-3">
            <RouterLink :to="{name:'board'}"
              class="inline-block text-black no-underline hover:text-gray-800 hover:text-underline py-2 px-4"
              >board</RouterLink
            >
          </li>
          <li class="mr-3">
            <RouterLink :to="{name:'map'}"
                        class="inline-block text-black no-underline hover:text-gray-800 hover:text-underline py-2 px-4"
            >map</RouterLink
            >
          </li>
          <li class="mr-3">
            <RouterLink :to="{name:'ai'}"
                        class="inline-block text-black no-underline hover:text-gray-800 hover:text-underline py-2 px-4"
            >ai recommend</RouterLink
            >
          </li>
          <li class="mr-3" v-if="isLogin">
            <RouterLink :to="{name:'my-page'}"
                        class="inline-block text-black no-underline hover:text-gray-800 hover:text-underline py-2 px-4"
            >my page
            </RouterLink>
          </li>
          <li class="mr-3" v-if="isLogin">
            <RouterLink :to="{name:'favorite'}"
                        class="inline-block text-black no-underline hover:text-gray-800 hover:text-underline py-2 px-4"
            >Favorite
            </RouterLink>
          </li>
        </ul>
        <button
          id="navAction"
          class="mx-auto lg:mx-0 hover:underline bg-white text-gray-800 font-bold rounded-full mt-4 lg:mt-0 py-4 px-8 shadow opacity-75 focus:outline-none focus:shadow-outline transform transition hover:scale-105 duration-300 ease-in-out"
        >
          <RouterLink v-if="!isLogin" :to="{ name: 'login' }">Login</RouterLink>
          <RouterLink v-if="isLogin" to="/" @click.prevent="logout">Logout</RouterLink>
        </button>
      </div>
    </div>
    <hr class="border-b border-gray-100 opacity-25 my-0 py-0" />
  </nav>

</template>

<style scoped>
header {
  line-height: 1.5;
  max-height: 100vh;
}

.logo {
  display: block;
  margin: 0 auto 2rem;
}

nav {
  width: 100%;
  font-size: 12px;
  text-align: center;
  margin-top: 2rem;
}

nav a.router-link-exact-active {
  color: var(--color-text);
}

nav a.router-link-exact-active:hover {
  background-color: transparent;
}

nav a {
  display: inline-block;
  padding: 0 1rem;
  border-left: 1px solid var(--color-border);
}

nav a:first-of-type {
  border: 0;
}

@media (min-width: 1024px) {
  header {
    display: flex;
    place-items: center;
    padding-right: calc(var(--section-gap) / 2);
  }

  .logo {
    width: 150px;
    height: 100px;
  }

  header .wrapper {
    display: flex;
    place-items: flex-start;
    flex-wrap: wrap;
  }

  nav {
    text-align: left;
    margin-left: -1rem;
    font-size: 1rem;

    padding: 1rem 0;
    margin-top: 1rem;
  }
}
</style>
