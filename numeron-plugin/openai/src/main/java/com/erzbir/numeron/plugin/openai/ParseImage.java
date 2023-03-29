package com.erzbir.numeron.plugin.openai;

import com.theokanning.openai.image.Image;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Erzbir
 * @Date: 2023/3/4 23:14
 */
public class ParseImage {
    private static final ExecutorService ex = Executors.newSingleThreadExecutor();

    public static byte[] store(Image image, String folder, boolean isSave) {
        if (image.getB64Json().isEmpty()) {
            throw new IllegalArgumentException("data is empty");
        }
        byte[] decode = Base64.getDecoder().decode(image.getB64Json());
        if (!isSave) {
            return decode;
        }
        ex.submit(() -> {
            File file = new File(folder + UUID.randomUUID() + ".png");
            try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
                fileOutputStream.write(decode);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        return decode;
    }
}
