package com.javaagent.agent4;

import net.bytebuddy.implementation.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class MethodInter {
    
    @RuntimeType public Object intercept(@This Object obj, @AllArguments Object[] allArguments, @SuperCall Callable<?> zuper, @Origin Method method) throws Throwable {
        System.out.println("agent4 method intercept");
        return zuper.call();
    }
}