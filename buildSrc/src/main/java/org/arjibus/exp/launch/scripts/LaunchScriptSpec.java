package org.arjibus.exp.launch.scripts;

import org.gradle.model.Managed;

import org.gradle.platform.base.ApplicationSpec;
import org.gradle.platform.base.ComponentSpec;
import org.gradle.jvm.JvmComponentSpec;

//@Managed
public interface LaunchScriptSpec extends ComponentSpec {


    void setMainClass(String mainClass);
    String getMainClass();


    /**
     * The component the script will wrap.
     */
    JvmComponentSpec getLaunchTarget();

    void launchTarget(JvmComponentSpec launchTarget);
    
}
