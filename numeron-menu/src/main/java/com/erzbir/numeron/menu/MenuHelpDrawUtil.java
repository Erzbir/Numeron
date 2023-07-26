package com.erzbir.numeron.menu;

import com.erzbir.numeron.annotation.Command;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class MenuHelpDrawUtil {
    public static final int CANVAS_WIDTH = 1000;
    public static final int CANVAS_HEIGHT = 1884;

    public static BufferedImage drawMenuHelp(String menuName) throws IOException, FontFormatException {
        int height = 840 + MenuStatic.menuMap.get(menuName).size() * 60;
        int canvasHeight = CANVAS_HEIGHT;
        if (height >= 1900) {
            canvasHeight = 2500;
        }
        BufferedImage drawImageBuffer = new BufferedImage(CANVAS_WIDTH, canvasHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = drawImageBuffer.createGraphics();
        IOUtils.setRenderingHints(graphics);
        graphics.setColor(new Color(255, 222, 255));
        graphics.fillRect(0, 0, CANVAS_WIDTH, canvasHeight);
        BufferedImage contentImageBuffer = drawingContent(menuName, height);
        graphics.drawImage(contentImageBuffer, 20, canvasHeight - height - 20, 960, height, null);
        graphics.setFont(IOUtils.getFont(Font.BOLD, 57));
        int width = graphics.getFontMetrics().stringWidth(menuName);
        BufferedImage titleImageBuffer = drawingTitle(menuName, width);
        graphics.drawImage(titleImageBuffer, (CANVAS_WIDTH - (width + 40)) / 2, (canvasHeight - height - 20 - 100) / 2, width + 40, 100, null);
        graphics.setColor(Color.WHITE);
        graphics.setFont(IOUtils.getFont(Font.ITALIC, 25));
        graphics.drawString("by Numeron", 400, (canvasHeight - height) / 2 + 95);
        graphics.dispose();
        return drawImageBuffer;
    }

    private static BufferedImage drawingContent(String menuName, int height) throws IOException, FontFormatException {
        BufferedImage drawingContentBuffer = new BufferedImage(960, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = IOUtils.getGraphics2D(drawingContentBuffer);
        graphics.setColor(new Color(76, 66, 76));
        BufferedImage describeImageBuffer = MenuStatic.bufferedImageMap.get("describe");
        graphics.drawImage(describeImageBuffer, 0, 0, 960, 80, null);
        BufferedImage authorImageBuffer = MenuStatic.bufferedImageMap.get("author");
        graphics.drawImage(authorImageBuffer, 0, 240, 960, 80, null);
        BufferedImage functionImageBuffer = MenuStatic.bufferedImageMap.get("function");
        graphics.drawImage(functionImageBuffer, 0, 480, 960, 80, null);
        //  BufferedImage showImageBuffer = bufferedImageMap.get("show");
        //      graphics.drawImage(showImageBuffer, 0, 660 + 60 * menuMap.get(menuName).size(), 960, 80, null);
        graphics.fillRoundRect(0, 100, 960, 60, 25, 25);
        graphics.fillRoundRect(0, 340, 960, 60, 25, 25);
        graphics.fillRoundRect(0, 580, 960, 60 * MenuStatic.menuMap.get(menuName).size(), 25, 25);
        //     graphics.fillRoundRect(0,760 + 60 * menuMap.get(menuName).size(),960,60,25,25);
        graphics.setColor(Color.WHITE);
        graphics.setFont(IOUtils.getFont(Font.PLAIN, 30));
//        graphics.drawString(menuList.get(menuName), 20, 140);
        graphics.drawString("Erzbir", 20, 380);
        int y = 580;
        for (Command command : MenuStatic.menuMap.get(menuName)) {
            if (command != null) {
                graphics.drawString(command.dec() + "  " + command.help() + "  " + command.permission().name(), 20, y + 60);
                y += 40;
            }
        }
        graphics.dispose();
        return drawingContentBuffer;
    }

    private static BufferedImage drawingTitle(String menuName, int width) throws IOException, FontFormatException {
        BufferedImage drawingTitleBuffer = new BufferedImage(width + 40, 100, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = IOUtils.getGraphics2D(drawingTitleBuffer);
        graphics.setColor(new Color(76, 66, 76));
        graphics.fillRoundRect(0, 0, width + 40, 100, 25, 25);
        graphics.setColor(Color.WHITE);
        graphics.setFont(IOUtils.getFont(Font.BOLD, 57));
        graphics.drawString(menuName, 20, 75.5F);
        graphics.dispose();
        return drawingTitleBuffer;
    }
}