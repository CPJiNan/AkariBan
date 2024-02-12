import io.izzel.taboolib.gradle.*

plugins {
    `java-library`
    `maven-publish`
    id("io.izzel.taboolib") version "2.0.2"
    id("org.jetbrains.kotlin.jvm") version "1.9.22"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.22"
    id("org.jetbrains.dokka") version "1.9.10"
}

taboolib {
    env {
        install(
            UNIVERSAL,
            UI,
            KETHER,
            METRICS,
            DATABASE,
            BUKKIT_ALL
        )
    }
    description {
        contributors {
            name("CPJiNan")
        }
        dependencies {
            name("PlaceholderAPI").optional(true)
        }
    }
    version { taboolib = "6.1.0" }
    relocate("kotlinx.serialization", "kotlinx.serialization160")
}

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("ink.ptms:nms-all:1.0.0")
    compileOnly("ink.ptms.core:v12004:12004:mapped")
    compileOnly("ink.ptms.core:v12004:12004:universal")
    compileOnly(kotlin("stdlib"))
    taboo("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
    taboo("org.jetbrains.kotlinx:kotlinx-serialization-cbor:1.6.0")
    compileOnly(fileTree("libs"))
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = listOf("-Xjvm-default=all")
    }
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}