package org.arjibus.exp.launch.scripts;

import org.gradle.model.Managed;

import org.gradle.platform.base.ApplicationSpec;

@Managed
public interface LaunchScriptSpec extends ApplicationSpec {


    void setMainClass(String mainClass);
    String getMainClass();
	
}
