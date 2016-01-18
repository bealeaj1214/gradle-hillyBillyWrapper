package org.arjibus.exp.launch.plugin;

import org.arjibus.exp.launch.scripts.LaunchScriptSpec;
import org.arjibus.exp.launch.scripts.LaunchScriptBinarySpec;

import org.arjibus.exp.launch.scripts.tasks.IdiotTask;

import org.gradle.api.*;


import org.gradle.language.base.plugins.ComponentModelBasePlugin;


//import org.gradle.platform.base;
import org.gradle.platform.base.ComponentType;
import org.gradle.platform.base.ComponentTypeBuilder;

import org.gradle.model.Model;
import org.gradle.model.Mutate;
import org.gradle.model.RuleSource;

public class LaunchScriptPlugin implements Plugin<Project>{

    public void apply(Project project){
	project.getPluginManager().apply(ComponentModelBasePlugin.class);
    }


    static class Rules extends RuleSource {

	@ComponentType
	void registerComponent(ComponentTypeBuilder<LaunchScriptSpec> builder){}
    }
}
