package Shoebill_Wrappers_Streamer

import Shoebill_Wrappers_Streamer.buildTypes.*
import Shoebill_Wrappers_Streamer.vcsRoots.*
import Shoebill_Wrappers_Streamer.vcsRoots.Vcs_Shoebill_Wrappers_Streamer
import jetbrains.buildServer.configs.kotlin.v10.*
import jetbrains.buildServer.configs.kotlin.v10.Project
import jetbrains.buildServer.configs.kotlin.v10.projectFeatures.VersionedSettings
import jetbrains.buildServer.configs.kotlin.v10.projectFeatures.VersionedSettings.*
import jetbrains.buildServer.configs.kotlin.v10.projectFeatures.versionedSettings

object Project : Project({
    uuid = "2d0a4a87-d00a-4c99-bac1-7fa925bb12d3"
    extId = "Shoebill_Wrappers_Streamer"
    parentId = "Shoebill_Wrappers"
    name = "Streamer"
    description = "Incognito's Streamer Wrapper for the Shoebill Project."

    vcsRoot(Vcs_Shoebill_Wrappers_Streamer)
    vcsRoot(Shoebill_Wrappers_Streamer_PullRequests)

    buildType(Shoebill_Wrappers_Streamer_Deploy)
    buildType(Shoebill_Wrappers_Streamer_TestBuild)

    features {
        versionedSettings {
            id = "PROJECT_EXT_2"
            mode = VersionedSettings.Mode.ENABLED
            buildSettingsMode = VersionedSettings.BuildSettingsMode.USE_CURRENT_SETTINGS
            rootExtId = Vcs_Shoebill_Wrappers_Streamer.extId
            showChanges = false
            settingsFormat = VersionedSettings.Format.KOTLIN
        }
    }
})
