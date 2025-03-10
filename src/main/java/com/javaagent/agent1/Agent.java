package com.javaagent.agent1;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.SuperMethodCall;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;

import java.lang.instrument.Instrumentation;

// javac -cp /Users/sunjifeng/.m2/repository/net/bytebuddy/byte-buddy/1.12.19/byte-buddy-1.12.19.jar com/javaagent/agent1/*.java
// jar cfm premain1.jar com/javaagent/agent1/MANIFEST.MF com/javaagent/agent1/*.class
public class Agent {
    public static void premain(String agentArgs, Instrumentation inst) {
        new AgentBuilder.Default()
            .type(ElementMatchers.named("com.javaagent.Echo"))
            .transform((builder, a, b, c, d) -> builder.constructor(ElementMatchers.any()).intercept(SuperMethodCall.INSTANCE.andThen(MethodDelegation.withDefaultConfiguration().to(new ConstructorInter(), ""))))
            .with(new AgentBuilder.Listener.Adapter() {
                @Override
                public void onError(String typeName, ClassLoader classLoader, JavaModule module, boolean loaded, Throwable throwable) {
                    throwable.printStackTrace();
                }
            })
            .installOn(inst);
    }
}
