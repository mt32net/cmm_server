<template>
  <div ref="relBody">
    <div ref="requesterCompContainer" class="requesterCompContainer">
      <requester ref="requesterComp" id="requesterComp"></requester>
    </div>
  </div>
</template>

<script lang="ts">
// @ is an alias to /src
//@ts-ignore
import Requester from '@/components/devices/Requester.vue'

export default {
  name: "DeviceRequest",
  components: { Requester }
};
</script>

<style scoped>
</style>