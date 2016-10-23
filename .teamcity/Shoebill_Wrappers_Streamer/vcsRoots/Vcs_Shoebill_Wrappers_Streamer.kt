package Shoebill_Wrappers_Streamer.vcsRoots

import jetbrains.buildServer.configs.kotlin.v10.*
import jetbrains.buildServer.configs.kotlin.v10.vcs.GitVcsRoot

object Vcs_Shoebill_Wrappers_Streamer : GitVcsRoot({
    uuid = "32046145-278e-4043-9a3e-4c9c85f037b5"
    extId = "Shoebill_Wrappers_Streamer"
    name = "Shoebill Streamer Wrapper"
    url = "git@github.com:Shoebill/streamer-wrapper.git"
    pushUrl = "git@github.com:Shoebill/streamer-wrapper.git"
    branch = "master"
    branchSpec = "+:refs/heads/(master)"
    userNameStyle = GitVcsRoot.UserNameStyle.NAME
    authMethod = uploadedKey {
        uploadedKey = "TeamCity - Marvin"
    }
})
