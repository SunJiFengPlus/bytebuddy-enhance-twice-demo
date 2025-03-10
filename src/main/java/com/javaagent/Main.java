package com.javaagent;

public class Main {
    
    public static void main(String[] args) throws InterruptedException {
        while (true) {
            new Echo().echo();
            Thread.sleep(1000);
        }
    }
}
