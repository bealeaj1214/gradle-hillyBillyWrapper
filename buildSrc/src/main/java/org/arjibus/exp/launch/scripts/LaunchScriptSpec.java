package org.arjibus.exp.launch.scripts;

import org.gradle.model.Managed;

import org.gradle.platform.base.ApplicationSpec;
import org.gradle.platform.base.ComponentSpec;

@Managed
public interface LaunchScriptSpec extends ComponentSpec {


    void setMainClass(String mainClass);
    String getMainClass();
	
}
