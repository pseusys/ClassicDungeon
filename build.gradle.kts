tasks.withType<Wrapper> {
    gradleVersion = "7.1.1"
    distributionType = Wrapper.DistributionType.ALL
}

buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.30")
        classpath("com.android.tools.build:gradle:4.2.2")
    }
}

group = "com.ekdorn.classicdungeon"
version = "1.0-SNAPSHOT"

allprojects {
    repositories {
        mavenCentral()
    }
}
