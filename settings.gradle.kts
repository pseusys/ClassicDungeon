pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
    
}

rootProject.name = "ClassicDungeon"

include(":android")
include(":browser")
include(":desktop")
include(":shared")
