import jetbrains.buildServer.configs.kotlin.v10.*
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.MavenBuildStep
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.MavenBuildStep.*
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.maven
import jetbrains.buildServer.configs.kotlin.v10.triggers.VcsTrigger
import jetbrains.buildServer.configs.kotlin.v10.triggers.VcsTrigger.*
import jetbrains.buildServer.configs.kotlin.v10.triggers.vcs

object Shoebill_Wrappers_Streamer_Deploy : BuildType({
    uuid = "302c820d-f614-47a1-804e-a8aa322f3e5d"
    extId = "Shoebill_Wrappers_Streamer_Deploy"
    name = "Deploy"
    description = "Builds, tests and deploys the project."

    artifactRules = "target/*.jar"

    vcs {
        root(Shoebill_Wrappers_Streamer.vcsRoots.Vcs_Shoebill_Wrappers_Streamer)

        checkoutMode = CheckoutMode.ON_SERVER
    }

    steps {
        maven {
            goals = "clean install test deploy"
            userSettingsSelection = "Sonatype"
            userSettingsPath = ""
            param("maven.home", "")
            param("target.jdk.home", "%env.JDK_18%")
        }
    }

    triggers {
        vcs {
            branchFilter = """+:refs/heads/master
                              +:master"""
        }
    }
})
