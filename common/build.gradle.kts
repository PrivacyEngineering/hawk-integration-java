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
    testImplementation("org.hamcrest:hamcrest:2.2")
    compileOnly("com.fasterxml.jackson.core:jackson-databind:2.13.1")
    compileOnly("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.13.1")
    compileOnly("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.13.1")
    compileOnly("com.fasterxml.jackson.dataformat:jackson-dataformat-properties:2.13.1")
    compileOnly("javax.servlet:javax.servlet-api:4.0.1")
    compileOnly("org.slf4j:slf4j-api:1.8.0-beta4")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.2")
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

tasks.withType<Test> {
    useJUnitPlatform()
}