plugins {
    kotlin("jvm") version "1.5.31"
    `maven-publish`
    id("net.linguica.maven-settings") version "0.5"
    id("org.hibernate.build.maven-repo-auth") version "3.0.2"
}

group = "org.datausagetracing.integration"
version = "1.0.3"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    testImplementation("org.hamcrest:hamcrest:2.2")
    compileOnly("com.fasterxml.jackson.core:jackson-databind:2.13.2")
    compileOnly("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.13.2")
    compileOnly("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.13.2")
    compileOnly("com.fasterxml.jackson.dataformat:jackson-dataformat-properties:2.13.2")
    compileOnly("javax.servlet:javax.servlet-api:4.0.1")
    compileOnly("org.slf4j:slf4j-api:1.8.0-beta4")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.2")
}

tasks {
    jar {
        archiveFileName.set("common.jar")
    }
}

publishing {
    repositories {
        maven {
            name = "github"
            setUrl("https://maven.pkg.github.com/TUB-CNPE-TB/hawk-integration-java")
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