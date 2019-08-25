package com.github.thriveframework.plugin.task

import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.Task

import static com.github.thriveframework.plugin.utils.Projects.getOrCreateTask

/**
 * Fixtures for a few project-version-related tasks. These do not provide much value, but they are pretty useful
 * for build automation and debugging (specifically debugging of some automated rules for versioning).
 *
 * {@see <a href="https://github.com/thrive-framework/thrive-versioning-plugin">versioning plugin</a>}
 */
class VersionTasks {

    /**
     * Creates a "writeVersion" task, that writes project version to [project]/build/generated/metadata/version.txt and
     * extends "clean" task with removal of that file (it such task exists).
     *
     * If a task with that name exists it does nothing, returns it instead.
     *
     * Useful for build automation (e.g. when you want to build docker images and tag them with project version).
     * @param project Project to which the task should be added.
     * @return "writeVersion" task (newly created or previously existing) of type {@link Echo}
     */
    static Echo createWriteVersion(Project project){
        def targetProvider = { new File(project.buildDir, "generated/metadata/version.txt") }
        def writeVersion = getOrCreateTask(
            project,
            "writeVersion",
            Echo,
            "build",
            "Writes project version to <buildDir>/generated/metadata/version.txt (useful for build automation)"
        ) {
            content = project.provider{ "${project.version}" }
            target = project.layout.file(project.provider(targetProvider))
        }

        project.tasks.findByName("clean")?.doLast {
            //delete "generated" dir
            targetProvider().parentFile.parentFile.deleteDir()
        }

        return writeVersion
    }

    /**
     * Creates a "printVersion" task, that prints project version to stdout.
     *
     * If a task with that name exists it does nothing, returns it instead.
     *
     * Useful for debugging.
     * @param project Project to which the task should be added.
     * @param prefix Prefix to be printed before the version, at the same line
     * @return "printVersion" task (newly created or previously existing) of type {@link DefaultTask}
     */
    static Task createPrintVersion(Project project, String prefix = "Project version:"){
        getOrCreateTask(
            project,
            "printVersion",
            DefaultTask,
            "documentation",
            "Prints project version to stdout"
        ) {
            doLast {
                if (prefix == null)
                    prefix = ""
                if (prefix && !(prefix.endsWith(" ")))
                    prefix = prefix+" "
                println "${prefix}${project.version}"
            }
        }
    }
}
