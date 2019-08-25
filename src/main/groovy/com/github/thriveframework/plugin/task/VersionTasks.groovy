package com.github.thriveframework.plugin.task

import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.Task

import static com.github.thriveframework.plugin.utils.Projects.createTask

class VersionTasks {
    //todo create when missing

    //todo docs
    static Echo createWriteVersion(Project project){
        def targetProvider = { new File(project.buildDir, "generated/metadata/version.txt") }
        def writeVersion = createTask(
            project,
            "writeVersion",
            Echo,
            "build",
            "Writes project version to <buildDir>/generated/metadata/version.txt (useful for build automation)"
        ) {
            content = project.provider{ "${project.version}" }
            target = project.layout.file(project.provider(targetProvider))
        }

        project.clean.doLast {
            //delete "generated" dir
            targetProvider().parentFile.parentFile.deleteDir()
        }

        return writeVersion
    }

    //todo test it
    //todo docs
    static Task createPrintVersion(Project project, String prefix = "Project version:"){
        createTask(
            project,
            "printVersion",
            DefaultTask,
            "documentation",
            "" //todo
        ) {
            doLast {
                println "$prefix ${project.version}"
            }
        }
    }
}
