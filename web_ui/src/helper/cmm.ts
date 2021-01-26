import axios from 'axios'
import { Device, Module, User } from '@/helper/cmmObjects'
//@ts-ignore
import { delete_cookie } from '@/helper/cookieHelper'

//deprecated
function getUserByUUID(uuid: String) {

}

export async function getDevices(): Promise<Array<Device>> {
    var devices: Array<Device> = []
    var response = await axios.get("/api/devices/list")
    devices = response.data.deviceList
    return devices
}

export async function geetCurrentUser(): Promise<User> {
    var user = await axios.get("/api/user/currentUser")
    return user as any
}

export async function registerUser(username: String, mail: String): Promise<any> {
    return axios.post("/api/user/register", null, {
        params: {
            username: username,
            mail: mail
        }
    })
}

export async function loginUser(username: String): Promise<any> {
    return axios.post("/api/user/login", null, {
        params: {
            username: username,
        }
    })
}

export async function loginUserWithToken(token: String): Promise<any> {
    return axios.post("/api/user/login", null, { params: { token: token } })
}

export async function verifyUser(token: String) {
    return axios.get("/api/user/verify", { params: { loginSecret: token } })
}

export async function logoutUser() {
    delete_cookie("cmmJWT", "/", "")
    //@ts-ignore
    window.location = "/login"
    window.location.reload(true)
}

export async function addNewDevice() {
    return axios.post("/api/devices/register")
}