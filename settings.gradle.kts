pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
    
}

rootProject.name = "ClassicDungeon"

include(":android")
include(":shared")
include(":desktop")

