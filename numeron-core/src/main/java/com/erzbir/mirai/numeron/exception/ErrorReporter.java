package com.erzbir.mirai.numeron.exception;

import com.erzbir.mirai.numeron.entity.NumeronBot;

import java.io.FileWriter;
import java.io.IOException;

/**
 * @author Erzbir
 * @Date: 2023/3/11 18:15
 */
public class ErrorReporter {

    public static void save(Exception e) {
        try (FileWriter fileWriter = new FileWriter(NumeronBot.INSTANCE.getFolder() + "error.log")) {
            fileWriter.append(e.getMessage());
            fileWriter.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
