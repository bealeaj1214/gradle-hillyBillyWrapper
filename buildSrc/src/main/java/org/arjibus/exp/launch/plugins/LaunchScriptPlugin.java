package org.arjibus.exp.launch.plugin;

import org.arjibus.exp.launch.scripts.LaunchScriptSpec;
import org.arjibus.exp.launch.scripts.LaunchScriptBinarySpec;

import org.arjibus.exp.launch.scripts.tasks.IdiotTask;

import org.gradle.api.*;


import org.gradle.language.base.plugins.ComponentModelBasePlugin;


//import org.gradle.platform.base;
import org.gradle.platform.base.BinaryType;
import org.gradle.platform.base.BinaryTypeBuilder;
import org.gradle.platform.base.ComponentBinaries;
import org.gradle.platform.base.ComponentType;
import org.gradle.platform.base.ComponentTypeBuilder;

import org.gradle.model.Defaults;
import org.gradle.model.Model;
import org.gradle.model.ModelMap;
import org.gradle.model.Mutate;
import org.gradle.model.Path;
import org.gradle.model.RuleSource;

import java.io.File;


public class LaunchScriptPlugin implements Plugin<Project>{

    public void apply(Project project){
	project.getPluginManager().apply(ComponentModelBasePlugin.class);
    }


    static class Rules extends RuleSource {

	@ComponentType
	void registerComponent(ComponentTypeBuilder<LaunchScriptSpec> builder){
	}

	@BinaryType
	void registerBinary(BinaryTypeBuilder<LaunchScriptBinarySpec> builder){

	}

	@ComponentBinaries
	void createBinaries(ModelMap<LaunchScriptBinarySpec> binaries,
			    final LaunchScriptSpec  component,
			    final @Path("buildDir") File buildDir){

	    binaries.create(component.getName(),
			    new Action<LaunchScriptBinarySpec>() {

				@Override
				public void execute(LaunchScriptBinarySpec binary){
				    binary.setMainClass(component.getMainClass());
				    //binary
				    binary.setOutputDirectory(new File(buildDir,"launchScripts"));
				}

			    }
			    );
	}

    }
}
