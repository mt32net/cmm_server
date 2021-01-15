<template>
  <div ref="relBody">
    <div ref="spacer"></div>
    <div ref="requesterCompContainer" class="requesterCompContainer">
      <requester ref="requesterComp" id="requesterComp"></requester>
    </div>
  </div>
</template>

<script lang="ts">
// @ is an alias to /src
//@ts-ignore
import Requester from '@/components/devices/Requester.vue'

var spacer: any
var requester: any

export default {
  name: "DeviceRequest",
  components: { Requester },
  methods: {
    updateSpacerHeight() {
      var windowHeight = window.innerHeight
      //@ts-ignore
      var requesterHeight = this.requester.$el.clientHeight
      //@ts-ignore
      this.spacer.style.height = (windowHeight - requesterHeight) / 2 + "px"
    }
  },
  mounted() {
    this.$nextTick(function () {
      this.spacer = this.$refs.spacer as Element
      this.requester = this.$refs.requesterComp as Element
      window.addEventListener('resize', this.updateSpacerHeight)
      this.updateSpacerHeight()
    })
  },
  data(): {
    spacer: any,
    requester: any
  } {
    return {
      spacer: this.$refs.spacer as Element,
      requester: this.$refs.requesterComp as Element
    }
  }
};
</script>

<style scoped>
</style>