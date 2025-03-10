package com.javaagent.agent3;

import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.This;

public class ConstructorInter {

    @RuntimeType
    public void intercept(@This Object obj, @AllArguments Object[] allArguments) {
        System.out.println("agent3 constructor intercept");
    }
}