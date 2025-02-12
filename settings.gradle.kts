pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "AutoserviceMobile"
include(":app")
include(":autoserviceAPI")
include(":autoserviceAPI")
include(":autoserviceData")
include(":autoserviceDatabase")
include(":newsAPI")
include(":common")
include(":newsData")
