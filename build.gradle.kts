plugins {
    kotlin("jvm") version "1.6.10"
}

allprojects {
    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin="org.jetbrains.kotlin.jvm")
    dependencies {
        implementation(kotlin("stdlib"))
        implementation("io.github.muqhc:skolloble-to-xml:1.2.3")
    }
}