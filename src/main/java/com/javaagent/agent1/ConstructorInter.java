package com.javaagent.agent1;

import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.This;

import java.util.Arrays;

public class ConstructorInter {
    @RuntimeType public void intercept(@This Object obj, @AllArguments Object[] allArguments) {
        System.out.println("agent1 constructor intercept");
    }
}