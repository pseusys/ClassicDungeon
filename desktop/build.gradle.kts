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

val glfw = "3.3.4"

// TODO: register before native build.
tasks.create("downloadGLFW") {
    outputs.files("./glfw/glfw.h", "./glfw/glfw.a")

    val bits = System.getProperty("sun.arch.data.model")
    val file = "glfw-$glfw.bin.WIN$bits"

    val archive = File("./glfw.zip")
    val url = "https://github.com/glfw/glfw/releases/download/$glfw/$file.zip"
    ant.invokeMethod("get", mapOf("src" to url, "dest" to archive))

    copy {
        from(zipTree("./glfw.zip")) {
            val dir = "lib-mingw-w$bits"
            include("$file/include/GLFW/glfw${glfw[0]}.h", "$file/$dir/libglfw${glfw[0]}.a")
            eachFile {
                val ext = relativePath.segments.last().substringAfterLast('.')
                relativePath = RelativePath(true, "glfw.$ext")
            }
            includeEmptyDirs = false
        }
        into("./glfw")
    }
    delete(archive)
}



// Workaround for gradle not serving transitive resource dependencies correctly.
// https://youtrack.jetbrains.com/issue/KTIJ-18536
// TODO: manage with native resources.
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
