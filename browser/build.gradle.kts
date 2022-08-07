plugins {
    kotlin("js")
}

group = Globals.group
version = Globals.version

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
                // Known warning produced:
                // https://youtrack.jetbrains.com/issue/KTIJ-22030/browserDevelopmentRun-task-fails-with-webpack-cli-type-error
                outputFileName = "ClassicDungeon.js"
                cssSupport.enabled = true
            }
        }
    }
}



// Workaround for gradle not serving transitive resource dependencies correctly.
// https://youtrack.jetbrains.com/issue/KTIJ-18536

tasks["compileKotlinJs"].doLast {
    copy {
        from("../shared/build/processedResources/js/main")
        into("build/processedResources/js/main")
    }
    copy {
        from("../shared/build/processedResources/js/main")
        into("build/distributions")
    }
}
