export class Device {
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

export class Module {
    name: String = ""
    version: String = ""
}

export class User {
    username: String = ""
    mail: String = ""
    uuid: String = ""
    verified: Boolean = false
    admin: Boolean = false
}
