plugins {
    kotlin("multiplatform")
    id(Dependencies.Plugins.serialization) version(Versions.Plugins.serialization)
    id(Dependencies.Plugins.android)
}

group = Globals.group
version = Globals.version

repositories {
    google()
    mavenCentral()
}

kotlin {
    android()
    mingwX64("desktop") {
        binaries {
            sharedLib {
                baseName = "libnative"
            }
        }
    }
    js(IR) {
        browser()
    }


    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("${Dependencies.Common.dateTime}:${Versions.Common.dateTime}")
                implementation("${Dependencies.Common.coroutines}:${Versions.Common.coroutines}")
                implementation("${Dependencies.Common.serialization}:${Versions.Common.serialization}")
                implementation("${Dependencies.Common.kermit}:${Versions.Common.kermit}")
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin(Dependencies.Common.test))
            }
        }

        val jsMain by getting {
            dependencies {
                implementation(npm(Dependencies.Browser.readPixels, Versions.Browser.readPixels))
            }
        }
    }
}

android {
    compileSdkVersion(32)
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdkVersion(24)
        targetSdkVersion(32)
    }
}
