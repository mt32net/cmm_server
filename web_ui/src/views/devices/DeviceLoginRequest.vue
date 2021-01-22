<template>
  <div ref="relBody">
    <div ref="requesterCompContainer" class="requesterCompContainer">
      <requester ref="requesterComp" id="requesterComp"></requester>
    </div>
  </div>
</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component'
// @ is an alias to /src
//@ts-ignore
import Requester from '@/components/devices/Requester.vue'

@Options({
  components: { Requester }
})
export default class DeviceRequester extends Vue {
};
</script>

<style scoped>
</style>