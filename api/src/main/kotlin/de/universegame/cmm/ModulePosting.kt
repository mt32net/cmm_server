package de.universegame.cmm

import de.universegame.cmm.database.devicesTable
import de.universegame.cmm.database.monitorTable
import org.http4k.core.Status
import org.http4k.core.Status.Companion.OK
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

fun updateMonitor(
    uuid: String?,
    cpuUsage: String?,
    ramUsage: String?,
    installedRam: String?,
    networkUploadKbs: String?,
    networkDownloadKbs: String?
): Status {
    if (uuid == null) return Status.PRECONDITION_FAILED
    var returnStatus = OK
    transaction {
        if (monitorTable.select { monitorTable.uuid eq uuid }.count() == 0L) {
            returnStatus = Status.NOT_FOUND
        } else {
            monitorTable.update({ monitorTable.uuid eq uuid }) {
                if (cpuUsage != null) it[monitorTable.cpuUsagePercent] = cpuUsage.toDouble()
                if(ramUsage != null) it[monitorTable.ramUsage] = ramUsage.toDouble()
                if(installedRam != null) it[monitorTable.installedRam] = installedRam.toInt()
                if(networkUploadKbs != null) it[monitorTable.networkUploadKbs] = networkUploadKbs.toDouble()
                if(networkDownloadKbs != null) it[monitorTable.networkDownloadKbs] = networkDownloadKbs.toDouble()
            }
            devicesTable.update({ devicesTable.uuid eq uuid }) {
                it[devicesTable.online] = true
            }
        }
    }
    return returnStatus
}