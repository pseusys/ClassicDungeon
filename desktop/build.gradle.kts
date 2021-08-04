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

tasks.create("downloadGLFW") {
    outputs.files("./glfw/glfw.h", "./glfw/glfw.a")

    val bits = System.getProperty("sun.arch.data.model")
    val file = "glfw-3.3.4.bin.WIN$bits"

    val archive = File("./glfw.zip")
    val url = "https://github.com/glfw/glfw/releases/download/3.3.4/$file.zip"
    ant.invokeMethod("get", mapOf("src" to url, "dest" to archive))

    copy {
        from(zipTree("./glfw.zip")) {
            val dir = "lib-mingw-w$bits"
            include("$file/include/GLFW/glfw3.h", "$file/$dir/libglfw3.a")
            eachFile {
                val fileName = if (relativePath.segments.last() == "glfw3.h") "glfw.h" else "glfw.a"
                relativePath = RelativePath(true, fileName)
            }
            includeEmptyDirs = false
        }
        into("./glfw")
    }
    delete(archive)
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
