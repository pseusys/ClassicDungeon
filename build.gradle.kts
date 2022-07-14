tasks.withType<Wrapper> {
    gradleVersion = "7.4.2"
    distributionType = Wrapper.DistributionType.ALL
}

buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.10")
        classpath("com.android.tools.build:gradle:4.2.2")
    }
}

group = "com.ekdorn.classicdungeon"
version = "0.1-SNAPSHOT"

allprojects {
    repositories {
        mavenCentral()
    }
}
