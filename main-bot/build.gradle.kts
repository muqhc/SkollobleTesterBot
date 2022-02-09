import org.jetbrains.kotlin.gradle.targets.js.testing.karma.processWebpackName

plugins {
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

dependencies {
    compileOnly("org.slf4j:slf4j-api:1.7.25")
    implementation("com.discord4j:discord4j-core:3.2.1")
}

sourceSets {
    all {
        dependencies {
            implementation("com.discord4j:discord4j-core:3.2.1")
            implementation("ch.qos.logback:logback-classic:1.2.3")
        }
    }
}

tasks {
    jar {
        manifest {
            attributes("Main-Class" to "${project.properties["group"] as String}.skblebot.SkollobleTesterBot")
        }
    }
}