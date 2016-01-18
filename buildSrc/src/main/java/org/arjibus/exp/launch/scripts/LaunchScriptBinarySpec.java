package org.arjibus.exp.launch.scripts;

import org.gradle.model.Managed;

import org.gradle.platform.base.ApplicationBinarySpec;


import java.io.File;

@Managed
public interface LaunchScriptBinarySpec extends ApplicationBinarySpec {

    
    void setMainClass(String mainClass);
    String getMainClass();

    void setOutputDirectory(File outDirectory);
    File getOutputDirectory();
      
}
