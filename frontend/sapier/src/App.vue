<script setup lang="ts">
// https://github.com/vueuse/head
// you can use this to manipulate the document head in any components,

// they will be rendered correctly in the html results with vite-ssg
useHead({
  title: 'sapier',
  meta: [
    { name: 'description', content: 'Opinionated Vite Starter Template' },
    {
      name: 'theme-color',
      content: () => isDark.value ? '#00aba9' : '#ffffff',
    },
  ],
  // link: [
  //   {
  //     rel: 'icon',
  //     type: 'image/svg+xml',
  //     href: () => preferredDark.value ? '/favicon-dark.svg' : '/favicon.svg',
  //   },
  // ],
})
</script>

<template>
  <RouterView />
</template>

<style>
:root {

  --font-H0-size: 96px;
  --font-H0-weight: 800;
  --font-H1-size: 32px;
  --font-H1-weight: 800;
  --font-H2-size: 24px;
  --font-H2-weight: 700;
  --font-H3-size: 24px;
  --font-H3-weight: 400;
  --font-H4-size: 20px;
  --font-H4-weight: 400;
  --font-H5-size: 16px;
  --font-H5-weight: 400;
  --font-H6-size: 16px;
  --font-H6-weight: 700;

  --color-blue1: #0F4C81; /* Classic Blue */
  --color-blue1-hover: #0c4577; /* Classic Blue Hover*/
  --color-blue1-active: #0a375f; /* Classic Blue Active*/
  --color-blue2: #658DC6; /* PrOvence */
  --color-blue3: #B5C7D3; /* Baby Blue */

  /* --color-white: White; White */
  --color-gray1: #F0F0F0; /* White Gray */
  --color-gray1-hover: #dedede; /* White Gray Hover*/
  --color-gray1-active: #cfcfcf; /* White Gray Active*/
  --color-gray2: #C9C9C9; /* Lite Gray */
  --color-gray3: #B6B6B6; /* Gray */
  --color-gray4: #838383; /* Dark Gray */
  --color-black: #2E2E2E; /* Black */

  --color-DELETE: #E64F47; /* DEL */
  --color-PATCH: #EFA44A; /* PATCH */
  --color-POST: #6EC465; /* POST */
  --color-GET: #44A1F8; /* GET */
  --color-PUT: #21DEB1; /* PUT */
  --color-OPTIONS: #C98CFF; /* POT */
  --color-HEAD: #FFA6DE; /* HEAD */

  color:var(--color-black);

  user-select: none;
}
::-webkit-scrollbar {
  display: none;
}
</style>
