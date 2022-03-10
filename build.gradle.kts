plugins {
    kotlin("jvm") version "1.6.10"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(16))
    }
}

allprojects {
    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin="org.jetbrains.kotlin.jvm")
    apply(plugin="com.github.johnrengelman.shadow")
    dependencies {
        implementation(kotlin("stdlib"))
        implementation("io.github.muqhc:skolloble-to-xml:1.2.4")
        implementation("io.github.muqhc:xml-to-skolloble:1.0.0")
        implementation("com.google.firebase:firebase-admin:8.1.0")
    }
}

sourceSets {
    all {
        dependencies {
            subprojects.forEach { implementation(project(":${it.name}")) }
        }
    }
}

tasks {

    jar {
        subprojects.forEach { from(project(":${it.name}").sourceSets["main"].output) }
        manifest {
            attributes("Main-Class" to "${project.properties["group"] as String}.skblebot.SkollobleTesterBot")
        }
    }

    shadowJar {
        subprojects.forEach { dependsOn(":${it.name}:shadowJar") }
        manifest {
            attributes("Main-Class" to "${project.properties["group"] as String}.skblebot.SkollobleTesterBot")
        }
    }

    create<Task>("stage") {
        dependsOn(":shadowJar")
    }

}