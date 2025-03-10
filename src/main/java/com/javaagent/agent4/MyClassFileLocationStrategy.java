package com.javaagent.agent4;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.dynamic.ClassFileLocator;
import net.bytebuddy.utility.JavaModule;

import java.io.File;

public class MyClassFileLocationStrategy implements AgentBuilder.LocationStrategy {

    private final AgentBuilder.LocationStrategy defaultStrategy;

    private final String dir;

    public MyClassFileLocationStrategy(AgentBuilder.LocationStrategy defaultStrategy, String dir) {
        this.defaultStrategy = defaultStrategy;
        this.dir = dir;
    }

    @Override
    public ClassFileLocator classFileLocator(ClassLoader classLoader, JavaModule module) {
        ClassFileLocator folderLocator = new ClassFileLocator.ForFolder(new File(dir));
        return new ClassFileLocator.Compound(defaultStrategy.classFileLocator(classLoader, module), folderLocator);
    }

}
