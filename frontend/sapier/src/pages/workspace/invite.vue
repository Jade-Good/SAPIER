<script setup>
const user = useUserStore()
const isMounted = useMounted()
const route = useRouter()
const axios = inject('$axios')

if (isMounted) {
  console.log('invite 페이지')
  if (!user.userInfo) {
    // 사용자 데이터가 없으면 로그인 페이지로 리다이렉팅
    route.push('/login/oauth')
  }
  else {
    // 사용자 데이터가 있으면 쿼리 파라미터에서 key 값 받아오기
    const key = route.currentRoute.value.query.key
    console.log(key)

    // key 값이 있다면 Axios를 사용하여 POST 요청
    if (key) {
      axios.post(`/api/v1/workspaces/${key}`)
        .then((response) => {
          // 성공적으로 처리된 경우, 추가적인 로직을 수행하거나 리다이렉팅
          route.push('/main')
        })
        .catch((error) => {
          console.error('Error adding member:', error)
          // 실패한 경우 에러 처리 로직 추가
        })
    }
    else {
      console.error('Key not found in query parameters')
      // key 값이 없는 경우 에러 처리 로직 추가
    }
  }
}
</script>

<template>
  <TheNav />
  <div>빙글빙글</div>
</template>

<style>
.mid {
  height: calc(100% - 70px);
}
</style>
