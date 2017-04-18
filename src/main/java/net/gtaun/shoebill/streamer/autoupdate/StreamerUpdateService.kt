package net.gtaun.shoebill.streamer.autoupdate

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream
import net.gtaun.shoebill.streamer.Streamer
import org.apache.commons.codec.digest.DigestUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.File

/**
 * Created by marvin on 14.04.17 in project streamer-wrapper.
 * Copyright (c) 2017 Marvin Haschker. All rights reserved.
 */
internal object StreamerUpdateService {

    val LOGGER: Logger = LoggerFactory.getLogger("Streamer Update Service")

    fun autoInstall(): Boolean {
        val binary = getBinaryPlugin()

        val oldPluginFile = let {
            when (OSCheck.operatingSystemType) {
                OSCheck.OSType.Windows -> {
                    File("plugins", "streamer.dll")
                }
                OSCheck.OSType.Linux -> {
                    File("plugins", "streamer.so")
                }
                else -> {
                    throw Exception("Operating system is not supported.")
                }
            }
        }

        if (oldPluginFile.exists()) {
            val oldMd5 = oldPluginFile.inputStream().use { DigestUtils.md5Hex(it) }
            val newMd5 = DigestUtils.md5Hex(binary)
            if (oldMd5 == newMd5) {
                LOGGER.info("Plugins are identical.")
                return true
            } else {
                LOGGER.info("MD5 hashes mismatch (old $oldMd5 to new $newMd5), deleting old plugin...")
                val delete = oldPluginFile.delete()
                if (delete)
                    LOGGER.info("Successfully deleted ${oldPluginFile.name}.")
                else {
                    LOGGER.error("Could not remove ${oldPluginFile.name} to update.")
                    return false
                }
            }
        }

        oldPluginFile.outputStream().use {
            it.write(binary)
            LOGGER.info("Successfully updated the streamer-plugin to version ${Streamer.get().description.version}.")
        }
        return true
    }

    internal fun getBinaryPlugin(): ByteArray {
        val classLoader = javaClass.classLoader
        val fileName = "binaries/".plus(let {
            when (OSCheck.operatingSystemType) {
                OSCheck.OSType.Windows -> {
                    "streamer.dll"
                }
                OSCheck.OSType.Linux -> {
                    "streamer.so"
                }
                else -> {
                    throw Exception("Operating system is not supported.")
                }
            }
        })

        val inputStream = classLoader.getResourceAsStream(fileName)
        val outputStream = ByteOutputStream()

        outputStream.write(inputStream)
        return outputStream.bytes
    }

}