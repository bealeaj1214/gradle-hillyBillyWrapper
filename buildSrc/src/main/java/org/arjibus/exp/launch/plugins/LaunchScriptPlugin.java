package org.arjibus.exp.launch.plugin;


import org.arjibus.exp.launch.scripts.LaunchScriptContainer;
import org.arjibus.exp.launch.scripts.LaunchScriptSpec;
import org.arjibus.exp.launch.scripts.LaunchScriptSpec;
import org.arjibus.exp.launch.scripts.LaunchScriptBinarySpec;

import org.arjibus.exp.launch.scripts.internal.BaseLaunchScriptSpec;
import org.arjibus.exp.launch.scripts.internal.BaseLaunchScriptBinarySpec;


import org.arjibus.exp.launch.scripts.tasks.IdiotTask;

import org.gradle.api.*;


import org.gradle.api.artifacts.dsl.RepositoryHandler;

import org.gradle.api.internal.artifacts.ArtifactDependencyResolver;

import org.gradle.api.internal.artifacts.repositories.ResolutionAwareRepository;

import org.gradle.internal.Transformers;

import org.gradle.internal.service.ServiceRegistry;

import org.gradle.jvm.JvmBinarySpec;
import org.gradle.jvm.JvmComponentSpec;


import org.gradle.jvm.platform.JavaPlatform;

import org.gradle.jvm.platform.internal.DefaultJavaPlatform;

import org.gradle.jvm.plugins.JvmComponentPlugin;

import org.gradle.language.base.plugins.ComponentModelBasePlugin;

//import org.gradle.platform.base;
import org.gradle.platform.base.BinaryContainer;
import org.gradle.platform.base.BinarySpec;
import org.gradle.platform.base.BinaryType;
import org.gradle.platform.base.BinaryTypeBuilder;
import org.gradle.platform.base.ComponentBinaries;
import org.gradle.platform.base.ComponentType;
import org.gradle.platform.base.ComponentTypeBuilder;

import org.gradle.platform.base.internal.BinarySpecInternal;
import org.gradle.platform.base.internal.PlatformResolvers;
import org.gradle.platform.base.internal.*;


import org.gradle.model.Defaults;
import org.gradle.model.Model;
import org.gradle.model.ModelMap;
import org.gradle.model.Mutate;
import org.gradle.model.Path;
import org.gradle.model.RuleSource;

import org.gradle.util.CollectionUtils;

import org.gradle.model.internal.manage.schema.ModelSchemaStore;


import java.io.File;

import java.util.Collection;
import java.util.Collections;
import java.util.List;


public class LaunchScriptPlugin implements Plugin<Project> {

    public void apply(Project project){
        project.getPluginManager().apply(ComponentModelBasePlugin.class);
        project.getPluginManager().apply(JvmComponentPlugin.class);
    }


    static class Rules extends RuleSource {

	@ComponentType
	void registerComponent(ComponentTypeBuilder<LaunchScriptSpec> builder){
	    builder.defaultImplementation(BaseLaunchScriptSpec.class);
	}

	@Model
	void launchScripts(LaunchScriptContainer launchScripts){
	}

	@Mutate
	void copyLaunchScriptBinariesToGlobalContainer(BinaryContainer binaries,
						       LaunchScriptContainer launchScripts ){
	    for(LaunchScriptSpec launchScript: launchScripts.values()){
		for (BinarySpecInternal binary: launchScript.getBinaries().withType(BinarySpecInternal.class).values()){
		    binaries.put(binary.getProjectScopedName(),binary);
		}
	    }
	}


	@BinaryType
	void registerBinary(BinaryTypeBuilder<LaunchScriptBinarySpec> builder){
	    builder.defaultImplementation(BaseLaunchScriptBinarySpec.class);
	}

	@ComponentBinaries
	void createBinaries(ModelMap<BinarySpec> binaries,
			    final LaunchScriptSpec  component,
			    final @Path("buildDir") File buildDir,
			    PlatformResolvers platformResolver,
			    final ServiceRegistry serviceRegistry,
			    final ModelSchemaStore modelSchemaStore){
	    /*
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
	    */

	    JvmComponentSpec launchTarget = component.getLaunchTarget();

	    if(launchTarget == null) {
		createLaunchScriptBinarySpec(binaries,
					     LaunchScriptBinarySpec.class,
					     component,
					     null,
					     platformResolver,
					     serviceRegistry,
					     modelSchemaStore,
					     buildDir
					     );
	    }

	}


	private void createLaunchScriptBinarySpec(final ModelMap<BinarySpec> testBinaries,
						  Class<LaunchScriptBinarySpec> launchScriptBinaryClass,
						  final LaunchScriptSpec launchScript,
						  final JvmBinarySpec launchedBinary,
						  PlatformResolvers platformResolver,
						  final ServiceRegistry serviceRegistry,
						  final ModelSchemaStore modelSchemaStore,
						  final File buildDir){


	    final List<JavaPlatform> javaPlatforms = resolvePlatforms(platformResolver);
	    final JavaPlatform platform = launchedBinary != null ? launchedBinary.getTargetPlatform() : javaPlatforms.get(0);
	    final BinaryNamingScheme namingScheme = namingSchemeFor(launchScript, launchedBinary, javaPlatforms, platform);


	    testBinaries.create(namingScheme.getBinaryName(),
				launchScriptBinaryClass,
				new Action<LaunchScriptBinarySpec>() {

				    @Override
				    public void execute(LaunchScriptBinarySpec binary){
					binary.setMainClass(launchScript.getMainClass());
					//binary
					binary.setOutputDirectory(new File(buildDir,"launchScripts"));
				    }

				    private void injectDependencyResolutionServices() {
					ArtifactDependencyResolver dependencyResolver = serviceRegistry.get(ArtifactDependencyResolver.class);
					RepositoryHandler repositories = serviceRegistry.get(RepositoryHandler.class);
					List<ResolutionAwareRepository> resolutionAwareRepositories = CollectionUtils.collect(repositories, Transformers.cast(ResolutionAwareRepository.class));
					//testBinary.setArtifactDependencyResolver(dependencyResolver);
				    }

				}
				);


	}



	private static BinaryNamingScheme namingSchemeFor(LaunchScriptSpec launchScript,
							  JvmBinarySpec launchedBinary,
							  List<JavaPlatform> selectedPlatforms,
							  JavaPlatform platform) {

        BinaryNamingScheme namingScheme = DefaultBinaryNamingScheme.component(launchScript.getName())
            .withBinaryType("binary") // not a 'Jar', not a 'test'
            .withRole("assembly", true)
            .withVariantDimension(platform, selectedPlatforms);

        if (launchedBinary != null) {
            return namingScheme.withVariantDimension(((BinarySpecInternal) launchedBinary).getProjectScopedName());
        }

        return namingScheme;
    }


	private static List<JavaPlatform> resolvePlatforms(final PlatformResolvers platformResolver) {
	    PlatformRequirement defaultPlatformRequirement = DefaultPlatformRequirement.create(DefaultJavaPlatform.current().getName());
	    return Collections.singletonList(platformResolver.resolve(JavaPlatform.class, defaultPlatformRequirement));
	}

	private Collection<JvmBinarySpec> testedBinariesOf(LaunchScriptSpec launchScript){
	    return testedBinariesWithType(JvmBinarySpec.class, launchScript);
	}

	private static <S> Collection<S> testedBinariesWithType(Class<S> type, LaunchScriptSpec launchScript){
	    JvmComponentSpec spec = launchScript.getLaunchTarget();
	    return spec.getBinaries().withType(type).values();
	}

    }
}
