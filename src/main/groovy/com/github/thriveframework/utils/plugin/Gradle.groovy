package com.github.thriveframework.utils.plugin

import org.gradle.util.GradleVersion

/**
 * Utility methods for asserting current gradle distribution.
 */
class Gradle {
    /**
     * Asserts (with Groovy {@code assert} that current Gradle version is at equal or greater than given.
     * @param minVersion smallest acceptable Gradle version
     */
    static void assertVersionAtLeast(GradleVersion minVersion){
        assert GradleVersion.current().compareTo(minVersion) >= 0, "Gradle ${minVersion.version} or later required!. The current version is ${GradleVersion.current().version}"
    }

    /**
     * Asserts (with Groovy {@code assert} that current Gradle version is at equal or greater than given.
     * @param minVersion smallest acceptable Gradle version
     */
    static void assertVersionAtLeast(String minVersion){
        assertVersionAtLeast(GradleVersion.version(minVersion))
    }
}
