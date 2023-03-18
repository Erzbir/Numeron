package com.erzbir.mirai.numeron.menu;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;

public class IOUtils {
    public static BufferedImage drawingImgByPath(String filePath) {
        BufferedImage drawingImgBuffer = null;
        InputStream inputStream = IOUtils.class.getResourceAsStream(filePath);
        if (inputStream != null) {
            try {
                drawingImgBuffer = ImageIO.read(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return drawingImgBuffer;
    }

    public static Graphics2D getGraphics2D(BufferedImage bufferedImage) {
        Graphics2D graphics = bufferedImage.createGraphics();
        RenderingHints renderingHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        renderingHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        graphics.setRenderingHints(renderingHints);
        return graphics;
    }

    public static InputStream bufferedImageToInputStream(BufferedImage image, String suffix) {
        if (image != null) {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            try {
                ImageIO.write(image, suffix, os);
                return new ByteArrayInputStream(os.toByteArray());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static Image getImagByUrl(String url) {
        try {
            URL file = new URL(url);
            return ImageIO.read(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Font getFont(int style, int size) {
        InputStream is = IOUtils.class.getResourceAsStream("/Fonts/SwordAndShield.ttf");
        Font actionJson = null;
        try {
            assert is != null;
            actionJson = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (actionJson != null) return actionJson.deriveFont(style, size);
        else return new Font("黑体", style, size);
    }

    public static void saveImage(BufferedImage bufferedImage, File file, String suffix) {
        try {
            if (!file.mkdirs()) return;
            ImageIO.write(bufferedImage, suffix, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
