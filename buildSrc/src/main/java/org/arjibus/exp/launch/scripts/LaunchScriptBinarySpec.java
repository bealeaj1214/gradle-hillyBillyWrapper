package org.arjibus.exp.launch.scripts;

import org.gradle.model.Managed;


import org.gradle.platform.base.ApplicationBinarySpec;
import org.gradle.platform.base.BinarySpec;
import org.gradle.platform.base.ComponentSpec;

import org.gradle.jvm.JvmComponentSpec;

import java.io.File;


public interface LaunchScriptBinarySpec extends BinarySpec {

    
    void setMainClass(String mainClass);
    String getMainClass();

    void setOutputDirectory(File outDirectory);
    File getOutputDirectory();

    /**
     * The component the script will wrap.
     */
    JvmComponentSpec getLaunchTarget();

    void launchTarget(JvmComponentSpec launchTarget);
    

}
