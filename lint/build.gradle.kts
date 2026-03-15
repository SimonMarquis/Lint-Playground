plugins {
    alias(libs.plugins.playground.android.library)
    alias(libs.plugins.playground.lint)
    alias(libs.plugins.nmcp)
    `maven-publish`
    signing
}

android.publishing.singleVariant("release")

publishing {
    repositories {
        mavenLocal {
            url = uri(isolated.rootProject.projectDirectory.dir("build/.m2/repository"))
        }
    }
    publications {
        register<MavenPublication>("Lint") {
            artifactId = "lint"
            afterEvaluate {
                from(components.getByName("release"))
            }
            pom {
                name = "Lint rules"
                description = "Android Lint rules"
                url = "https://github.com/SimonMarquis/Lint-Playground"
                licenses {
                    license {
                        name.set("MIT")
                        url.set("https://opensource.org/licenses/MIT")
                    }
                }
                developers {
                    developer {
                        id = "SimonMarquis"
                        name = "Simon Marquis"
                        email = "contact@simon-marquis.fr"
                    }
                }
                scm {
                    connection = "scm:git:git://github.com/SimonMarquis/Lint-Playground.git"
                    developerConnection = "scm:git:ssh://github.com/SimonMarquis/Lint-Playground.git"
                    url = "https://github.com/SimonMarquis/Lint-Playground"
                }
            }
        }
    }
}

signing {
    val defaultSecretKey = System.getenv("GPG_SECRET_KEY")
    val defaultPassword = System.getenv("GPG_PASSWORD")
    if (defaultSecretKey.isNullOrBlank()) return@signing
    useInMemoryPgpKeys(defaultSecretKey, defaultPassword)
    sign(publishing.publications)
    isRequired = true
}

nmcp.publishAllPublicationsToCentralPortal {
    username = System.getenv("CENTRAL_PORTAL_USERNAME")
    password = System.getenv("CENTRAL_PORTAL_PASSWORD")
    publishingType = "AUTOMATIC"
}

val isSnapshotVersion = version.toString().endsWith("-SNAPSHOT")
tasks.publish.configure {
    if (isSnapshotVersion) dependsOn("publishAllPublicationsToCentralPortalSnapshots")
    else dependsOn("publishAllPublicationsToCentralPortal")
}

dependencies {
    lintPublish(projects.checks) {
        // Remove automatic api dependency on kotlin-stdlib to be able to publish `:lint` module.
        // Lint library only supports a single JAR file in the 'lintPublish' configuration.
        // And `kotlin.stdlib.default.dependency=false` applies to the entire project.
        isTransitive = false
    }
}
