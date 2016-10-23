package Shoebill_Wrappers_Streamer.buildTypes

import Shoebill_Wrappers_Streamer.vcsRoots.Shoebill_Wrappers_Streamer_PullRequests
import jetbrains.buildServer.configs.kotlin.v10.*
import jetbrains.buildServer.configs.kotlin.v10.buildFeatures.CommitStatusPublisher
import jetbrains.buildServer.configs.kotlin.v10.buildFeatures.CommitStatusPublisher.*
import jetbrains.buildServer.configs.kotlin.v10.buildFeatures.commitStatusPublisher
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.MavenBuildStep
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.MavenBuildStep.*
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.maven
import jetbrains.buildServer.configs.kotlin.v10.triggers.VcsTrigger
import jetbrains.buildServer.configs.kotlin.v10.triggers.VcsTrigger.*
import jetbrains.buildServer.configs.kotlin.v10.triggers.vcs

object Shoebill_Wrappers_Streamer_TestBuild : BuildType({
    uuid = "0c9c5d3a-c668-4688-a1f2-e8fc2e3484a6"
    extId = "Shoebill_Wrappers_Streamer_TestBuild"
    name = "Test Build"
    description = "Tests if the changes will compile successfully."


    vcs {
        root(Shoebill_Wrappers_Streamer.vcsRoots.Shoebill_Wrappers_Streamer_PullRequests)

        checkoutMode = CheckoutMode.ON_SERVER
    }

    steps {
        maven {
            goals = "clean test"
            userSettingsPath = ""
            param("maven.home", "")
            param("target.jdk.home", "%env.JDK_18%")
        }
    }

    triggers {
        vcs {
            branchFilter = "+:refs/pull/*/merge"
        }
    }

    features {
        commitStatusPublisher {
            vcsRootExtId = Shoebill_Wrappers_Streamer_PullRequests.extId
            publisher = github {
                githubUrl = "https://api.github.com"
                authType = personalToken {
                    token = "zxx55b06fb9d2d8edabee9063bcfb4b94f17cd00ccb0e96d2e75a17b46c20fd52bf7039f29ea0d02cf4775d03cbe80d301b"
                }
            }
            param("github_username", "123marvin123")
            param("secure:github_password", "zxx9e24f4a85d83c3b76fc64bafcea1bb63")
        }
    }
})
