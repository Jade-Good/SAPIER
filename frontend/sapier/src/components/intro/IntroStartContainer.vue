<template>
  <div class="intro_start">
    <div class="blank_space_top" />
    <IntroStartContent />
    <div class="blank_space_bottom" />
    <IntroStartButton />
    <div class="blank_space_bottom" />
  </div>
</template>

<style scoped>
.intro_start{
    display: flex;
    flex-direction: column;
    justify-content: space-around;

    background-color: #F0F0F0;
}

.blank_space_top{
    margin-top: 3%;
}

.blank_space_bottom{
    margin-bottom: 3%;
}
</style>
