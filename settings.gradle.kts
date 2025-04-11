pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        maven("https://oss.sonatype.org/content/repositories/snapshots")
        maven("https://repo.spongepowered.org/maven/")
        maven("https://repo.essential.gg/repository/maven-public")
        maven("https://repo.essential.gg/public")
        maven("https://maven.architectury.dev")
        maven("https://maven.fabricmc.net")
        maven("https://maven.minecraftforge.net")

    }
    resolutionStrategy {
        eachPlugin {
            when (requested.id.id) {
                "gg.essential.loom" -> useModule("gg.essential:architectury-loom:${requested.version}")
            }
        }
    }
}


plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version("0.8.0")
    //id("com.github.johnrengelman.shadow") version "8.1.1"
}


rootProject.name = "KazzUtils"


/*

    mavenCentral()
    mavenLocal()
    gradlePluginPortal()
    maven("https://repo.spongepowered.org/maven/")

    // If you don't want to log in with your real minecraft account, remove this line
    maven("https://pkgs.dev.azure.com/djtheredstoner/DevAuth/_packaging/public/maven/v1")

    maven("https://repo.nea.moe/releases")
    maven("https://maven.notenoughupdates.org/releases")
    maven("https://repo.essential.gg/repository/maven-public")
    maven("https://maven.essential.gg/")
    maven("https://maven.architectury.dev")
    maven("https://maven.fabricmc.net")
    maven("https://maven.minecraftforge.net")

    idea
    java

    id("gg.essential.defaults") version "0.6.7"
    id("dev.architectury.architectury-pack200") version "0.1.3"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    kotlin("jvm") version "1.9.0"
    kotlin("plugin.serialization") version "1.9.22"
    id("org.jetbrains.gradle.plugin.idea-ext") version "1.1.7"
    id("gg.essential.loom") version "0.10.0.+"

 */