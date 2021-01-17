import axios from 'axios'

class Device {
    name: String = ""
    deviceUUID: String = ""
    mac: String = ""
    userUUID: String = ""
    verified: Boolean = true
    online: Boolean = false
    os: String = ""
    osShort: String = ""
    modules: Array<Module> = []

}

class Module {
    name: String = ""
    version: String = ""
}

export async function getDevices(): Promise<Array<Device>> {
    var devices: Array<Device> = []
    var response = await axios.get("/api/devices/list")
    devices = response.data.deviceList
    return devices
}