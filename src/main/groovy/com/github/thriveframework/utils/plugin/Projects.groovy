package com.github.thriveframework.utils.plugin

import groovy.util.logging.Slf4j
import org.gradle.api.Project
import org.gradle.api.Task

/**
 * Utility methods for project manipulation and inspection.
 */
@Slf4j
class Projects {
    /**
     * Get full project name (path), with parent projects and colons (":").
     * @param project Project to be inspected
     * @return Full project name (path)
     */
    static String fullName(Project project){
        def l = []
        while (project) {
            l = [ project.name ] + l
            project = project.parent
        }
        ([""]+l).join(":")
    }

    /**
     * Soft wrapper around {@code project.tasks.create(Map, Closure)} with some useful logging. Parameter names
     * correspond to map keys in mentioned method.
     * @param project project in which new task should be created
     * @return newly created task
     */
    static <T extends Task> T createTask(Project project, String name, Class<T> type, String group, String desc, Closure config){
        createTask(project, name, type, group, desc, [], config)
    }

    /**
     * Soft wrapper around {@code project.tasks.create(Map, Closure)} with some useful logging. Parameter names
     * correspond to map keys in mentioned method.
     * @param project project in which new task should be created
     * @return newly created task
     */
    static <T extends Task> T createTask(Project project, String name, Class<T> type, String group, String desc, List constructorArgs, Closure config){
        log.info("Creating '$name' task in project ${fullName(project)}")
        project.tasks.create(
            [
                name: name,
                type: type,
                description: desc,
                group: group,
                constructorArgs: constructorArgs
            ],
            config
        )
    }

    /**
     * Ensures existence of a task with given name. If such task exists, it is returned, if not - delegates to {@link this::createTask}
     * @param project project in which new task should be created if missing
     * @return task with given name
     */
    static <T extends Task> T getOrCreateTask(Project project, String name, Class<T> type, String group, String desc, Closure config){
        getOrCreateTask(project, name, type, group, desc, [], config)
    }

    /**
     * Ensures existence of a task with given name. If such task exists, it is returned, if not - delegates to {@link this::createTask}
     * @param project project in which new task should be created if missing
     * @return task with given name
     */
    static <T extends Task> T getOrCreateTask(Project project, String name, Class<T> type, String group, String desc, List constructorArgs, Closure config){
        def existing = project.tasks.findByName(name)
        if (existing){
            log.info("Task '${name}' already exists in project ${fullName(project)}")
            return existing
        }
        createTask(project, name, type, group, desc, constructorArgs, config)
    }

    /**
     * Applies given {@code plugin} (any {@code Object}, but most probably anything but {@code String} and {@code Class<?
     * extends Plugin<...>} will fail) to given {@code project}
     *
     * Simple {@code Project::apply} is no-op when plugin is already applied, but this method introduces nicer logging.
     * @param project project to which the plugin should be applied
     * @param plugin plugin to be applied
     */
    static void applyPlugin(project, plugin){
        String nameToLog;
        if (plugin instanceof Class)
            nameToLog = plugin.canonicalName
        else if (plugin instanceof String)
            nameToLog = plugin
        else
            log.warn("$plugin is neither a class nor String, but rather ${plugin.class}; prepare for possible trouble")
        log.info("Trying to apply plugin with implementation $nameToLog to project ${fullName(project)}")
        if (!project.plugins.findPlugin(plugin)) {
            log.info("Applying $nameToLog")
            project.apply plugin: plugin
        } else {
            log.info("$nameToLog already applied")
        }
    }
}
