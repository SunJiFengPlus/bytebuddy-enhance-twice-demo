package com.javaagent.agent4;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;

import java.lang.instrument.Instrumentation;

// javac -cp /Users/sunjifeng/.m2/repository/net/bytebuddy/byte-buddy/1.14.9/byte-buddy-1.14.9.jar com/javaagent/agent4/*.java
// jar cfm premain4.jar com/javaagent/agent4/MANIFEST.MF com/javaagent/agent4/*.class
public class Agent {
    public static void premain(String agentArgs, Instrumentation inst) {
        new AgentBuilder.Default(new ByteBuddy())
            .type(ElementMatchers.named("com.javaagent.Echo"))
            .transform((builder, a, b, c, d) -> builder.method(ElementMatchers.named("echo")).intercept(MethodDelegation.to(new MethodInter())))
            .with(new AgentBuilder.Listener.Adapter() {
                @Override
                public void onError(String typeName, ClassLoader classLoader, JavaModule module, boolean loaded, Throwable throwable) {
                    throwable.printStackTrace();
                }
            })
            .with(new MyClassFileLocationStrategy(AgentBuilder.LocationStrategy.ForClassLoader.WEAK, "/Users/sunjifeng/Desktop/tmp"))
            .installOn(inst);
    }
}
