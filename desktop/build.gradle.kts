plugins {
    kotlin("multiplatform")
}

group = "com.ekdorn.classicdungeon"
version = "1.0-SNAPSHOT"

repositories {
    google()
    mavenCentral()
}

kotlin {
    mingwX64("desktop") {
        binaries {
            executable()
        }
    }

    sourceSets {
        val desktopMain by getting {
            dependencies {
                implementation(project(":shared"))
            }
        }
    }
}



// Workaround for gradle not serving transitive resource dependencies correctly.
// https://youtrack.jetbrains.com/issue/KTIJ-18536

tasks.create("copyResources") {
    copy {
        from("../shared/build/processedResources/js/main")
        into("build/processedResources/js/main")
    }
}
/*
tasks["browserDistribution"].doLast {
    tasks["copyResources"]
}
*/
