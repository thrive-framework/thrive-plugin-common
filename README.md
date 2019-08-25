# thrive-plugin-common

Common code for [Thrive](https://github.com/thrive-framework) Gradle plugins.

Provides:
- tasks:
  - [Echo](/src/main/groovy/com/github/thriveframework/plugin/task/Echo.groovy) - similiar to unix `echo` command
  - [VersionTasks](/src/main/groovy/com/github/thriveframework/plugin/task/VersionTasks.groovy) - fixtures for version-related tasks
- utility classes:
  - [Gradle](/src/main/groovy/com/github/thriveframework/plugin/utils/Gradle.groovy) - for asserting current Gradle distribution
  - [Projects](/src/main/groovy/com/github/thriveframework/plugin/utils/Projects.groovy) - for manipulating and inspecting projects

## Get it

Use JitPack:

    repositories {
        maven {
            name "jitpack"
            url "https://jitpack.io"
        }
    }
    
And declare a dependency on this project:

    implementation "com.github.thrive-framework:thrive-plugin-common:0.1.0-SNAPSHOT"

Keep in mind, that this is written in Groovy, so if you're writing your plugin in pure Java, you'll probably want to add `localGroovy()` dependency.