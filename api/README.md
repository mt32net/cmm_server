# CMM -API

This REST API written in Kotlin will provide all needed details for the Web UI and will be used as pc endpoint as well.
The api can be accessed via relatively simple HTTP requests.

List of all HTTP requests (all GET data is formatted in JSON):

path | HTTP type | description | example return body
--- | --- | --- | ---
`/api/info` | GET | a brief summary of all important information for the clients  consisting of version, type (Kotlin, HTTP-only), supported commands  | ```{ "version":"1.0", "type":"kotlin", "cmds":["info", "devicelist"]}```
`/api/devicelist` | GET | get a list of all registered devices and the modules that they have installed, devices returned in an array with their uuid, name and installed modules | ```{"devices":[{"name":"ubuntu-live-desktop", "uuid":"18908j-dhfz7430jkk", "modules":["notifier"]}]```
`/api/deviceInfo/?uuid=<value>` | GET | Gets the devices info, modules from that machine | ```{"uuid":"asdhuiq34-234h", name="ubuntu-live-desktop", "modules":["notifier","monitor"]}```
`/api/notifier/?uuid=<value>` | GET | get all open notifications for that machine, if module is installed. Each notification consists of a title, an icon-type (base64 encoded or an url), the icon data and the notification itself | ```{"uuid":"alkhjg4-sdfgj", "name":"ubuntu-live-desktop", "installed":true, "notifications":[{"title":"Test", "iconType":"url", "icon":"...", "content":"abc"}]}```
`/api/monitor/?uuid=<value>` | GET | gets the monitoring data if module is installed. The monitoring data consists of the cpu usage, ram usage, drive spaces, network usage and, if enabled even the first couple of processes that uses the most cpu power and ram. The network activity is split into download/upload | ```{"uuid":"alkhjg4-sdfgj", "name":"ubuntu-live-desktop", "installed":true, "monitoring":{"cpu":"29%", "ram":5/8GB", "network-usage":"50kb/20kb", "processes":[{"title":"Firefox", "cpu":"10%", "ram":"500Mb"}, {"title":"Geekbench", "cpu":"90%", "ram":"100Mb"}]}```
`/api/uuid/?clientsecret=<value>` | GET | this will resend the uuid for the machine with that clientsecret, using this method is not recommended | ```{"uuid":"your 'forgotten' uuid"}```
`/api/register/?name=<name>` | POST | registers a new machine to the system, returns a client secret and a uuid (IMPORTANT: client secret will only be sent this one time, if your`re using this api on your own, store it somewhere "secure" together with the uuid, this replaces a login with username and password) | ```{"uuid":"ankfhglkajh1-qsflkhjh34iuh", "clientsecret":"here will be a twohundred (200) character long string wich will only be sent this one time}```
`/api/monitor/?uuid=<uuid>[cpuUsage, ramUsage, installedRam, upload, download]` | POST | queries in `[]` are optional network activity (`upload`/`download`) is in kb/s, `cpuUsage`in percent, `ramUsage` in Gb as well as `installedRam` | no body returned