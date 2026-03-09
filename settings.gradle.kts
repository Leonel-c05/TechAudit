pluginManagement {
    repositories {
google {
    content {
        includeGroupByRegex("com\\.android.*")
        includeGroupByRegex("com\\.google.*")
        includeGroupByRegex("androidx.*")
        includeGroupByRegex("org\\.google.*")
        includeGroupByRegex("org\\.jetbrains.*")
    }
}
mavenCentral()
gradlePluginPortal()
        gradlePluginPortal() // Agrégalo
}
}
dependencyResolutionManagement {
repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
repositories {
google()
mavenCentral()
}
}

rootProject.name = "TechAudit"
include(":app")
