package net.gtaun.shoebill.streamer.autoupdate

import java.util.Locale

/**
 * Created by marvin on 18.04.17 in project streamer-wrapper.
 * Copyright (c) 2017 Marvin Haschker. All rights reserved.
 */
object OSCheck {
    /**
     * types of Operating Systems
     */
    enum class OSType {
        Windows, MacOS, Linux, Other
    }

    // cached result of OS detection
    private var detectedOS: OSType? = null

    /**
     * detect the operating system from the os.name System property and cache
     * the result
     * @returns - the operating system detected
     */
    val operatingSystemType: OSType
        get() {
            if (detectedOS == null) {
                val OS = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH)
                if (OS.indexOf("mac") >= 0 || OS.indexOf("darwin") >= 0) {
                    detectedOS = OSType.MacOS
                } else if (OS.indexOf("win") >= 0) {
                    detectedOS = OSType.Windows
                } else if (OS.indexOf("nux") >= 0) {
                    detectedOS = OSType.Linux
                } else {
                    detectedOS = OSType.Other
                }
            }
            return detectedOS!!
        }
}