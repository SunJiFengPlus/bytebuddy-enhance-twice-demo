package com.javaagent.agent3;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.SuperMethodCall;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;

import java.io.File;
import java.io.IOException;
import java.lang.instrument.Instrumentation;

// javac -cp /Users/sunjifeng/.m2/repository/net/bytebuddy/byte-buddy/1.14.9/byte-buddy-1.14.9.jar com/javaagent/agent3/*.java
// jar cfm premain3.jar com/javaagent/agent3/MANIFEST.MF com/javaagent/agent3/*.class
public class Agent {
    public static void premain(String agentArgs, Instrumentation inst) {
        new AgentBuilder.Default(new ByteBuddy())
            .type(ElementMatchers.named("com.javaagent.Echo"))
            .transform((builder, a, b, c, d) -> builder.constructor(ElementMatchers.any()).intercept(SuperMethodCall.INSTANCE.andThen(MethodDelegation.withDefaultConfiguration().to(new ConstructorInter()))))
            .with(new AgentBuilder.Listener.Adapter() {
                @Override
                public void onError(String typeName, ClassLoader classLoader, JavaModule module, boolean loaded, Throwable throwable) {
                    throwable.printStackTrace();
                }

                @Override
                public void onTransformation(TypeDescription typeDescription, ClassLoader classLoader, JavaModule module, boolean loaded, DynamicType dynamicType) {
                    try {
                        dynamicType.saveIn(new File("/Users/sunjifeng/Desktop/tmp"));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            })
            .installOn(inst);
    }
}
