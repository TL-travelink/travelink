import {localAxios} from "@/util/http-commons.js";

const local = localAxios();

async function confirm(param, success, fail){
  await local.post(`${import.meta.env.VITE_API_URL}/login`, param).then(success).catch(fail);
}

async function findById(userid, success, fail){
  local.defaults.headers["Authorization"] = "Bearer " + sessionStorage.getItem("accessToken"); // Bearer <- token naming convention
  await local.get(`${import.meta.env.VITE_API_URL}/member`).then(success).catch(fail);
}

async function tokenRegeneration(user, success, fail) {
  local.defaults.headers["refreshToken"] = sessionStorage.getItem("refreshToken"); //axios header에 refresh-token 셋팅
  await local.post(`${import.meta.env.VITE_API_URL}/refresh`, user).then(success).catch(fail);
}

async function logout(userid, success, fail){
  await local.get(`${import.meta.env.VITE_API_URL}/logout`).then(success).catch(fail);
}


export {confirm, tokenRegeneration,findById, logout};