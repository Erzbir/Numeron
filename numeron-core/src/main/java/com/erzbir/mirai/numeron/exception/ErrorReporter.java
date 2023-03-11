package com.erzbir.mirai.numeron.exception;

import com.erzbir.mirai.numeron.entity.NumeronBot;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Stack;

/**
 * @author Erzbir
 * @Date: 2023/3/11 18:15
 */
public class ErrorReporter {
    private static final Stack<Exception> stack = new Stack<>();

    static {
        scan();
    }

    public static String getReport() {
        StringBuilder sb = new StringBuilder();
        stack.forEach(t -> sb.append(stack.pop()).append("\n"));
        return sb.toString();
    }

    public static void scan() {
        Runtime.getRuntime().addShutdownHook(new Thread(ErrorReporter::save));
    }

    public static void save() {
        try (FileWriter fileWriter = new FileWriter(NumeronBot.INSTANCE.getFolder())) {
            fileWriter.append(getReport());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void add(Exception e) {
        stack.push(e);
    }
}
