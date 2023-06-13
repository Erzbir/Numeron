package com.erzbir.numeron.menu;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;

public class IOUtils {
    public static BufferedImage drawingImgByPath(String filePath) throws IOException {
        BufferedImage drawingImgBuffer = null;
        InputStream inputStream = IOUtils.class.getResourceAsStream(filePath);
        if (inputStream != null) {
            drawingImgBuffer = ImageIO.read(inputStream);
        }
        if (inputStream != null) {
            inputStream.close();
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

    public static InputStream bufferedImageToInputStream(BufferedImage image, String suffix) throws IOException {
        if (image != null) {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(image, suffix, os);
            return new ByteArrayInputStream(os.toByteArray());
        }
        return null;
    }

    public static Image getImagByUrl(String url) throws IOException {
        URL file = new URL(url);
        return ImageIO.read(file);
    }

    public static Font getFont(int style, int size) throws IOException, FontFormatException {
        InputStream is = IOUtils.class.getResourceAsStream("/Fonts/SwordAndShield.ttf");
        Font actionJson = null;
        if (is != null) {
            actionJson = Font.createFont(Font.TRUETYPE_FONT, is);
            is.close();
        }
        if (actionJson != null) return actionJson.deriveFont(style, size);
        else return new Font("黑体", style, size);
    }

    public static void saveImage(BufferedImage bufferedImage, File file, String suffix) throws IOException {
        if (!file.mkdirs()) return;
        ImageIO.write(bufferedImage, suffix, file);
    }
}
