package org.arjibus.exp.launch.scripts.internal;


import org.arjibus.exp.launch.scripts.LaunchScriptSpec;


import org.gradle.platform.base.ComponentSpec;
import org.gradle.platform.base.component.BaseComponentSpec;

import org.gradle.jvm.JvmComponentSpec;

public class BaseLaunchScriptSpec extends BaseComponentSpec implements LaunchScriptSpec {

    private String mainClass;

    private JvmComponentSpec launchTarget;


    
    public void setMainClass(String mainClazz){
	mainClass = mainClazz;
    }

    
    public String getMainClass(){
	return mainClass;
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
