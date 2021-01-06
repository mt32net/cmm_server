package de.universegame.cmm.modules.monitor

import de.universegame.cmm.database.devicesTable
import de.universegame.cmm.database.monitorTable
import de.universegame.cmm.log
import org.http4k.core.Status
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

data class MachineState(
    val uuid: String,
    val cpuUsagePercent: Number,
    val ramUsage: Number,
    val installedRam: Number,
    val networkUploadKb: Number,
    val networkDownloadKb: Number
)

fun updateMonitor(
    uuid: String?,
    cpuUsage: String?,
    ramUsage: String?,
    installedRam: String?,
    networkUploadKbs: String?,
    networkDownloadKbs: String?
): Status {
    if (uuid == null) return Status.PRECONDITION_FAILED
    var returnStatus = Status.OK
    try {
        transaction {
            if (monitorTable.select { monitorTable.uuid eq uuid }.count() == 0L) {
                returnStatus = Status.NOT_FOUND
            } else {
                monitorTable.update({ monitorTable.uuid eq uuid }) {
                    if (cpuUsage != null) it[cpuUsagePercent] = cpuUsage.toDouble()
                    if (ramUsage != null) it[monitorTable.ramUsage] = ramUsage.toDouble()
                    if (installedRam != null) it[monitorTable.installedRam] = installedRam.toInt()
                    if (networkUploadKbs != null) it[monitorTable.networkUploadKbs] = networkUploadKbs.toDouble()
                    if (networkDownloadKbs != null) it[monitorTable.networkDownloadKbs] = networkDownloadKbs.toDouble()
                }
                devicesTable.update({ devicesTable.uuid eq uuid }) {
                    it[online] = true
                }
            }
        }
    } catch (e: Exception) {
        log(e.stackTraceToString())
        return Status.BAD_REQUEST
    }
    return returnStatus
}