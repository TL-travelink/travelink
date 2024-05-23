// import {useRouter} from "vue-router";
import {jwtDecode} from "jwt-decode";
import { ref } from "vue";
import { defineStore } from "pinia";
import {confirm, tokenRegeneration,findById, logout} from "@/api/member.js";
import Swal from "sweetalert2";


export const useMemberStore = defineStore("memberStore", () => {
  // const isLogin = ref
  const isLogin = ref(false)
  const isLoginError = ref(false)
  const userInfo = ref(null)
  const isValidToken = ref(false)

  const userLogin = async (userInfo) => {
    await confirm(userInfo, (res) => {
      // res.status == httpStatusCode.CREATE
      let { data } = res;
      console.log(data);
      let accessToken = data["accessToken"]
      let refreshToken = data["refreshToken"]

      isLogin.value = true;
      isLoginError.value = false;
      isValidToken.value = true;
      sessionStorage.setItem("accessToken", accessToken)
      sessionStorage.setItem("refreshToken", refreshToken)
      Swal.fire({
                icon: 'success',
                title: '로그인 성공',
                showConfirmButton: true
            })
      // token 2개
    },
    (error) => {
        console.log("login fail")
        isLogin.value = false;
        isLoginError.value = true;
        isValidToken.value = false;
        console.log(error)
        Swal.fire({
            icon: 'warning',
            title: '로그인 실패',
            text: '회원정보를 확인해주세요',
            showConfirmButton: true
        })
      }
    )
  }

  const getUserInfo = async (token) => {
    let decodeToken = jwtDecode(token)
    await findById(
        decodeToken.userId,
        (res) => {
          userInfo.value = res.data
        },
        async (error) => {
          console.log("토근 만료")
          console.log(error.response.status)
          console.log(error.response.statusText)
          isValidToken.value = false

          await tokenRegenerate()
        }
    )
  }

  const tokenRegenerate = async () => {
    await tokenRegeneration(
        JSON.stringify(userInfo.value),
        (res) => {
          let accessToken = res.data["accessToken"]
          sessionStorage.setItem("accessToken", accessToken)
          isValidToken.value = true;
        },
        async (error) => {
          //error.response.status 비교
          await logout(
              userInfo.value.id,
              (res) => {
                //res.status 비교
                isLogin.value = false;
                userInfo.value = null;
                isValidToken.value = false;
              },
              (error) => {
                console.error(error)
                isLogin.value = false
                userInfo.value = null
              }
          )
        }
        )
  }

  const userLogout = async () => {
    console.log("logout" + userInfo.value)
    await logout(
        userInfo.value.userId,
        (res) => {
          //res.status === httpStatusCode.OK
          isLogin.value = false
          userInfo.value = null
          isValidToken.value = false

          sessionStorage.removeItem("accessToken")
          sessionStorage.removeItem("refreshToken")
        },(error) => {
          console.log(error)
        }
    )
  }
  return {
    isLogin, isLoginError, userInfo, isValidToken, userLogin, getUserInfo, tokenRegenerate, userLogout
  }
})