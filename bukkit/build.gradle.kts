plugins {
    id("xyz.jpenilla.run-paper") version "1.0.6"
}

setupShadowJar()

dependencies {
    api(project(":tags-core"))

    compileOnly(libs.paper.api)
    compileOnly("me.clip", "placeholderapi", Versions.PAPI)
    compileOnly("com.github.MilkBowl", "VaultAPI", Versions.VAULT)

    api(libs.cloud.paper)
    api(libs.cloud.extras)
}

tasks {
    runServer {
        minecraftVersion("1.18.2")
    }
}
