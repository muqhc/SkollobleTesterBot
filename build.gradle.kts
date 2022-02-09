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

tasks {

    jar {
        from(project(":main-bot").sourceSets["main"].output)
        manifest {
            attributes("Main-Class" to "${project.properties["group"] as String}.skblebot.SkollobleTesterBot")
        }
    }

    create<Task>("stage") {
        dependsOn(":build")
    }

}