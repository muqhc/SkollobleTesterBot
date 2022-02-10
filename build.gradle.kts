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
        implementation("io.github.muqhc:skolloble-to-xml:1.2.3")
    }
}

sourceSets {
    all {
        dependencies {
            implementation(project(":main-bot"))
        }
    }
}

tasks {

    jar {
        from(project(":main-bot").sourceSets["main"].output)
        manifest {
            attributes("Main-Class" to "${project.properties["group"] as String}.skblebot.SkollobleTesterBot")
        }
    }

    shadowJar {
        dependsOn(":main-bot:shadowJar")
        manifest {
            attributes("Main-Class" to "${project.properties["group"] as String}.skblebot.SkollobleTesterBot")
        }
    }

    create<Task>("stage") {
        dependsOn(":shadowJar")
    }

}