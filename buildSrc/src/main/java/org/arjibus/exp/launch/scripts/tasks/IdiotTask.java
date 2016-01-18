package org.arjibus.exp.launch.scripts.tasks;


import org.gradle.api.DefaultTask;

import org.gradle.api.tasks.TaskAction;

import java.io.File;

public class IdiotTask extends DefaultTask {

    private String mainClass;

    private File outputDirectory;

    public void setMainClass(String mainClass){
	this.mainClass = mainClass;
    }
    
    public String getMainClass(){
	return mainClass;
    }
	
    public void setOutputDirectory(File outDirectory){
	this.outputDirectory =outDirectory;
    }
    
    File getOutputDirectory(){
	return outputDirectory;
    }


    @TaskAction
    void executeAction(){
	System.out.println("task "+getName()+ " class: " + mainClass);
    }

}
