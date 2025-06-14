rootProject.name = "user-service"

pluginManagement {
    val kotlinVersion = "2.1.21"

    plugins {
        kotlin("jvm") version kotlinVersion
        kotlin("plugin.spring") version kotlinVersion
        kotlin("plugin.jpa") version kotlinVersion
    }
}
