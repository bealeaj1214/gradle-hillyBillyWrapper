package org.arjibus.exp.launch.scripts.internal;


import org.arjibus.exp.launch.scripts.LaunchScriptBinarySpec;


import org.gradle.platform.base.BinarySpec;
import org.gradle.platform.base.ComponentSpec;

import org.gradle.platform.base.binary.BaseBinarySpec;

import org.gradle.jvm.JvmComponentSpec;

import java.io.File;

public class BaseLaunchScriptBinarySpec extends BaseBinarySpec implements LaunchScriptBinarySpec {

    private String mainClass;
    private File outputDirectory;

    private JvmComponentSpec launchTarget;


    
    public void setMainClass(String mainClazz){
	mainClass = mainClazz;
    }

    
    public String getMainClass(){
	return mainClass;
    }


    public void setOutputDirectory(File outDirectory){
	this.outputDirectory = outDirectory;
    }
    
    public File getOutputDirectory(){
	return outputDirectory;
    }
    
    /**
     * The component the script will wrap.
     */
    public JvmComponentSpec getLaunchTarget(){
	return launchTarget;
    }

    public void launchTarget(JvmComponentSpec launchTarget){
	this.launchTarget = launchTarget;
    }
    
}
