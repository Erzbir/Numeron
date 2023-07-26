package com.erzbir.numeron.menu;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;


public class MenuDrawUtil {
    private static final int canvasWidth = 1000;
    private static final int canvasHeight = 1884;

    public static BufferedImage drawMenu(Long groupNumber) throws IOException, FontFormatException {
        BufferedImage drawImageBuffer = new BufferedImage(canvasWidth, canvasHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = drawImageBuffer.createGraphics();
        IOUtils.setRenderingHints(graphics);
        // Set background color
        graphics.setColor(new Color(255, 222, 255));
        graphics.fillRect(0, 0, canvasWidth, canvasHeight);
        // Drawing title
        BufferedImage titleImageBuffer = drawingTitle();
        graphics.drawImage(titleImageBuffer, 140, 300, 715, 100, null);
        // Draw "帮助菜单" text
        graphics.setColor(Color.WHITE);
        graphics.setFont(IOUtils.getFont(Font.BOLD, 57));
        String menuTitle = "帮助菜单";
        float titleWidth = (canvasWidth - graphics.getFontMetrics().stringWidth(menuTitle)) / 2.0f;
        graphics.drawString(menuTitle, titleWidth, 372.5F);
        // Draw "by Numeron" text
        graphics.setFont(IOUtils.getFont(Font.ITALIC, 25));
        graphics.drawString("by Numeron", 400, 450);
        // Retrieve images
        BufferedImage helpImageBuffer = MenuStatic.bufferedImageMap.get("help");
        BufferedImage contentImageBuffer = MenuStatic.bufferedImageMap.get("content");
        BufferedImage openImageBuffer = MenuStatic.bufferedImageMap.get("open");
        BufferedImage closeImageBuffer = MenuStatic.bufferedImageMap.get("close");
        // Draw help and content images
        if (helpImageBuffer != null) {
            graphics.drawImage(helpImageBuffer, 20, 550, 960, 40, null);
        }
        if (contentImageBuffer != null) {
            graphics.drawImage(contentImageBuffer, 20, 624, 960, 1240, null);
        }
        // Draw menu items
        graphics.setFont(IOUtils.getFont(Font.BOLD, 25));
        int i = 0;
        int j = 0;
        for (String key : MenuStatic.menuList) {
            if (i % 3 == 0) {
                j++;
            }
            int y = 654 + (j - 1) * 40 + (j - 1) * 2;
            int x = i % 3 == 0 ? 0 : i % 3 * 320;
            x += 30;
            graphics.setFont(IOUtils.getFont(Font.BOLD, 25));
            graphics.drawString((i + 1) + ": " + key, x, (float) y);
            if (MenuStatic.closeMenuGroups.get(key) != null && MenuStatic.closeMenuGroups.get(key).contains(groupNumber)) {
                graphics.drawImage(closeImageBuffer, x + 220, y - 40, 84, 60, null);
            } else {
                graphics.drawImage(openImageBuffer, x + 220, y - 40, 84, 60, null);
            }
            i++;
        }
        graphics.dispose();
        return drawImageBuffer;
    }

    private static BufferedImage drawingTitle() {
        BufferedImage drawingTitleBuffer = new BufferedImage(715, 100, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = drawingTitleBuffer.createGraphics();
        RenderingHints renderingHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        renderingHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        graphics.setRenderingHints(renderingHints);
        graphics.setColor(new Color(76, 66, 76));
        graphics.fillRoundRect(0, 0, 715, 100, 25, 25);
        graphics.dispose();
        return drawingTitleBuffer;
    }
}
