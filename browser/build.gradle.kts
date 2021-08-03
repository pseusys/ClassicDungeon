plugins {
    kotlin("js")
}

group = "com.ekdorn.classicdungeon"
version = "1.0-SNAPSHOT"

repositories {
    google()
    mavenCentral()
}

dependencies {
    implementation(project(":shared"))
}

kotlin {
    js(IR) {
        binaries.executable()
        browser {
            commonWebpackConfig {
                outputFileName = "ClassicDungeon.js"
                cssSupport.enabled = true
            }
        }
    }
}



// Workaround for gradle not serving transitive resource dependencies correctly.
// https://youtrack.jetbrains.com/issue/KTIJ-18536

tasks["browserDistribution"].doLast {
    copy {
        from("../shared/build/processedResources/js/main")
        into("build/distributions")
    }
}
tasks["browserDevelopmentRun"].doFirst {
    copy {
        from("../shared/build/processedResources/js/main")
        into("build/processedResources/js/main")
    }
}
