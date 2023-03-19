package com.erzbir.numeron.menu;


import com.erzbir.numeron.core.handler.Command;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MenuHelpDrawUtil {
    public static final int canvasWidth = 1000;
    public static int canvasHeight = 1884;

    public static BufferedImage drawMenuHelp(String menuName) {
        int height = 840 + MenuStatic.menuMap.get(menuName).size() * 60;
        if (height >= 1900) {
            canvasHeight = 2500;
        }
        BufferedImage drawImageBuffer = new BufferedImage(canvasWidth, canvasHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = drawImageBuffer.createGraphics();
        RenderingHints renderingHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        renderingHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        graphics.setRenderingHints(renderingHints);
        graphics.setColor(new Color(255, 222, 255));
        graphics.fillRect(0, 0, canvasWidth, canvasHeight);
        BufferedImage contentImageBuffer = drawingContent(menuName, height);
        graphics.drawImage(contentImageBuffer, 20, canvasHeight - height - 20, 960, height, null);
        graphics.setFont(IOUtils.getFont(Font.BOLD, 57));
        int width = graphics.getFontMetrics().stringWidth(menuName);
        BufferedImage titleImageBuffer = drawingTitle(menuName, width);
        graphics.drawImage(titleImageBuffer, (canvasWidth - (width + 40)) / 2, (canvasHeight - height - 20 - 100) / 2, width + 40, 100, null);
        graphics.setColor(Color.WHITE);
        graphics.setFont(IOUtils.getFont(Font.ITALIC, 25));
        graphics.drawString("by Numeron", 400, (canvasHeight - height) / 2 + 95);
        graphics.dispose();
        return drawImageBuffer;
    }

    private static BufferedImage drawingContent(String menuName, int height) {
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
        //    graphics.drawString("暂无", 20, 800 + 60 * menuMap.get(menuName).size());
        int i = 0;
        for (Command k : MenuStatic.menuMap.get(menuName)) {
            if (k == null) {
                continue;
            }
            graphics.drawString(k.dec() + "  " + k.help() + "  " + k.permission().name(), 20, 580 + 60 + i * 40);
            i++;
        }
        graphics.dispose();
        return drawingContentBuffer;
    }

    private static BufferedImage drawingTitle(String menuName, int width) {
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
