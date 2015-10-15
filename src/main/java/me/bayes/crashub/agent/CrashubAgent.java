package me.bayes.crashub.agent;

import java.lang.instrument.Instrumentation;

/**
 * Created by kevinbayes on 2015/10/14.
 */
public class CrashubAgent {


    public static void premain(String agentArgument,
                               Instrumentation instrumentation){

        System.out.println("Test Java Agent");
        new CrashBootstrap().start();
    }

}
