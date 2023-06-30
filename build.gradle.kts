import com.github.gradle.node.npm.task.NpxTask

plugins {
    id("com.github.node-gradle.node") version "3.1.1"
    id("com.diffplug.spotless") version "6.14.1"
}

group = "com.opensource"
version = "1.0-SNAPSHOT"

spotless {
    typescript {
        target("lib/**/*.ts", "test/**/*.ts")
        prettier()
        eslint("8.41.0")
                .configFile(".eslintrc.json")
                .tsconfigFile("tsconfig.json")
        endWithNewline()
    }
    json {
        target("**/*.json")
        targetExclude("**/node_modules/","**/node/","**/build/","**/cdk.out/")
        endWithNewline()
    }
}

node {
    version.set("16.20.0")
    npmVersion.set("9.6.7")
    distBaseUrl.set("https://nodejs.org/download/release")
    download.set(true)
    npmInstallCommand.set("ci")
}

task<NpxTask>("test") {
    dependsOn("compile")
    description = "Test using Jest"
    command.set("npm")
    args.set(listOf("run", "test", "--") + npmArguments())
}

task<NpxTask>("compile") {
    dependsOn("npmInstall")
    description = "Compile using Typescript Compiler"
    command.set("npm")
    args.set(listOf("run", "build"))
}

/**
 * Returns the NPM arguments for Jest.
 */
fun npmArguments(): List<String> {
    val npmArgs: String? by project
    return npmArgs.orEmpty()
            .split("-c ")
            .filter { it.isNotEmpty() }
            .map { listOf("--" + it.trim()) }
            .flatten()
}
