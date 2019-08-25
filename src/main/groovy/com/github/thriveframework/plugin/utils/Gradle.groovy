package com.github.thriveframework.plugin.utils

import org.gradle.util.GradleVersion

class Gradle {

    static void assertVersionAtLeast(GradleVersion minVersion){
        assert GradleVersion.current().compareTo(minVersion) >= 0, "Gradle ${minVersion.version} or later required!. The current version is ${GradleVersion.current().version}"
    }

    static void assertVersionAtLeast(String minVersion){
        assertVersionAtLeast(GradleVersion.version(minVersion))
    }
}
