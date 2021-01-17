<template>
  <div>
    <NotificationInfo
      deviceName="SurfaceTom"
      operatingSystemShort="Win"
      lastUpdated="12/01/2021 00:30"
      notificationsAvialable="5"
    />
  </div>
</template>

<script lang="ts">
//@ts-ignore
import NotificationInfo from "@/components/devices/NotificationInfo.vue"

export default {
  name: "Notifications_Info",
  components: { NotificationInfo },
}
</script>

<style scoped>
</style>