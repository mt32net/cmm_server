<template>
  <router-link :to="moduleURL(module.name.toLowerCase())" class="module">
    <!--info icon-->
    <svgIcon
      v-if="iconSelector == 0"
      viewPort="0 0 24 24"
      :width="size"
      :height="size"
      :iconColor="color"
    >
      <path
        d="M12 2C6.486 2 2 6.486 2 12s4.486 10 10 10 10-4.486 10-10S17.514 2 12 2zm0 18c-4.411 0-8-3.589-8-8s3.589-8 8-8 8 3.589 8 8-3.589 8-8 8z"
      ></path>
      <path d="M11 11h2v6h-2zm0-4h2v2h-2z"></path>
    </svgIcon>

    <!--notification icon-->
    <svgIcon
      v-if="iconSelector == 1"
      viewPort="0 0 24 24"
      :width="size"
      :height="size"
      :iconColor="color"
    >
      <circle cx="18" cy="6" r="3" />
      <path
        d="M18 19H5V6h8c0-.712.153-1.387.422-2H5c-1.103 0-2 .897-2 2v13c0 1.103.897 2 2 2h13c1.103 0 2-.897 2-2v-8.422A4.962 4.962 0 0118 11v8z"
      />
    </svgIcon>
  </router-link>
</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component'
//@ts-ignore
import svgIcon from '@/components/svgIcon.vue'



@Options({
  name: 'ModuleListElement',
  components: { svgIcon },
  props: {
    module: { name: "", version: "" }
  }
})
export default class DeviceListElement extends Vue {

  size = "2rem"
  color = ""
  module: any = { name: "", version: "" }

  moduleURL(module: String) {
    //@ts-ignore
    return "/devices/" + this.$route.params.uuid + "/" + module
  }

  get iconSelector() {
    switch ((this.module.name as String).toLowerCase()) {
      case "info":
        return 0
      case "notifications":
        return 1

    }
    return 0
  }

}
</script>

<style scoped>
.module {
  font-weight: bold;
  color: #b1b1b1;
  text-decoration: none;
  margin: 1rem;
  text-align: center;
  fill: #bdbdbd;
}

.router-link-exact-active.module {
  color: #ffffff;
  fill: #ffffff;
}
</style>