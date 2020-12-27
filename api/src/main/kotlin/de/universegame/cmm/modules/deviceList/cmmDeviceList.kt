package de.universegame.cmm.modules.deviceList

import de.universegame.cmm.CMMInfoJackson.auto
import de.universegame.cmm.database.devicesTable
import de.universegame.cmm.modules.deviceInfo.*
import de.universegame.cmm.modules.deviceInfo.getJSONCMMDevice
import org.http4k.core.*
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

fun getJSONCMMDeviceList(): CMMDeviceList {
    var deviceList: MutableList<CMMDevice> = mutableListOf<CMMDevice>()
    transaction {
        devicesTable.selectAll().toList().forEach {
            deviceList.add(getJSONCMMDevice(it))
        }
    }
    return CMMDeviceList(deviceList)
}

fun getJSONCMMDeviceListResponse(request: Request): Response {
    return Response(Status.OK).with(
        Body.auto<CMMDeviceList>().toLens() of getJSONCMMDeviceList()
    )
}