plugins {
    java
    scala
    application
    id("org.openjfx.javafxplugin") version "0.0.8"
    id("org.beryx.jlink") version "2.12.0"
}

group = "com.github.hzqd"
version = "0.1"

application {
    mainClassName = "com.github.hzqd.tank.war.App"
}

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

javafx {
    version = "13.0.1"
    modules = mutableListOf("javafx.controls")
}

jlink {
    launcher {
        name = "hellofx"
    }
}

dependencies {
    implementation("org.scala-lang:scala-library:2.13.1")
    implementation("junit", "junit", "4.12")
    implementation("org.openjfx", "javafx-base", "13.0.1")
    implementation("org.openjfx", "javafx-graphics", "13.0.1")
    implementation("org.openjfx", "javafx-controls", "13.0.1")
    implementation("org.openjfx", "javafx-media", "13.0.1")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}
tasks {
    compileScala
}
