plugins {
    kotlin("jvm") version "1.5.31"
    `maven-publish`
    id("com.google.cloud.artifactregistry.gradle-plugin") version "2.1.1"
}

group = "org.datausagetracing.integration"
version = "1.0.0"

repositories {
    maven {
        setUrl("artifactregistry://europe-west3-maven.pkg.dev/cnpe-blue/data-usage-tracing-maven")
    }
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    compileOnly("com.fasterxml.jackson.core:jackson-annotations:2.13.1")
    compileOnly("javax.servlet:javax.servlet-api:4.0.1")
}

publishing {
    repositories {
        maven {
            setUrl("artifactregistry://europe-west3-maven.pkg.dev/cnpe-blue/data-usage-tracing-maven")
        }
    }
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}