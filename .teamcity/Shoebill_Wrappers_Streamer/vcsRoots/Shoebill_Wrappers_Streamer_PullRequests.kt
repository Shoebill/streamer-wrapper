package Shoebill_Wrappers_Streamer.vcsRoots

import jetbrains.buildServer.configs.kotlin.v10.*
import jetbrains.buildServer.configs.kotlin.v10.vcs.GitVcsRoot

object Shoebill_Wrappers_Streamer_PullRequests : GitVcsRoot({
    uuid = "263c26f0-3946-4a6d-b7c1-73afb523e60e"
    extId = "Shoebill_Wrappers_Streamer_PullRequests"
    name = "Shoebill Streamer Wrapper Pull Requests"
    url = "git@github.com:Shoebill/streamer-wrapper.git"
    pushUrl = "git@github.com:Shoebill/streamer-wrapper.git"
    branch = "master"
    branchSpec = "+:refs/pull/*/merge"
    authMethod = uploadedKey {
        uploadedKey = "TeamCity - Marvin"
    }
})
