plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

group = "com.ekdorn.classicdungeon"
version = "1.0-SNAPSHOT"

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
        binaries.executable()
        browser {
            commonWebpackConfig {
                outputFileName = "ClassicDungeon.js"
                cssSupport.enabled = true
            }
        }
    }


    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.2.1")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.0")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }

        val jsMain by getting {
            dependencies {
                implementation(npm("read-pixels", "0.2.1"))
            }
        }
    }
}

android {
    compileSdkVersion(30)
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdkVersion(24)
        targetSdkVersion(30)
    }
}
