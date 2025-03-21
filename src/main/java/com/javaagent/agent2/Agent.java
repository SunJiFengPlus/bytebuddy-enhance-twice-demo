package com.javaagent.agent2;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;

import java.lang.instrument.Instrumentation;

// javac -cp /Users/sunjifeng/.m2/repository/net/bytebuddy/byte-buddy/1.12.19/byte-buddy-1.12.19.jar com/javaagent/agent2/*.java
// jar cfm premain2.jar com/javaagent/agent2/MANIFEST.MF com/javaagent/agent2/*.class
public class Agent {
    public static void premain(String agentArgs, Instrumentation inst) {
        new AgentBuilder.Default()
            .type(ElementMatchers.named("com.javaagent.Echo"))
            .transform((builder, a, b, c, d) -> builder.method(ElementMatchers.named("echo")).intercept(MethodDelegation.to(new MethodInter())))
            .with(new AgentBuilder.Listener.Adapter() {
                @Override
                public void onError(String typeName, ClassLoader classLoader, JavaModule module, boolean loaded, Throwable throwable) {
                    throwable.printStackTrace();
                }
            })
            .with(AgentBuilder.RedefinitionStrategy.RETRANSFORMATION)
            .installOn(inst);
    }
}
